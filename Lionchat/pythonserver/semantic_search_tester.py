# -*- coding: utf-8 -*-
"""
Created on Sat Feb 19 15:51:41 2022

@author: Kevin Wang
"""
import LionchatBrain
import unittest
from parameterized import parameterized
import pandas as pd
import random

class SemanticSearchTester(unittest.TestCase):
    
    def setUp(self):
        LionchatBrain.app.testing = True
        self.app = LionchatBrain.app.test_client()
    
    def load_IT_test_cases():
        
        lineList = []
        with open('IT_Test_Cases.txt', 'r', encoding='utf-8') as IT_test_case_file:
            lineList = IT_test_case_file.readlines()
        
        questionDict = {}
        currentArticleName = ''
        for line in lineList:
            if line[0] == '#':
                articleName = line.split('#')
                articleName = articleName[1]
                articleName = articleName.strip()
                currentArticleName = articleName
                if articleName not in questionDict:
                    questionDict[articleName] = []
            else:
                questionDict[currentArticleName].append(line.strip())
        
        test_case_list = []
        for articleName in questionDict.keys():
            for testQuestion in questionDict[articleName]:
                test_case_list.append((testQuestion, articleName))
        
        return test_case_list
    
    @parameterized.expand(load_IT_test_cases)
    def test_semantic_search(self, question, expected):
        jsonData = {"query": question}
        result = self.app.post('/semantic-search-results', json=jsonData)
        resultList = result.get_json()['searchresults']
        
        self.assertEqual(resultList[0], expected)

#Generate random IT test case article names and write to file 
def generate_IT_test_cases(num_of_articles):
    df = pd.read_csv('IT_Knowledge_Articles.csv')
    
    articleTitles = df.ArticleName.values
    
    randomList = random.choices(articleTitles, k=num_of_articles)
    
    writeList = []
    for articleName in randomList:
        writeList.append(str(articleName) + '\n')
        
    with open("IT_Test_Cases.txt", "w", encoding="utf-8") as outputFile:
        outputFile.writelines(writeList)
        
if __name__ == "__main__":
    #Generate IT test case article names and write to file
    # generate_IT_test_cases(5)
    # load_IT_test_cases()
    
    unittest.main()