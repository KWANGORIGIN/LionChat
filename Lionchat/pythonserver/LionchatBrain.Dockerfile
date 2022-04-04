FROM python:3.8-slim

RUN apt update && \
    apt install --no-install-recommends -y build-essential gcc default-libmysqlclient-dev && \
    apt clean && rm -rf /var/lib/apt/lists/*

WORKDIR /pythonserver

COPY requirements.txt ./
RUN --mount=type=cache,target=/root/.cache pip3 install -r requirements.txt

COPY . .

CMD [ "python3", "-u", "./LionchatBrain.py" ]
EXPOSE 8000