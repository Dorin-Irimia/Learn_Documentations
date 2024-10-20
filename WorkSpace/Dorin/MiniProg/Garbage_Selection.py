import os
import platform
import sys
from datetime import datetime
import winshell

# Verificăm platforma curentă
def get_recycle_bin_path():
    current_platform = platform.system().lower()

    if current_platform == "windows":
        return winshell.recycle_bin()
    elif current_platform == "darwin":  # macOS
        return os.path.expanduser("~/.Trash")
    elif current_platform == "linux":
        return os.path.expanduser("~/.local/share/Trash/files")
    else:
        raise NotImplementedError(f"Platforma {current_platform} nu este suportată.")

# Funcție pentru a verifica dacă un folder este gol
def is_folder_empty(path):
    return len(list(path)) == 0

# Funcție pentru a obține data ștergerii (deocamdată disponibilă doar pe Windows)
def get_deletion_date(file, recycle_bin):
    if platform.system().lower() == "windows":
        for item in recycle_bin:
            if item.filename() == file:
                return item.recycle_date()
    # Pentru macOS și Linux, această funcționalitate nu este disponibilă ușor
    return "Unknown"

# Funcție pentru a sorta fișierele din Recycle Bin
def sort_recycle_bin(criterion="name"):
    recycle_bin_path = get_recycle_bin_path()

    # Verificăm dacă Recycle Bin este gol
    if is_folder_empty(recycle_bin_path):
        print("\nRecycle Bin is empty\n")
        sys.exit()

    # Obținem toate fișierele din Recycle Bin
    if platform.system().lower() == "windows":
        recycle_bin = winshell.recycle_bin()
        files = [(item.filename(), item.recycle_date()) for item in recycle_bin]
    else:
        files = [(file, get_deletion_date(file, recycle_bin_path)) for file in os.listdir(recycle_bin_path)]

    # Grupăm fișierele în funcție de extensie
    grouped_files = {}
    for file, date in files:
        ext = os.path.splitext(file)[1].lower()
        if ext not in grouped_files:
            grouped_files[ext] = []
        grouped_files[ext].append((file, date))

    # Sortăm fiecare grup de fișiere în funcție de criteriul ales
    sorted_groups = {}
    for ext, file_list in grouped_files.items():
        if criterion == "name":
            sorted_files = sorted(file_list, key=lambda x: x[0].lower())
        elif criterion == "date":
            sorted_files = sorted(file_list, key=lambda x: x[1], reverse=True)
        else:
            raise ValueError("Criteriul de sortare nu este valid. Alege 'name' sau 'date'.")
        sorted_groups[ext] = sorted_files

    # Afișăm fișierele sortate pe tipuri (extensii)
    for ext, sorted_files in sorted_groups.items():
        print(f"\nExtensie: {ext if ext else 'Folder'}")
        for file, date in sorted_files:
            print(f"  {file} - Deleted on: {date}")

# Exemplu de rulare: sortare după "date"
sort_recycle_bin(criterion="date")
