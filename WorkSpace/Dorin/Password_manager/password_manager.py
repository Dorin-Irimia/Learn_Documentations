from encryption import encrypt, decrypt
from data_store import save_data, load_data

class PasswordManager:
    def __init__(self, master_key):
        self.master_key = master_key
        self.data = load_data()

    def add_password(self, service, username, password):
        encrypted_password = encrypt(password, self.master_key)
        self.data[service] = {"username": username, "password": encrypted_password}
        save_data(self.data)

    def get_password(self, service):
        if service in self.data:
            encrypted_password = self.data[service]["password"]
            return decrypt(encrypted_password, self.master_key)
        return None

    def list_services(self):
        return list(self.data.keys())
