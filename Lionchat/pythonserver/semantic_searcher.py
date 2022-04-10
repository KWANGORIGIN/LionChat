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
from sklearn.neighbors import NearestNeighbors
import pandas as pd
import numpy as np

# similarity_searcher = None
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

# def init():
#     global similarity_searcher
#     global embedder_model
    
#     # embedder_model = SentenceTransformer('./output/all-MiniLM-L6-v2')
#     embedder_model = SentenceTransformer('./output/multi-qa-MiniLM-L6-dot-v1')
    
#     corpus_embeddings = generate_corpus_embeddings()
    
#     similarity_searcher = NearestNeighbors(n_neighbors=5)
    
#     similarity_searcher.fit(corpus_embeddings)

#     print("Semantic Searcher Loaded")
def init():
    global embedder_model
    global corpus_embeddings
    
    embedder_model = SentenceTransformer('./output/multi-qa-MiniLM-L6-dot-v1')
    
    corpus_embeddings = generate_corpus_embeddings()
    
def search(query):
    global embedder_model
    global corpus_embeddings
    global articleTitles
    global articleUrls
    
    results = {'titles': [], 'urls': []}
    query_embedding = embedder_model.encode([query], convert_to_tensor=True, normalize_embeddings=True)
    corpus_results = util.semantic_search(query_embedding, corpus_embeddings, top_k=5, score_function=util.dot_score)
    result_list = corpus_results[0]
    
    for result_dict in result_list:
        position = result_dict['corpus_id']
        results['titles'].append(articleTitles[position])
        results['urls'].append(articleUrls[position])
        
    return results

# def search(query):
#     global similarity_searcher
#     global embedder_model
#     global articleTitles
#     global articleUrls
    
#     similarities = []
#     positions = []
#     results = {'titles':[], 'urls': []}
#     if similarity_searcher is not None:
#         #Convert query to embedding
#         query_embedding = embedder_model.encode(query, convert_to_numpy=True)
        
#         #Reshape corpus embedding for input in KNN
#         query_embedding = np.reshape(query_embedding, (1, 384))
        
#         #KNN semantic search
#         similarities, positions = similarity_searcher.kneighbors(query_embedding, return_distance=True)
#         similarities = similarities[0]
#         positions = positions[0]
        
#         #Get results
#         for position in positions:
#             results['titles'].append(articleTitles[position])
#             results['urls'].append(articleUrls[position])
            
#     return results

if __name__ == "__main__":
    embedder_model = SentenceTransformer('./output/multi-qa-MiniLM-L6-dot-v1')
    corpus_embeddings = generate_corpus_embeddings()
    corpus_embeddings = util.normalize_embeddings(corpus_embeddings)

    query = ['How do I connect to the wifi']
    query_embedding = embedder_model.encode(query, convert_to_tensor=True)
    query_embedding = util.normalize_embeddings(query_embedding)
    hits = util.semantic_search(query_embedding, corpus_embeddings, top_k = 5, score_function=util.dot_score)
    print(hits)