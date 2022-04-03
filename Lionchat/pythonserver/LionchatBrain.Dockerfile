FROM python:3.8-slim

RUN apt update && \
    apt install --no-install-recommends -y build-essential gcc && \
    apt clean && rm -rf /var/lib/apt/lists/*

WORKDIR /pythonserver

COPY requirements.txt ./
RUN pip3 install -r requirements.txt

COPY . .

CMD [ "python3", "./LionchatBrain.py" ]
EXPOSE 8000