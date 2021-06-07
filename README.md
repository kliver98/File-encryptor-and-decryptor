# File-encryptor-and-decryptor
Program that allows select a file and encrypt or decrypt by a password provided

<hr>

**ESPAÑOL:**

El programa debe tener dos opciones: 
1. **Cifrar archivo:** Debe recibir como entrada un archivo cualquiera, y una contraseña. A partir de la contraseña, debe generarse una clave de 128 bits, empleando el algoritmo PBKDF2. Por último, el archivo debe cifrarse con el algoritmo AES, usando la clave obtenida; el resultado debe escribirse a otro archivo, que debe contener también el hash SHA-1 del archivo sin cifrar.
1.  **Descifrado**: Debe recibir como entrada un archivo cifrado y la contraseña. El programa deberá descifrar el archivo y escribir el resultado en un archivo nuevo. Luego, debe computar el hash SHA-1 del archivo descifrado y compararlo con el hash almacenado con el archivo cifrado.

**ENGLISH:**

The program should have two options:
1. **Encrypt file:** It must receive as input any file, and a password. From the password, a 128-bit key must be generated, using the PBKDF2 algorithm. Finally, the file must be encrypted with the AES algorithm, using the obtained key; the result must be written to another file, which must also contain the SHA-1 hash of the unencrypted file.
1. **Decryption:** You must receive an encrypted file and password as input. The program will have to decrypt the file and write the result to a new file. Then you need to compute the SHA-1 hash of the decrypted file and compare it to the stored hash with the encrypted file.

<hr>

# Aclarations

1. When Cipher, the generated new file will be saved in the same path as original
1. SHA1 hash of original file will be saved in same path as original file with .txt extension
1. If only will be needed to decipher, there is no need to charge SHA1 hash. Otherwise you must charge the file with SHA1 hash or digit one
1. At the end of the work for cipher or decipher, a message will be shown indicating the status