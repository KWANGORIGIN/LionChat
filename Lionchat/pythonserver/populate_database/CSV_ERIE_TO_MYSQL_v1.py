# -*- coding: utf-8 -*-
"""
Created on Mon Jan 24 20:56:55 2022

@author: willh
"""

import re
import pandas as pd
import mysql.connector
from datetime import datetime

def main():
    print("Reading CSV...")
    df = pd.read_csv('erieEventsProcessed.csv') # read info in from csv into pandas dataframe
    
    evts_list = []
    # populate events list.  Requires more intensive processing so use individual functions
    for i in range(len(df)):
        name = get_evt_name(df, i)
        #print(name)
        org = get_org(df, i)
        #print(org)
        loc = get_location(df, i)
        #print(loc)
        start = get_start(df, i)
        #print(start)
        url = get_url(df, i)
        
        evts_list.append((name, org, loc, start, url))
    
    print(str(len(evts_list)) + " events to be added...")
    print("Connecting to database...")
    
    # connect to MySQL database
    print("Connecting to database...")
    mydb = mysql.connector.connect(
        host = 'db',
        user = 'root',
        password = 'root',
        database = 'ml_database'
        )
    
    cursor = mydb.cursor()
    
    sql = "INSERT INTO events (EVTNAME, ORG, LOC, DATE, URL) VALUES (%s, %s, %s, %s, %s)"
    
    cursor.executemany(sql, evts_list)
    
    mydb.commit() # commit changes to database
    
    mydb.close()
    
    print("Done!")
    
def get_url(df, i):
    url = str(df.loc[i, 'eventLink-href']).replace('\n', '')
    return url

def get_start(df, i):
    date = str(df.loc[i, 'date']).replace('\n', '')
    
    if not (date == 'nan'):
        date_list = date.split('/')
        date = date_list[2] + '-' + date_list[0].zfill(2) + '-' + date_list[1].zfill(2)
    else:
        print("Date does not exist!")
        return 0
        
    startHourDateTime = str(df.loc[i, 'startHourDateTime']).replace('\n', '')
    if not (startHourDateTime == 'nan'):
        startHourDateTime = str(int(float(startHourDateTime))).zfill(2)  + ':00:00'

    start = date
    if not startHourDateTime == 'nan':
        start = start + ' ' + startHourDateTime
    else:
        start = start + ' ' + '00:00:00'
        
    #print("Start Time: " + start)
    start = str(int(datetime.strptime(start, '%Y-%m-%d %H:%M:%S').timestamp()))
    return start

def get_org(df, i):
    org = df.loc[i, 'organization']
    org = str(org).replace('\n', '')
    if org == 'nan':
        return "Data Unavailable"
    return org

def get_evt_name(df, i):
    name = df.loc[i, 'eventName']
    name = str(name).replace('\n', '')
    if name == 'nan':
        return "Data Unavailable"
    return name
    
def get_location(df, i):
    
    loc = df.loc[i, 'eventLocation']
    loc = re.findall(r".+,\s+\S+\s+\d{5}", str(loc).replace('\n', ''))
    if loc:
        #print(loc[0])
        return loc[0]
    else:
        return "Data Unavailable"
    
    
                    


if __name__ == "__main__":
    main()