# -*- coding: utf-8 -*-
"""
Created on Mon Jan 24 20:56:55 2022

@author: willh
"""


import pandas as pd
import mysql.connector
from datetime import datetime

def main():
    print("Reading CSV...")
    df = pd.read_csv('calendar_campus.csv') # take info in from csv into pandas dataframe
    df = df.drop(['description', 'status', 'categories'], axis = 1) # remove unnecessary categories
    
    # connect to MySQL database
    print("Connecting to database...")
    mydb = mysql.connector.connect(
        host = 'db',
        user = 'root',
        password = 'root',
        database = 'ml_database'
        )
    
    cursor = mydb.cursor()
    
    evts_list = []
    
    # fill list of events with info from pandas dataframe
    for x in range(len(df)):
        name = df.loc[x, 'summary']
        org = df.loc[x, 'host']
        loc = df.loc[x, 'location'] + " behrend"
        url = df.loc[x, 'URL']
        start = df.loc[x, 'startTime']
        
        start = start[:len(start) - 2]
        #end = end[:len(end) - 2]
        #0000-00-00 00:00:00
        start = str(int(datetime.strptime(start, '%Y-%m-%d %H:%M:%S').timestamp()))
        evts_list.append((name, org, loc, start, url))
        
        
    print(str(len(evts_list)) + " events to be added...")
        
    sql = "INSERT INTO events (EVTNAME, ORG, LOC, DATE, URL) VALUES (%s, %s, %s, %s, %s)"
    
    cursor.executemany(sql, evts_list)
    
    mydb.commit() #update database
    
    mydb.close()
    
    print("Done!")
    
                    


if __name__ == "__main__":
    main()