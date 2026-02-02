# Répartition de Ressources dans une Colonie Spatiale

[English version](README.md)

## Description

Ce projet traite le problème de l'allocation équitable de ressources entre les colons d'une colonie spatiale. Le système vise à distribuer les ressources aux colons tout en minimisant la jalousie et en maximisant la satisfaction globale, en fonction des préférences de chaque colon.

Le projet implémente plusieurs algorithmes pour trouver des solutions optimales ou quasi-optimales à ce problème d'allocation, en tenant compte de contraintes telles que l'unicité des ressources et la satisfaction des préférences.

## Fonctionnalités

- Initialisation manuelle et automatique de la colonie
- Plusieurs algorithmes d'allocation de ressources :
  - Solution naïve (approche gloutonne)
  - Amélioration itérative par échanges
  - Algorithmes d'optimisation
- Gestion des préférences pour chaque colon
- Calcul et minimisation de la jalousie
- Chargement de fichiers de configuration
- Sauvegarde et chargement de solutions
- Système de menu interactif

## Technologies

- **Langage** : Java 17
- **Outil de build** : Maven
- **Tests** : JUnit Jupiter
- **Système de build** : Make (optionnel)

## Prérequis

- Java Development Kit (JDK) 17 ou supérieur
- Maven 3.6 ou supérieur (pour les builds Maven)
- Make (optionnel, pour l'utilisation du Makefile)

## Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/thmsgo18/Projet-PAA.git
cd Projet-PAA
```

2. Compiler le projet :

### Avec Maven :
```bash
mvn clean compile
```

### Avec Make :
```bash
make compilation
```

### Avec les commandes Java :
```bash
javac src/main/java/**/*.java -d bin
```

## Utilisation

### Mode Interactif

Lancer le programme sans arguments pour accéder au menu interactif :

```bash
# Avec Maven
mvn exec:java -Dexec.mainClass="code.Main"

# Avec Make
make run

# Avec Java
java -cp bin code.Main
```

### Mode Fichier de Configuration

Lancer le programme avec un fichier de configuration :

```bash
java -cp bin code.Main <chemin-vers-fichier-config>
```

### Fichiers d'Exemple

Le projet inclut plusieurs fichiers de configuration de test :

```bash
# Petites colonies (20 colons)
java -cp bin code.Main src/main/fichierTXT/Colonie20.txt

# Colonies moyennes (30 colons)
java -cp bin code.Main src/main/fichierTXT/Colonie30.txt

# Configurations personnalisées
java -cp bin code.Main src/main/fichierTXT/config.txt
java -cp bin code.Main src/main/fichierTXT/equipage1
java -cp bin code.Main src/main/fichierTXT/equipage2
java -cp bin code.Main src/main/fichierTXT/equipage3
```

## Format des Fichiers de Configuration

Les fichiers de configuration doivent suivre cette structure :

```
colon(nom).
ressource(nom).
preferences(nomColon,ressource1,ressource2,...).
deteste(colon1,colon2).
```

Exemple :
```
colon(Alice).
colon(Bob).
ressource(Eau).
ressource(Nourriture).
preferences(Alice,Eau,Nourriture).
preferences(Bob,Nourriture,Eau).
deteste(Alice,Bob).
```

## Structure du Projet

```
Projet-PAA/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── code/
│   │   │   │   ├── Main.java          # Point d'entrée
│   │   │   │   ├── Colonie.java       # Gestion de la colonie
│   │   │   │   ├── Colon.java         # Représentation d'un colon
│   │   │   │   ├── Ressource.java     # Représentation d'une ressource
│   │   │   │   ├── Algo.java          # Algorithmes d'allocation
│   │   │   │   ├── Fichier.java       # Gestion des fichiers
│   │   │   │   └── Menu.java          # Menu interactif
│   │   │   └── exception/             # Exceptions personnalisées
│   │   └── fichierTXT/                # Fichiers de configuration
│   └── test/
│       └── java/                      # Tests unitaires
├── pom.xml                            # Configuration Maven
├── Makefile                           # Automatisation du build
└── README.fr.md                       # Ce fichier
```

## Tests

Exécuter les tests avec Maven :

```bash
mvn test
```

## Algorithmes

Le projet implémente plusieurs stratégies d'allocation :

1. **Solution Naïve** : Allocation gloutonne basée sur les premières préférences
2. **Amélioration par Échanges** : Optimisation itérative par échanges de ressources
3. **Minimisation de la Jalousie** : Algorithmes focalisés sur la réduction de la jalousie entre colons

## Gestion des Erreurs

Le système gère divers cas d'erreur :
- Fichiers de configuration invalides
- Ressources ou colons manquants
- Entrées dupliquées
- Violations de contraintes

Des fichiers de configuration d'erreur sont disponibles pour les tests dans `src/main/fichierTXT/config_error*.txt`.

## Commandes Make

```bash
make compilation    # Compiler le projet
make run           # Exécuter le projet en mode interactif
make clear         # Nettoyer les fichiers compilés
```

## Auteur

[thmsgo18](https://github.com/thmsgo18)
