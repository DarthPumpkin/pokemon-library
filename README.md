# Pokémon Library
This library implements core functionality from the Pokémon game series. It is highly reusable and portable. It fits into any Android application but does its job equally well as the backbone of a battle server with thousands of users.

Main features include:
* Battle engine for single battles, double battles, triple battles and any other arbitrary player constellation
* Extendable AI framework with player implementations mimicking the intelligence level of wild pokemons, trainers and gym leaders
* Safe and efficient data structures for all basic entities

# Current state
This project is in its early development phase. Soon it will be possible to simulate a simple battle.

# How to run
Gradle makes things easier for you, but is not required. Clone this repository with the `--recursive` flag, this will fetch the subrepositories for you (see [here](http://git-scm.com/book/en/v2/Git-Tools-Submodules#Cloning-a-Project-with-Submodules) for more). Run `gradle eclipse` if you would like Gradle to generate the Eclipse project files for you. To run the unit tests, do `gradle test` or just execute the JUnit Test Suite `AllTests.java`.
