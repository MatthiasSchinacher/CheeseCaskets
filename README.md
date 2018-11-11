# CheeseCaskets
Implementation of the paper based game "Käsekästchen"

The German game [Käsekästchen](https://de.wikipedia.org/wiki/K%C3%A4sek%C3%A4stchen) is a paper and pencil game everybody loved to play in school back in the day, whenever the subject was boring. It is similar, I am told, to [Dots and Boxes](https://en.wikipedia.org/wiki/Dots_and_Boxes).

This is a just for fun project to bring back old memories to current technology :-).

# Game description
TODO

# Dependencies
## JGoodies
The project depends on 2 JGoodies libraries (Forms and Common).
[You can download](http://www.jgoodies.com/downloads/archive/) free versions of these.
I used
- JGoodies Forms 1.8.0
- JGoodies Common 1.8.1

The downloadable ZIP- archives contain also source, documentation and test-utils, for this project we only need the
jar- archives within these, e.g. "jgoodies-forms-1.8.0.jar" and "jgoodies-common-1.8.1.jar".

## Java
As the game is written in Java, a Java runtime and compiler with Swing installed is needed.
I tested it with a jdk 1.8.0_191 on Linux (a Ubuntu 18.04 variant, AMD64).

# Compile and run
Place the 2 JGoodies jar- files within the project folder, and on a Linux (bash) command- line, go to the project folder

    javac -d ./bin -sourcepath ./src -cp ./jgoodies-forms-1.8.0.jar src/de/schini/cheesecaskets/*.java
    java -cp ./jgoodies-forms-1.8.0.jar:./jgoodies-common-1.8.1.jar:./bin de.schini.cheesecaskets.CheeseCasketsSwing
