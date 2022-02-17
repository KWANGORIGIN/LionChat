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

app = Flask(__name__)
CORS(app)

#Sentence Transformer embedder
embedder = SentenceTransformer('all-MiniLM-L6-v2')
articleTitles = []
def generate_corpus_embeddings():
    #Read values from csv
    df = pd.read_csv('IT_Knowledge_Articles.csv')
    
    #Get article titles from column
    articleTitles = df.ArticleName.values
    
    return embedder.encode(articleTitles, convert_to_numpy=True)

#Similarity search object
similarity_searcher = NearestNeighbors(n_neighbors=5)
def fit_corpus_for_similarity_search():
    #Get corpus embeddings
    corpus_embeddings = generate_corpus_embeddings()
    
    #Fit onto nearest neighbors
    similarity_searcher.fit(corpus_embeddings)

@app.route('/semantic-search-results', methods=['POST'])
def getSemanticSearchResults():
    #Get POST request from client
    receivedDict = request.get_json()
    
    #Get query from received dict
    query = receivedDict['query']
    
    #Convert query to embedding
    query_embedding = embedder.encode(query, convert_to_numpy=True)
    
    #Reshape corpus embedding for input in KNN
    query_embedding = np.reshape(query_embedding, (1, 384))
    
    #KNN semantic search
    resultPositions = similarity_searcher.kneighbors(query_embedding, return_distance=False)[0]
    
    #Get results
    results = []
    for position in resultPositions:
        results.append(articleTitles[position])
    
    #Return json list of search results
    return jsonify(searchresults = results)

@app.route('/intent', methods=['POST'])
def getIntent():
    print("What intent?")
    
    
@app.route('/entities', methods=["POST"])
def getEntities():
    text = request.json["text"]
    entities = []
    
    for ent in ner.getEnts(text):
        entities.append((ent.text, ent.label_))
        
    return jsonify(entities = entities)
    
if __name__ == "__main__":
    #Initialize similarity searcher
    fit_corpus_for_similarity_search()
    
    #load ner model
    ner.init()
    
    #Run server
    app.run(port = 8000)