# Use an official Python runtime as an image
FROM python:3.8-slim

# The EXPOSE instruction indicates the ports on which a container
EXPOSE 8000

# Sets the working directory for following COPY and CMD instructions
# Notice we haven’t created a directory by this name - this instruction 
# creates a directory with this name if it doesn’t exist
WORKDIR /app

# Install any needed packages specified in requirements.txt
COPY requirements.txt /app
RUN --mount=type=cache,target=/root/.cache pip install -r requirements.txt

# Run app.py when the container launches
COPY server.py /app
CMD python server.py