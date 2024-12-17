import base64
from cryptography.fernet import Fernet

def generate_key(master_key):
    from hashlib import sha256
    hashed_key = sha256(master_key.encode()).digest()  # Create a 32-byte key
    return Fernet(base64.urlsafe_b64encode(hashed_key))

def encrypt(data, master_key):
    cipher = generate_key(master_key)
    return cipher.encrypt(data.encode()).decode()

def decrypt(data, master_key):
    cipher = generate_key(master_key)
    return cipher.decrypt(data.encode()).decode()
