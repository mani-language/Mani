## Getting started.

### Install
Installing Máni can be very easy.

Simply execute the following commands below to get Máni.
```bash
    git clone https://github.com/crazywolf132/Mani
    cd Mani
    ./gradle build
```
This will create a folder called `build` and inside that, `libs`. This is where the Mani jar file is. From here, you can do what you please.

### Editors
For your enjoyment, we have created syntaxing for some editors.
* [Atom](https://github.com/crazywolf132/Mani-Atom)
* [VSCode](https://github.com/crazywolf132/Mani-vscode)

### Usage
To run a `.mni` file run the command below.
```bash
    java -jar Mani-Stable.jar filename.mni
```

To run the REPL simply perfom the following command:
```bash
    java -jar Mani-Stable.jar
```


### Dead simple.
A simple Hello, world script in Máni looks like this.
~~~ mani
    say "Hello, World!";
~~~