from password_manager import PasswordManager

def main(master_key):
    print("Welcome to the Password Manager!")
    #master_key = input("Enter your master key: ")
    manager = PasswordManager(master_key)

    while True:
        print("\n1. Add Password\n2. Get Password\n3. List Services\n4. Exit")
        choice = input("Choose an option: ")

        if choice == "1":
            service = input("Enter service name: ")
            username = input("Enter username: ")
            password = input("Enter password: ")
            manager.add_password(service, username, password)
            print(f"Password for {service} added successfully!")
        elif choice == "2":
            service = input("Enter service name: ")
            password = manager.get_password(service)
            if password:
                print(f"Password for {service}: {password}")
            else:
                print(f"No password found for {service}.")
        elif choice == "3":
            services = manager.list_services()
            print("Stored services:")
            for service in services:
                print(f"- {service}")
        elif choice == "4":
            print("Exiting Password Manager. Goodbye!")
            break
        else:
            print("Invalid choice. Try again.")