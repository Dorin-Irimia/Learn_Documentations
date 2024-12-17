import tkinter as tk
from tkinter import messagebox
from password_manager import PasswordManager

def add_password():
    service = service_entry.get()
    username = username_entry.get()
    password = password_entry.get()

    if service and username and password:
        manager.add_password(service, username, password)
        messagebox.showinfo("Success", f"Password for {service} saved!")
    else:
        messagebox.showwarning("Input Error", "All fields are required!")

def retrieve_password():
    service = service_entry.get()
    if service:
        password = manager.get_password(service)
        if password:
            messagebox.showinfo("Password", f"Password for {service}: {password}")
        else:
            messagebox.showwarning("Not Found", f"No password found for {service}.")
    else:
        messagebox.showwarning("Input Error", "Service name is required!")

app = tk.Tk()
app.title("Password Manager")

tk.Label(app, text="Admin_code").grid(row=0, column=0)
admin_entry = tk.Entry(app)
service_entry.grid(row=0, column=1)

tk.Label(app, text="Service").grid(row=1, column=0)
service_entry = tk.Entry(app)
service_entry.grid(row=1, column=1)

tk.Label(app, text="Username").grid(row=2, column=0)
username_entry = tk.Entry(app)
username_entry.grid(row=2, column=1)

tk.Label(app, text="Password").grid(row=3, column=0)
password_entry = tk.Entry(app, show="*")
password_entry.grid(row=3, column=1)

tk.Button(app, text="Add Password", command=add_password).grid(row=4, column=0)
tk.Button(app, text="Get Password", command=retrieve_password).grid(row=4, column=1)

master_key = "Dorin"  # Use a secure method to set this in production!
manager = PasswordManager(master_key)

app.mainloop()
