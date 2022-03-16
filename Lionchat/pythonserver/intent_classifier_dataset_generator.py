# -*- coding: utf-8 -*-
"""
Created on Tue Mar  8 09:13:26 2022

@author: Kevin Wang
"""
from nltk.corpus import stopwords
import pandas as pd
import spacy
from spacy.tokens import DocBin
from tqdm.auto import tqdm
import random

class intent_classifier_dataset_generator:
    
    alpha = .80
    
    def remove_stopwords(dataset):
        pass
    
    def synonym_replacement(sentence):
        pass
    
    def random_insertion(sentence):
        pass
    
    def random_swap(sentence):
        pass
    
    def random_deletion(sentence):
        pass
    
    def generate_question_and_label_list(cls, question_list, label_list, source_list, label):
        for question in source_list:
            question_list.append(question)
            label_list.append(label)
        return question_list, label_list
    
    def convert_to_docs(cls, data):
        nlp = spacy.load("en_core_web_trf")
        
        docs = []
        for doc, label in tqdm(nlp.pipe(data, as_tuples=True), total=len(data)):
            if(label=='IT_Intent'):
                doc.cats['IT_Intent'] = 1
                doc.cats['Event_Intent'] = 0
            else:
                doc.cats['IT_Intent'] = 0
                doc.cats['Event_Intent'] = 1
            docs.append(doc)
        return docs
    
    def generate_dataset(cls):
        '''
        Current Implementation utilizes all of the
        Campus Events dataset and however much is necessary
        from Erie Events dataset to balance out with IT articles
        (after 80-20 split between training and validation dataset)
        '''
        
        '''
        First, read in questions/values from datasets
        '''
        it_df = pd.read_excel('All Knowledge Articles.xlsx')
        
        campus_events_train_df = pd.read_csv('campus_events_training_data.csv', encoding='UTF-8')
        campus_events_valid_df = pd.read_csv('campus_events_validation_data.csv', encoding='UTF-8')
        campus_events_df = pd.concat([campus_events_train_df, campus_events_valid_df], ignore_index=True)
        
        erie_events_train_df = pd.read_csv('erie_events_training_data.csv', encoding='UTF-8')
        erie_events_valid_df = pd.read_csv('erie_events_validation_data.csv', encoding='UTF-8')
        erie_events_df = pd.concat([erie_events_train_df, erie_events_valid_df], ignore_index=True)
        
        it_list = it_df['Article Name'].values
        campus_events_list = campus_events_df['questions'].values
        erie_events_list = erie_events_df['questions'].values
        
        it_list_len = len(it_list)
        campus_events_len = len(campus_events_list)
        erie_events_len = len(erie_events_list)
        
        it_list_len = 1000
        campus_events_len = 900
        
        print("Num of IT Questions:", len(it_list))
        print("Num of Campus Events Questions:", len(campus_events_list))
        print("Num of Erie Events Questions:", len(erie_events_list))
        print("Total:", it_list_len + campus_events_len + erie_events_len)
        
        random.shuffle(it_list)
        
        percentTrain = 1.0
        it_train_list = it_list[:(int(percentTrain * it_list_len))]
        it_valid_list = it_list[(int(percentTrain * it_list_len)):]
        
        campus_events_train_list = campus_events_list[:(int(percentTrain * campus_events_len))]
        campus_events_valid_list = campus_events_list[(int(percentTrain * campus_events_len)):]
        
        newLength = abs(it_list_len - campus_events_len)
        erie_events_train_list = erie_events_list[:(int(newLength))]
        erie_events_valid_list = erie_events_list[(int(newLength)):]
        
        train_question_list = []
        train_label_list = []
        
        train_question_list, train_label_list = cls.generate_question_and_label_list(train_question_list, train_label_list, it_train_list, 'IT_Intent')
        train_question_list, train_label_list = cls.generate_question_and_label_list(train_question_list, train_label_list, campus_events_train_list, 'Event_Intent')
        train_question_list, train_label_list = cls.generate_question_and_label_list(train_question_list, train_label_list, erie_events_train_list, 'Event_Intent')
        
        valid_question_list = []
        valid_label_list = []
        
        valid_question_list, valid_label_list = cls.generate_question_and_label_list(valid_question_list, valid_label_list, it_valid_list, 'IT_Intent')
        valid_question_list, valid_label_list = cls.generate_question_and_label_list(valid_question_list, valid_label_list, campus_events_valid_list, 'Event_Intent')
        valid_question_list, valid_label_list = cls.generate_question_and_label_list(valid_question_list, valid_label_list, erie_events_valid_list, 'Event_Intent')
        
        print("Training Question list length:", len(train_question_list))
        print("Training Label list length:", len(train_label_list))
        
        print("Validation Question list length:", len(valid_question_list))
        print("Validation Label list length:", len(valid_label_list))
        
        '''
        Lowercase whole training and validation list
        '''
        train_question_list = [question.lower() for question in train_question_list]
        valid_question_list = [question.lower() for question in valid_question_list]
        
        print(train_question_list[0:5])
        print(valid_question_list[0:5])
        
        train_question_tuple_list = tuple(zip(train_question_list, train_label_list))
        valid_question_tuple_list = tuple(zip(valid_question_list, valid_label_list))
        
        train_docs = cls.convert_to_docs(train_question_tuple_list)
        valid_docs = cls.convert_to_docs(valid_question_tuple_list)
        
        print("Generating .spacy files")
        train_doc_bin = DocBin(docs=train_docs)
        train_doc_bin.to_disk("./train_questions.spacy")
        print("Training File finished!")
        valid_doc_bin = DocBin(docs=valid_docs)
        valid_doc_bin.to_disk("./valid_questions.spacy")
        print("Validation File finished!")
        
    def process_erie_events(cls):
        df = pd.read_csv('erieEvents.csv', encoding='UTF-8')
        
        #Process dates and times
        eventTimeList = df['eventTime'].values
        
        event_date_list = []
        event_start_hour_list = []
        event_end_hour_list = []
        for event_time in eventTimeList:
            if 'General' in event_time:
                event_time = event_time.replace('General', '')
            
            event_time_split_list = event_time.split('|')
            event_date = event_time_split_list[0]
            event_hour_range = ''
            if len(event_time_split_list) > 1:
                event_hour_range = event_time_split_list[1]
            
            event_date = event_date.lstrip()
            event_hour_range = event_hour_range.lstrip()
            
            start_hour = ''
            end_hour = ''
            if event_hour_range != '':
                hour_split_list = event_hour_range.split('-')
                start_hour = hour_split_list[0]
                
                if len(hour_split_list) > 1:
                    end_hour = hour_split_list[1]
            event_start_hour_list.append(start_hour)
            event_end_hour_list.append(end_hour)
            
            event_date_list.append(event_date)
        
        df['date'] = event_date_list
        df['dateDateTime'] = pd.to_datetime(df['date'])
        df['startHour'] = event_start_hour_list
        df['startHourDateTime'] = pd.to_datetime(df['startHour']).dt.hour
        df['endHour'] = event_end_hour_list
        
        df = df.sort_values(['dateDateTime', 'startHourDateTime'], ascending=(True, True))
        
        #Process locations as "Organizations"
        locationList = df['eventLocation'].values
        
        organizationList = []
        for location in locationList:
            organization = ''
            if not pd.isnull(location):
                locationInfo = location.splitlines()
                organization = locationInfo[0]
                # print(organization)
            organizationList.append(organization)
            
        df['organization'] = organizationList
                
        df.to_csv('erieEventsProcessed.csv', encoding='UTF-8')
    
    def test_model(cls):
        nlp = spacy.load("./output_updated/model-best")
        inputVal = input("Enter text: ")
        while inputVal != 'exit':
            output = nlp(inputVal)
            print(output.cats)
            inputVal = input("Enter text: ")
        
        # demo = nlp("where does behrend cheerleading meet")
        # print(demo.cats)
    
if __name__ == "__main__":
    
    dataset_generator = intent_classifier_dataset_generator()
    # dataset_generator.process_erie_events()
    # dataset_generator.generate_dataset()
    dataset_generator.test_model()