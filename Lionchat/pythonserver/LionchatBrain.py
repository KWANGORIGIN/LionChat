# -*- coding: utf-8 -*-
"""
Created on Wed Feb 16 19:32:14 2022

@author: Kevin Wang
"""
from flask import Flask, request, jsonify
from flask_cors import CORS
import ner
import tox
import intent_classifier
import semantic_searcher
from mysql.connector import connect
from datetime import datetime
from datetime import tzinfo, timedelta, datetime

app = Flask(__name__)
CORS(app)

# configure database info


@app.route('/semantic-search-results', methods=['POST'])
def getSemanticSearchResults():
    # Get POST request from client
    receivedDict = request.get_json()
    print("ReceivedDict: ", receivedDict)

    # Get query from received dict
    query = receivedDict['query']

    results = semantic_searcher.search(query)

    # print(results)
    # print(jsonify(results))
    # print(similarities)

    # print("Outgoing results: ", results)
    # Return json list of search results
    return jsonify(results)


@app.route('/intent', methods=['POST'])
def classifyIntent():

    # Get POST request from client
    receivedDict = request.get_json()
    print(receivedDict)

    # Get user utterance
    userInput = receivedDict["utterance"]

    classifiedIntent = intent_classifier.classifyIntent(userInput)
    print("Classified Intent: ", classifiedIntent)

    # Return intent as JSON
    return jsonify(intent=classifiedIntent)


@app.route('/answer_events', methods=["POST"])
def get_events():
    # default message
    message = "Here's what I found for this week:"

    # get request from Spring Boot application containing query
    if isinstance(request.json, str):
        text = jsonify(request.json)["utterance"]
    else:
        text = request.json["utterance"]
        text = str.replace(text, "\'", "\\'")

    entities = []

    try:
        entities = ner.getEnts(text)  # get entities from user query
    except:
        print("NER model not found")

    # if entities are present
    if entities:
        # initialize some booleans
        more_entities = False
        set_date = False
        set_name = False
        # begin sql query on events table
        sql = "SELECT EVTNAME, ORG, LOC, DATE, URL FROM events WHERE "

        # find current day for use in query
        current_day = datetime.today().replace(
            hour=0, minute=0, second=0, microsecond=0)
        date1 = current_day
        date2 = date1

        # by default, get events happening this week, so time span is set for current day till end of week
        while date2.weekday() != 6:
            date2 += timedelta(days=1)
        date2 += timedelta(days=1)

        for ent in entities:
            label = ent.label_
            # replace question mark if was accidentally returned
            text = ent.text.replace("?", "")

            if label == 'EVTNAME':  # if event name was input, get all future dates instead of one week
                set_name = True

            if more_entities and label != 'DATE':  # if date was entered, set time span accordingly
                sql = sql + " AND "

            if label == 'DATE':
                set_date = True

                if text == 'today':
                    date2 = date1 + timedelta(days=1)
                    message = "Here's what I found for today:"

                elif text == 'tomorrow':
                    date1 = current_day + timedelta(days=1)
                    date2 = date1 + timedelta(days=1)
                    message = "Here's what I found for tomorrow:"

                elif text == 'next week':
                    date1 += timedelta(weeks=1)
                    date2 = date1

                    while date1.weekday() != 0:
                        date1 -= timedelta(days=1)
                    while date2.weekday() != 6:
                        date2 += timedelta(days=1)
                    date2 += timedelta(days=1)
                    message = "Here's what I found for next week:"

                elif text == 'this month':
                    date2 = date1
                    month = date1.month
                    while date2.month == month:
                        date2 += timedelta(days=1)
                    message = "Here's what I found for this month:"
                elif text != 'this week':
                    message = "I'm sorry, that time-frame was not recognized.  Here are today's events:"
                    date2 = date1 + timedelta(days=1)

            else:

                print(text)
                sql = sql + "LOWER({}) like '%{}%'".format(label, text)
                more_entities = True

        for ent in entities:
            print(ent.label_, ent.text)

        if more_entities:
            sql = sql + " AND "  # add more entities to search in the database

        # if event name was set
        if set_name:
            message = "Here's what I found for this event:"

        if not set_date and set_name:  # if name entered and date not entered, query all future dates for event
            sql += "DATE >= {}".format(int(date1.timestamp()))
        else:  # otherwise query between whatever dates were set
            sql += "DATE BETWEEN {} AND {}".format(
                int(date1.timestamp()), int(date2.timestamp()) - 1)
        # print(sql)
        # print(date1)
        # print(datetime.fromtimestamp(date2.timestamp() - 1))

        # make sure program does not crash if database missing
        try:
            config = {
                'user': 'root',
                'password': 'root',
                'host': 'db',
                'port': '3306',
                'database': 'ml_database'
            }
            connection = connect(**config)
            cursor = connection.cursor()
            cursor.execute(sql)

            evt_list = list(cursor)
            cursor.close()
            connection.close()

            for i in range(len(evt_list)):
                evt_list[i] = list(evt_list[i])
                evt_list[i][0] = "Event Name: " + evt_list[i][0]
                evt_list[i][1] = "Event Organizer: " + evt_list[i][1]
                evt_list[i][2] = "Event Location: " + evt_list[i][2]
                evt_list[i][3] = "Event Date: " + str(datetime.fromtimestamp(
                    int(evt_list[i][3])).strftime("%m/%d/%Y, %H:%M:%S"))
                evt_list[i][4] = "Event Link: " + evt_list[i][4]
        except Exception as e:
            print(e)
            print("Can't access database")
            evt_list = ["Database error."]

        if evt_list:  # if events found
            payload = jsonify(message=message, events=evt_list)
        else:  # if nothing found
            payload = jsonify(
                message="It looks like I don't have any information on that.", events=[evt_list])

    else:  # if no entities were present
        payload = jsonify(
            message='I am sorry, I need more data.  Can you be more specific?', events=[])

     # EVTNAME
     # LOC
     # DATE
     # ORG

    return payload


@app.route('/toxic_classification', methods=["POST"])
def is_toxic():

    if isinstance(request.json, str):
        text = jsonify(request.json)["utterance"]
    else:
        text = request.json["utterance"]

    return jsonify(toxicity=tox.is_toxic(text))

def preprocess_question(question):

        #Lowercase the question
        question.lower()
        
        #Remove punctuation
        question = re.sub(r'[^\w\s]', '', question)
            
        #Remove stop words
        stop_words_set = set(stopwords.words('english'))
        question_tokens = word_tokenize(question)
        print(question_tokens)
        processed_question_list = [word for word in question_tokens if word not in stop_words_set]
        #print(stop_words_set)
        
        processed_question = ''
        for w in processed_question_list:
            processed_question += w + ' '

        print(processed_question)
        return processed_question


if __name__ == "__main__":
    # Initialize similarity searcher
    # fit_corpus_for_similarity_search()
    # try:
    #     config = {
    #         'user': 'root',
    #         'password': 'root',
    #         'host': 'db',
    #         'port': '3306',
    #         'database': 'ml_database'
    #     }
    #     connection = connect(**config)
    #     cursor = connection.cursor()

    #     for line in open("./db/ml_database.sql"):
    #         cursor.execute(line)

    #     evt_list = list(cursor)
    #     cursor.close()
    #     connection.close()
    # except Exception as e:
    #     print(e)
    #     print("Can't access database")
    #     evt_list = ["Database error."]

    # load ner model
    ner.init()
    tox.init()
    intent_classifier.init()
    semantic_searcher.init()
    
    # Run server
    app.run(host='0.0.0.0', port = 8000)