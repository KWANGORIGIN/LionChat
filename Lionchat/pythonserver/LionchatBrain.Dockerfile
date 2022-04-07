FROM tiangolo/uwsgi-nginx-flask:python3.8

WORKDIR /pythonserver

COPY ./requirements.txt ./
RUN --mount=type=cache,target=/root/.cache pip install -r requirements.txt

RUN python -m nltk.downloader stopwords

COPY ./intent-classifier-model ./intent-classifier-model
COPY ./output ./output
COPY ./ner.py ./
COPY ./tox.py ./
COPY ./intent_classifier.py ./
COPY ./semantic_searcher.py ./
COPY ./LionchatBrain.py ./

CMD python -u LionchatBrain.py
ENV LISTEN_PORT 8000
EXPOSE 8000