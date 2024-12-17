from ui.cli import main as cli_main
from ui.gui import main as gui_main

def main():
    print("Welcome to the Password Manager!")
    master_key = input("Enter your master key (this will secure your data): ").strip()

    # Pass the master_key to the CLI or GUI main function
    print("Choose your interface:")
    print("1. Command-Line Interface (CLI)")
    print("2. Graphical User Interface (GUI)")

    choice = input("Enter your choice (1/2): ").strip()
    
    if choice == "1":
        cli_main(master_key)
    elif choice == "2":
        gui_main(master_key)
    else:
        print("Invalid choice. Exiting.")

if __name__ == "__main__":
    main()
