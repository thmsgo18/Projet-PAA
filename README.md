# Resource Allocation in a Space Colony

[Version française](README.fr.md)

## Description

This project addresses the problem of fair resource allocation among colonists in a space colony. The system aims to distribute resources to colonists while minimizing jealousy and maximizing overall satisfaction, based on each colonist's preferences.

The project implements several algorithms to find optimal or near-optimal solutions to this allocation problem, considering constraints such as resource uniqueness and preference satisfaction.

## Features

- Manual and automatic colony initialization
- Multiple resource allocation algorithms:
  - Naive solution (greedy approach)
  - Iterative improvement through exchanges
  - Optimization algorithms
- Preference management for each colonist
- Jealousy calculation and minimization
- Configuration file loading
- Solution saving and loading
- Interactive menu system

## Technologies

- **Language**: Java 17
- **Build Tool**: Maven
- **Testing**: JUnit Jupiter
- **Build System**: Make (optional)

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher (for Maven builds)
- Make (optional, for Makefile usage)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/thmsgo18/Projet-PAA.git
cd Projet-PAA
```

2. Compile the project:

### Using Maven:
```bash
mvn clean compile
```

### Using Make:
```bash
make compilation
```

### Using Java commands:
```bash
javac src/main/java/**/*.java -d bin
```

## Usage

### Interactive Mode

Launch the program without arguments for the interactive menu:

```bash
# Using Maven
mvn exec:java -Dexec.mainClass="code.Main"

# Using Make
make run

# Using Java
java -cp bin code.Main
```

### Configuration File Mode

Launch the program with a configuration file:

```bash
java -cp bin code.Main <path-to-config-file>
```

### Example Files

The project includes several test configuration files:

```bash
# Small colonies (20 colonists)
java -cp bin code.Main src/main/fichierTXT/Colonie20.txt

# Medium colonies (30 colonists)
java -cp bin code.Main src/main/fichierTXT/Colonie30.txt

# Custom configurations
java -cp bin code.Main src/main/fichierTXT/config.txt
java -cp bin code.Main src/main/fichierTXT/equipage1
java -cp bin code.Main src/main/fichierTXT/equipage2
java -cp bin code.Main src/main/fichierTXT/equipage3
```

## Configuration File Format

Configuration files should follow this structure:

```
colon(name).
ressource(name).
preferences(colonName,ressource1,ressource2,...).
deteste(colon1,colon2).
```

Example:
```
colon(Alice).
colon(Bob).
ressource(Water).
ressource(Food).
preferences(Alice,Water,Food).
preferences(Bob,Food,Water).
deteste(Alice,Bob).
```

## Project Structure

```
Projet-PAA/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── code/
│   │   │   │   ├── Main.java          # Entry point
│   │   │   │   ├── Colonie.java       # Colony management
│   │   │   │   ├── Colon.java         # Colonist representation
│   │   │   │   ├── Ressource.java     # Resource representation
│   │   │   │   ├── Algo.java          # Allocation algorithms
│   │   │   │   ├── Fichier.java       # File handling
│   │   │   │   └── Menu.java          # Interactive menu
│   │   │   └── exception/             # Custom exceptions
│   │   └── fichierTXT/                # Configuration files
│   └── test/
│       └── java/                      # Unit tests
├── pom.xml                            # Maven configuration
├── Makefile                           # Build automation
└── README.md                          # This file
```

## Testing

Run tests using Maven:

```bash
mvn test
```

## Algorithms

The project implements several allocation strategies:

1. **Naive Solution**: Greedy allocation based on first preferences
2. **Exchange-based Improvement**: Iterative optimization through resource exchanges
3. **Jealousy Minimization**: Algorithms focused on reducing jealousy among colonists

## Error Handling

The system handles various error cases:
- Invalid configuration files
- Missing resources or colonists
- Duplicate entries
- Constraint violations

Error configuration files are available for testing in `src/main/fichierTXT/config_error*.txt`.

## Make Commands

```bash
make compilation    # Compile the project
make run           # Run the project in interactive mode
make clear         # Clean compiled files
```

## Author

- **thmsgo18** - [GitHub Profile](https://github.com/thmsgo18)

## Academic Context

This project was developed as part of the Advanced Algorithmics course (PAA - Projet d'Algorithmique Avancée) during the third year of Bachelor's degree (L3 S1).

## License

This project is an academic work. Please refer to your institution's academic integrity policies regarding code reuse.
  