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
        
    try:
        nlp = spacy.load("./output/model-best-toxic")
        print('Model loaded.')
    except:
        print('Model not found.')
    
    
def is_toxic(text):
    
    if nlp is not None:
        text = str.lower(text) 
        doc = nlp(text)
        print(doc.cats)
        return max(doc.cats, key= doc.cats.get)
    else:
        return "ERROR"
    
