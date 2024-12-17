import json

FILE_PATH = "passwords.json"

def load_data():
    try:
        with open(FILE_PATH, 'r') as file:
            return json.load(file)
    except FileNotFoundError:
        return {}

def save_data(data):
    with open(FILE_PATH, 'w') as file:
        json.dump(data, file)
