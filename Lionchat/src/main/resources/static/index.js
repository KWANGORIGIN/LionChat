var xhr = new XMLHttpRequest();
xhr.open("POST", "http://localhost:8080/chat/askquestion/");
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.send(JSON.stringify({question: "Question"}));
