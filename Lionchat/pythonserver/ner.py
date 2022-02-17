# -*- coding: utf-8 -*-
"""
Created on Wed Feb 16 17:56:06 2022

@author: willh
"""

import spacy

nlp = None

def init():
    global nlp 
    usingGPU = spacy.prefer_gpu()
    if usingGPU:
        print("Using GPU")
    else:
        print("Not using GPU")
        
    nlp = spacy.load("./nerModel/modified-model")
    print('Model loaded.')
    
    
def getEnts(text):
    global nlp
    text = str.lower(text)
    doc = nlp(text)
    return doc.ents
    