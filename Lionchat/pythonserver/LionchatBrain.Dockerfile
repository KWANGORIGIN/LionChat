FROM python:3.8-slim

WORKDIR /pythonserver

COPY requirements.txt ./
RUN --mount=type=cache,target=/root/.cache pip install -r requirements.txt

COPY ./output ./output
COPY ./ner.py ./
COPY ./tox.py ./
COPY ./IT_Knowledge_Articles.csv ./
COPY ./intent_classifier.py ./
COPY ./semantic_searcher.py ./
COPY ./LionchatBrain.py ./

CMD python -u LionchatBrain.py

EXPOSE 8000