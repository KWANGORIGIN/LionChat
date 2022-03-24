# -*- coding: utf-8 -*-
"""
Created on Wed Feb 16 19:32:14 2022

@author: Kevin Wang
"""
from flask import Flask, request, jsonify
from flask_cors import CORS
from sentence_transformers import SentenceTransformer
import numpy as np
import pandas as pd
from sklearn.neighbors import NearestNeighbors
import ner
import tox
import csv
import spacy

app = Flask(__name__)
CORS(app)

#Sentence Transformer embedder
embedder = SentenceTransformer('all-MiniLM-L6-v2')
articleUrls = []
articleTitles = []
def generate_corpus_embeddings():
    #Read values from csv
    df = pd.read_csv('IT_Knowledge_Articles.csv')
    
    #Get article titles from column
    global articleTitles, articleUrls
    articleTitles = df.ArticleName.values
    articleUrls = df.ArticleLink.values
    
    return embedder.encode(articleTitles, convert_to_numpy=True)

#Similarity search object
similarity_searcher = NearestNeighbors(n_neighbors=5)
@app.before_first_request
def fit_corpus_for_similarity_search():
    #Get corpus embeddings
    corpus_embeddings = generate_corpus_embeddings()
    
    #Fit onto nearest neighbors
    similarity_searcher.fit(corpus_embeddings)

@app.route('/semantic-search-results', methods=['POST'])
def getSemanticSearchResults():
    #Get POST request from client
    receivedDict = request.get_json()
    print("ReceivedDict: ", receivedDict)
    
    #Get query from received dict
    query = receivedDict['query']
    
    #Convert query to embedding
    query_embedding = embedder.encode(query, convert_to_numpy=True)
    
    #Reshape corpus embedding for input in KNN
    query_embedding = np.reshape(query_embedding, (1, 384))
    
    #KNN semantic search
    similarities, positions = similarity_searcher.kneighbors(query_embedding, return_distance=True)
    similarities = similarities[0]
    positions = positions[0]
    #Get results
    results = {'titles':[], 'urls': []}
    
    for position in positions:
        results['titles'].append(articleTitles[position])
        results['urls'].append(articleUrls[position])

    # print(results)
    # print(jsonify(results))
    # print(similarities)
    
    # print("Outgoing results: ", results)
    #Return json list of search results
    return jsonify(results)

nlp = spacy.load("./output_updated/model-best")
@app.before_first_request
def trainIntentClassifier():
    nlp = spacy.load("./output_updated/model-best")
    
    # #Open questions.csv to train classifier from
    # questions = []
    # labels = []
    # with open("questions.csv", newline='', encoding='utf-8-sig') as csvfile:
    #     csvReader = csv.DictReader(csvfile, delimiter=',')
    #     for row in csvReader:
    #         questions.append(row['Question'].strip())
    #         labels.append(row['Intent'].strip())
        
    # #Transforming questions list into questions tfIDF vectors
    # questions_vec = inputVector.fit_transform(questions)
    
    # #Training the classifier
    # classifier.fit(questions_vec, labels)

@app.route('/intent', methods=['POST'])
def classifyIntent():
    
    #Get POST request from client
    receivedDict = request.get_json()
    print(receivedDict)
    
    #Get user utterance
    userInput = receivedDict["utterance"]
    
    doc = nlp(userInput)
    
    classifiedIntent = max(doc.cats, key = doc.cats.get)
    print("Classified Intent: ", classifiedIntent)
    
    # #Transform user utterance to tfIDF vector
    # new_question = inputVector.transform(userInput)
    
    # #Classify intent
    # intent = classifier.predict(new_question)
    # # print(intent)
    
    # prob = classifier.predict_proba(new_question)
    # # print(prob)
    # prob = prob.item(0) #Gets first probability in list
    
    # #If probability is the same across each class, then intent is unknown
    

    
    # May be wrong. Might need to update for multilabel classification

    # if(prob == (1 / len(classifier.classes_))):
    #     intent = ["unknownIntent"]
    
    #Return intent as JSON
    return jsonify(intent=classifiedIntent)
    
@app.route('/events_entities', methods=["POST"])
def get_entities():
    
    if isinstance(request.json, str):
        text = jsonify(request.json)["text"]
    else:
        text = request.json["text"]
    
    entities = []
    
    for ent in ner.getEnts(text):
        entities.append((ent.text, ent.label_))
        
    return jsonify(entities = entities)

@app.route('/toxic_classification', methods=["POST"])
def is_toxic():
    
    if isinstance(request.json, str):
        text = jsonify(request.json)["utterance"]
    else:
        text = request.json["utterance"]

    return jsonify(toxicity = tox.is_toxic(text))
    
    
if __name__ == "__main__":
    #Initialize similarity searcher
    # fit_corpus_for_similarity_search()
    
    #load ner model
    ner.init()
    tox.init()
    
    #Run server
    app.run(port = 8000)
