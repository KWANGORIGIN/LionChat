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
import yaml
from flask_mysqldb import MySQL
from datetime import datetime
from datetime import tzinfo, timedelta, datetime

app = Flask(__name__)
CORS(app)

#configure database info

#make sure server doesn't crash if config file missing
try:
    db = yaml.safe_load(open('db.yaml'))
    app.config['MYSQL_HOST'] = db['mysql_host']
    app.config['MYSQL_USER'] = db['mysql_user']
    app.config['MYSQL_PASSWORD'] = db['mysql_password']
    app.config['MYSQL_DB'] = db['mysql_db']
    mydb = MySQL(app)
except:
    print("YAML database config file not found!")

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

@app.route('/answer_events', methods=["POST"])
def get_events():
    #default message
    message = "Here's what I found for this week:"
    
    #get request from Spring Boot application containing query
    if isinstance(request.json, str):
        text = jsonify(request.json)["utterance"]
    else:
        text = request.json["utterance"]
    
    entities = ner.getEnts(text) # get entities from user query
    
    #if entities are present
    if entities:
        #initialize some booleans
        more_entities = False
        set_date = False
        set_name = False
        sql = "SELECT * FROM events WHERE " # begin sql query on events table
        
        #find current day for use in query
        current_day = datetime.today().replace(hour = 0, minute = 0, second = 0, microsecond = 0)
        date1 = current_day
        date2 = date1
        
        #by default, get events happening this week, so time span is set for current day till end of week
        while date2.weekday() != 6:
            date2 += timedelta(days=1)
        date2 += timedelta(days=1)

        
        for ent in entities:
            label = ent.label_
            text = ent.text.replace("?", "") #replace question mark if was accidentally returned
            
            if label == 'EVTNAME': #if event name was input, get all future dates instead of one week
                set_name = True
            
            if more_entities and label != 'DATE': #if date was entered, set time span accordingly
                sql = sql + " AND " 
            
            if label == 'DATE':
                set_date = True

                if text == 'today':
                    date2 = date1 + timedelta(days=1)
                    message = "Here's what I found for this today:"

                elif text == 'tomorrow':
                    date1 = current_day + timedelta(days=1)
                    date2 = date1 + timedelta(days=1)
                    message = "Here's what I found for tomorrow:"
                
                elif text == 'next week':
                    date1 += timedelta(weeks = 1)
                    date2 = date1
                    
                    while date1.weekday() != 0:
                        date1 -= timedelta(days = 1)
                    while date2.weekday() != 6:
                        date2 += timedelta(days = 1)
                    date2 += timedelta(days=1)   
                    message = "Here's what I found for next week:"
                    
                elif text == 'this month':
                    date2 = date1
                    month = date1.month
                    while date2.month == month:
                        date2 += timedelta(days = 1)
                    message = "Here's what I found for this month:"
                elif text != 'this week':
                    message = "I'm sorry, that time-frame was not recognized.  Here are today's events:"
                    date2 = date1 + timedelta(days=1)

            else:
                sql = sql +  "LOWER({}) like '%{}%'".format(label, text)
                more_entities = True
            
        for ent in entities:
            print(ent.label_, ent.text)
            
        if more_entities:
            sql = sql + " AND " #add more entities to search in the database
            
        #if event name was set
        if set_name:
            message = "Here's what I found for this event:"
     
        if not set_date and set_name: #if name entered and date not entered, query all future dates for event
            sql += "DATE >= {}".format(int(date1.timestamp()))
        else: #otherwise query between whatever dates were set
            sql += "DATE BETWEEN {} AND {}".format(int(date1.timestamp()), int(date2.timestamp()) - 1)
        print(sql)
        print(date1)
        print(datetime.fromtimestamp(date2.timestamp() - 1))
        
        #make sure program does not crash if database missing
        try:
            cursor = mydb.connection.cursor()
            cursor.execute(sql)
        
            evt_list = list(cursor)
            cursor.close()
 
            for i in range(len(evt_list)):
                evt_list[i] = list(evt_list[i])
                evt_list[i][3] = str(datetime.fromtimestamp(int(evt_list[i][3])).strftime("%m/%d/%Y, %H:%M:%S"))
        except:
            print("Can't access database")
            evt_list = ["Database missing."]
            
        if evt_list: #if events found
            payload = jsonify(message = message, events = evt_list)
        else: #if nothing found
            payload = jsonify(message = "It looks like I don't have any information on that.", events = [evt_list])
        
        
    else: #if no entities were present
        payload = jsonify(message = 'I am sorry, I need more data.  Can you be more specific?', events = [])
        
    
     
    #EVTNAME
    #LOC
    #DATE
    #ORG
    
    return payload

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