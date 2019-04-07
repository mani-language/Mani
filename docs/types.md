## Types
Unlike some other languages (Java is a good example), Máni does not require the user to define what type a variable is going to be.

This results in the language being dynamically typed.

### Number
Naturally everything is a Double.
~~~ mani
    let a = 123;        // An int, but converted to a Double natrually
    let b = 456.789;    // A classic Double (AKA. Float)

    let c = Number.random(1, 5);    // Returns a random number between 1 - 5
~~~

> To use `Number.random()`, you must use the Number library.

### String
Strings are sentances of chracters sorrounded by double quotes.
~~~ mani
    let a = "Im a string!";     // Creates a new string.

    let len = a.length();       // Returns the length of the string as a Double.

    let b = a.posOf("a");       // b is now 3.

    b = a.count("i");           // b is now 2.
    b = a.count("im");          // b is now 1.

    b = a.repeat("E", 5);       // b is now "EEEEE".

    // Naturally strings can use Upper and Lower functions.
    // When no arguments are passed, the entire string will be effected.
    b = a.upper();              // b is now "IM A STRING!".
    b = a.lower();              // b is now "im a string!".

    // You can also pass as many arguments as there is characters in the string.
    // This will then tell Máni which characters to change.
    b = a.upper(1, 3, 5, 7);    // b is now "iM A StRing!"
    // Same applies for Lower();


    // Strings can also be split into lists.
    let c = "WOW IM A STRING".split(" ");   // returns a list, split by " ".
    c.contains("WOW");      // true
    c.contains("NO");       // false

    // Replacing characters.
    let test = "dad";
    test.replace("d", "m"); // returns "mam"

    // You can also use maps in replace.

    test = "dad";
    test.replace({ "d" : "m", "a" : "o" });     // returns "mom";
~~~

> To perform list operations, the list library must be imported.

### Boolean
Booleans can only allow 2 values. true and false.

~~~ mani
    let t = true;
    let f = false;
~~~

### Nil
Nil (AKA. Null in other languages), represents the lack of a value.
~~~ mani
    class Math {
        Math() {
            this.a = 10;
            this.b;
        }

        calc() {
            return this.a * this.b;
        }
    }

    fn withClass() {
        var m = Math();
        // b in Math has not been set, so the calculation cannot be performed.
        // This means that Nil will be returned.
        return m.calc();
    }

    fn normal() {
        let a;

        return a;                       // Will return Nil, as a has no set value.
    }
~~~