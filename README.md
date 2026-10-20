# Secret Key Encryption — Dictionary Attack on AES

Course project for **Information Security**, demonstrating a dictionary-based key recovery attack on AES encryption.

## Overview

A ciphertext was produced by encrypting a known plaintext (`"This is a top secret."`) with AES in CBC mode, using a secret key and a known IV. The project recovers that secret key by trying every word in a dictionary file as a candidate key, re-encrypting the plaintext with each candidate, and comparing the result to the target ciphertext.

## How It Works

1. Read candidate keys line by line from `words.txt`.
2. Pad any candidate shorter than 16 bytes with `#` characters, since AES keys must be a fixed length.
3. Encrypt the known plaintext with the candidate key and the known IV (AES/CBC/PKCS5Padding).
4. Compare the resulting ciphertext (in hex) to the target ciphertext.
5. When a match is found, decrypt the ciphertext with that key and print the recovered key and plaintext.

## Files

- `Task7.java` — the key-guessing program
- `words.txt` — dictionary of candidate keys
- `Information_security_Alshaimaa_Alamer_project.docx` — project write-up

## Running It

```bash
javac Task7.java
java Task7
```

Make sure `words.txt` is in the same directory when running.

## Note

This project was built for a university course on information security, to demonstrate why weak, dictionary-guessable keys make encryption vulnerable even when the algorithm itself (AES) is strong.
