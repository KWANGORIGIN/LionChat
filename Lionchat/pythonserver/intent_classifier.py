# -*- coding: utf-8 -*-
"""
Created on Sat Apr  2 17:28:07 2022

@author: wanga
"""
import spacy

nlp = None

def init():
    global nlp
    
    try:
        nlp = spacy.load("./intent-classifier-model/model-best")
    except:
        print("Unable to load intent classifier model")

def classifyIntent(question):
    
    if nlp is not None:
        doc = nlp(question)
        return max(doc.cats, key = doc.cats.get)
   
    return "Search_Intent"