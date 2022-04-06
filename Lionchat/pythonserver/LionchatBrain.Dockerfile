FROM tiangolo/uwsgi-nginx-flask:python3.8

WORKDIR /pythonserver

COPY ./requirements.txt ./
RUN --mount=type=cache,target=/root/.cache pip install -r requirements.txt

RUN python -m nltk.downloader stopwords

# COPY . .
# CMD python -u LionchatBrain.py

COPY . .

ENV LISTEN_PORT 8000
EXPOSE 8000