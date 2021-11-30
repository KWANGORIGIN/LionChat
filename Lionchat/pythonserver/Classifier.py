from flask import Flask, send_from_directory, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

#Send index.html
@app.route('/', methods=['GET'])
@app.route('/index.html', methods=['GET'])
def get_index():
    #return contents from index.html
    return send_from_directory('', 'index.html', mimetype='text/html')

#Send main.js
@app.route('/main.js', methods=['GET'])
def get_main():
    #return contents from main.js
    return send_from_directory('', 'main.js', mimetype='text/javascript')

@app.route('/intent', methods=['POST'])
def getIntent():
    
    #Get POST request from client
    receivedDict = request.get_json()
    print(receivedDict)
    
    #Get user utterance
    userInput = []
    userInput.append(receivedDict["utterance"])
    
    return jsonify(intent="")
    
if __name__ == "__main__":
    #Run server
    app.run(port = 8000)