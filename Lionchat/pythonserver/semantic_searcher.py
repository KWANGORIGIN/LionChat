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
from sentence_transformers import SentenceTransformer, util
import pandas as pd

embedder_model = None
corpus_embeddings = None
articleUrls = []
articleTitles = []

def generate_corpus_embeddings():
    #Read values from csv
    df = pd.read_csv('IT_Knowledge_Articles.csv')
    
    #Get article titles from column
    global articleTitles, articleUrls
    articleTitles = df.ArticleName.values
    articleUrls = df.ArticleLink.values
    
    return embedder_model.encode(articleTitles, convert_to_tensor=True, normalize_embeddings=True)

def init():
    global embedder_model
    global corpus_embeddings
    
    embedder_model = SentenceTransformer('./output/multi-qa-MiniLM-L6-dot-v1')
    
    corpus_embeddings = generate_corpus_embeddings()
    print("Semantic Searcher Loaded")
    
def search(query):
    global embedder_model
    global corpus_embeddings
    global articleTitles
    global articleUrls
    
    #Encode query and run semantic search
    results = {'titles': [], 'urls': []}
    query_embedding = embedder_model.encode([query], convert_to_tensor=True, normalize_embeddings=True)
    corpus_results = util.semantic_search(query_embedding, corpus_embeddings, top_k=5, score_function=util.dot_score)
    result_list = corpus_results[0]
    
    #Generate results with article title and associated url
    for result_dict in result_list:
        position = result_dict['corpus_id']
        results['titles'].append(articleTitles[position])
        results['urls'].append(articleUrls[position])
        
    return results