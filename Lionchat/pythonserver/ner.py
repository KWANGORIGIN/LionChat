# -*- coding: utf-8 -*-
"""
Created on Wed Feb 16 17:56:06 2022

@author: willh
"""
from timeit import default_timer as timer
import spacy
import os
import threading

nlp = None

def init():
    global nlp 
    # usingGPU = spacy.prefer_gpu()
    # if usingGPU:
    #     print("Using GPU")
    # else:
    #     print("Not using GPU")
        
    try:
        nlp = spacy.load("./output/model-best-ner")
        print('NER model loaded.')
    except Exception as e:
        print(e)
        print('NER model not found.')
    
    
def getEnts(text):
    global nlp
    
    if nlp is not None:
        print("PID: ", os.getpid())
        print("Thread: ", threading.get_ident())
        print("Text: ", text)
        text = str.lower(text) 
        doc = nlp(text)
        return doc.ents
    else:
        return []
    
