# -*- coding: utf-8 -*-
"""
Created on Sun Feb 20 16:05:06 2022

@author: Kevin Wang
"""
import LionchatBrain
import unittest

class IntentClassifierTester(unittest.TestCase):
    
    def setUp(self):
        LionchatBrain.app.testing = True
        self.app = LionchatBrain.app.test_client()
        
    def test_intent_classifier(self):
        question = "How do I connect to the wifi on campus"
        jsonData = {"utterance": question}
        response = self.app.post('/intent', json=jsonData)
        classifiedIntent = response.get_json()['intent']
        print("Classified Intent: ", classifiedIntent)

if __name__ == "__main__":
    unittest.main()