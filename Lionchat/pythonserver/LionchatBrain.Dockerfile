FROM python:3.8-slim

WORKDIR /pythonserver

COPY requirements.txt ./
RUN --mount=type=cache,target=/root/.cache pip install -r requirements.txt

COPY . .
CMD python -u LionchatBrain.py

EXPOSE 8000