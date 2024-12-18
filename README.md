# Répartition de ressource dans une colonie

## Comment Utiliser

### Option 1 : Utilisation du Makefile

Voici les commandes disponibles :

- **Compilation** :
  ```bash
  make compilation

- **Run** :
  ```bash
  make run

- **Clear** :
  ```bash
  make clear

### Option 2 : Utilisation du terminal

Voici les commandes disponibles :

- **Compilation** :
  ```bash
  javac src/main/java/**/*.java -d bin

- **Run** :
  ```bash
  java -cp bin code.Main


## Exemple d'exécution

Pour tester le programme il est possible d'exécuter le programme sur des colonies de test.

- **Colonie20.txt**:
  ```bash
  java -cp bin code.Main fichierTXT/Colonie20.txt

- **Colonie30.txt**:
  ```bash
  java -cp bin code.Main fichierTXT/Colonie30.txt

- **config.txt**:
  ```bash
  java -cp bin code.Main fichierTXT/config.txt

- **equipage1**:
  ```bash
  java -cp bin code.Main fichierTXT/equipage1

- **equipage2**:
  ```bash
  java -cp bin code.Main fichierTXT/equipage2

- **equipage3**:
  ```bash
  java -cp bin code.Main fichierTXT/equipage3
  