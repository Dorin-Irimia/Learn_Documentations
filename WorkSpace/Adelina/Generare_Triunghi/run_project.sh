#!/bin/bash

# Verifică dacă scriptul rulează deja
LOCK_FILE="/tmp/project_run.lock"

if [ -e "$LOCK_FILE" ]; then
    echo "Scriptul rulează deja! Ieșire..."
    exit 1
fi

# Creează fișierul de blocare
touch "$LOCK_FILE"

# Curăță fișierul de blocare când scriptul se termină
trap "rm -f $LOCK_FILE" EXIT

# Compilează proiectul
echo "Compilare Triunghi.java..."
javac Triunghi.java
if [ $? -ne 0 ]; then
    echo "Eroare la compilare!"
    exit 1
fi

# Rulează proiectul
echo "Rulez Triunghi..."
java Triunghi
