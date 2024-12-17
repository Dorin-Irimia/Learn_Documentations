import tkinter as tk
from tkinter import messagebox

import base64
from cryptography.fernet import Fernet

import json

FILE_PATH = "passwords.json"

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


def add_password():
    service = service_entry.get()
    username = username_entry.get()
    password = password_entry.get()
   

    if service and username and password:
        manager.add_password(service, username, password)
        messagebox.showinfo("Success", f"Password for {service} saved!")
    else:
        messagebox.showerror("Error", "All fields must be filled out.")
        
def retrieve_password():
    service = service_entry.get()
    if service:
        password = manager.get_password(service)
        if password:
            messagebox.showinfo("Password Retrieved", f"Password for {service}: {password}")
        else:
            messagebox.showerror("Error", f"No password found for service: {service}")
    else:
        messagebox.showerror("Input Error", "Service name must be provided.")

def generate_key(master_key):
    from hashlib import sha256
    hashed_key = sha256(master_key.encode()).digest()
    return Fernet(base64.urlsafe_b64encode(hashed_key))

def encrypt(data, master_key):
    cipher = generate_key(master_key)
    return cipher.encrypt(data.encode()).decode()

def decrypt(data, master_key):
    cipher = generate_key(master_key)
    return cipher.decrypt(data.encode()).decode()


def load_data():
    try:
        with open(FILE_PATH, 'r') as file:
            return json.load(file)
    except FileNotFoundError:
        return {}
    
def save_data(data):
    with open(FILE_PATH, 'w') as file:
        json.dump(data, file)




app = tk.Tk()
app.title("Password Manager")

tk.Label(app, text='Service').grid(row=0, column=0)
service_entry = tk.Entry(app)
service_entry.grid(row=0, column=1)

tk.Label(app, text='Username').grid(row=1, column=0)
username_entry = tk.Entry(app)
username_entry.grid(row=1, column=1)

tk.Label(app, text='Password').grid(row=2, column=0)
password_entry = tk.Entry(app)
password_entry.grid(row=2, column=1)

tk.Button(app, text='Add Password', command= add_password).grid(row=3, column=0)
tk.Button(app, text='Get Password', command= retrieve_password).grid(row=3, column=1)

tk.Label(app, text='AdminPassword').grid(row=4, column=0)
admin_entry = tk.Entry(app)
admin_entry.grid(row=4, column=1)

# master_key = "password"  # Use a secure method to set this in production!
master_key = admin_entry.get()
manager = PasswordManager(master_key)
app.mainloop()