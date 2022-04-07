# -*- coding: utf-8 -*-
"""
Created on Sat Apr  2 19:15:20 2022

@author: wanga
"""

# -*- coding: utf-8 -*-
"""
Created on Sat Apr  2 17:28:07 2022

@author: wanga
"""
from sentence_transformers import SentenceTransformer
from sklearn.neighbors import NearestNeighbors
import pandas as pd
import numpy as np

similarity_searcher = None
embedder_model = None
articleUrls = []
articleTitles = []

def generate_corpus_embeddings():
    #Read values from csv
    df = pd.read_csv('IT_Knowledge_Articles.csv')
    
    #Get article titles from column
    global articleTitles, articleUrls
    articleTitles = df.ArticleName.values
    articleUrls = df.ArticleLink.values
    
    return embedder_model.encode(articleTitles, convert_to_numpy=True)

def init():
    global similarity_searcher
    global embedder_model
    
    embedder_model = SentenceTransformer('./output/all-MiniLM-L6-v2')
    
    corpus_embeddings = generate_corpus_embeddings()
    
    similarity_searcher = NearestNeighbors(n_neighbors=5)
    
    similarity_searcher.fit(corpus_embeddings)

def search(query):
    global similarity_searcher
    global embedder_model
    global articleTitles
    global articleUrls
    
    similarities = []
    positions = []
    results = {'titles':[], 'urls': []}
    if similarity_searcher is not None:
        #Convert query to embedding
        query_embedding = embedder_model.encode(query, convert_to_numpy=True)
        
        #Reshape corpus embedding for input in KNN
        query_embedding = np.reshape(query_embedding, (1, 384))
        
        #KNN semantic search
        similarities, positions = similarity_searcher.kneighbors(query_embedding, return_distance=True)
        similarities = similarities[0]
        positions = positions[0]
        
        #Get results
        for position in positions:
            results['titles'].append(articleTitles[position])
            results['urls'].append(articleUrls[position])
            
    return results