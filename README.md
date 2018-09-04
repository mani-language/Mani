
<p align="center">
<img src="docs/assets/images/logo_outlined.png" height="200px" alt="Máni" title="Máni">
</p>

<b>Version</b> <small>1.0.0</small>

> An awesome, super simple language!

* Simple and lightweight
* No external dependencies
* Straight recursive descent paser

[![CircleCI](https://circleci.com/gh/crazywolf132/MoonLang.svg?style=svg)](https://circleci.com/gh/crazywolf132/MoonLang)

## What is it?
Máni is an interpreted language that is simple to learn, and easy to use.

The idea behind it is, take some of the good parts of other languages like nodejs, python etc.
Implement them into this, and try and build an "Alpha" language... if that is even a such thing.

# Importing and Loading
## Importing
~~~ moon
import("lists"); // Loads the lists library
import("files"); // Loads the files library
~~~
## Loading
~~~ moon
load("somefile.moon"); // Loads the local file called "somefile.moon" if it exists.
~~~

> Whats the difference? <br>
The difference is, Importing loads the standard libraries, whereas Loading loads local files


# Standard Library
## Lists

~~~ moon
let l = List("String");   // Creates a String list
let obj = List("Object"); // Creates an Object list
~~~

### Displaying the list

~~~ moon
say l;      // Will show: List instance
l.show();   // Will show: []
~~~

### Adding items to the list

~~~ moon
l.add("some string");  // This will add "some string" to the String list

say l; // Will now show [some string]

l.add("something else"); // This will add "something else" to the list

l.show(); // Will now show [some string, something else]
~~~

### Replacing a string

~~~ moon
// Usage is: list.replace(position, string);
l.replace(1, "banana");

l.show(); // We will now see... [some string, banana]
~~~

### Deleting a position

~~~ moon
// Usage is: list.delete(position);
l.delete(1);

l.show(); // Will now show [some string]
~~~

### Reset the list
~~~ moon
// Usage is: list.reset();
l.reset();

l.show(); // Will now once again show []
~~~

### Triming items in list
~~~ moon
// Usage is: list.trimEach();

// first we will add some strings to work with.
l.add("        whitespace");
l.add("after     ");
l.show(); // List will show [        whitespace, after     ]

// now to trim the strings.
l.trimEach();
l.show(); // The list will now look like this: [whitespace, after]
~~~

### Direct Listing
~~~ moon
// THIS FEATURE IS ONLY IF YOU KNOW HOW TO USE THE LANGUAGE API

// Usage list.direct(non_list_object);

// First we need to create a direct list from the api.
let d = list("String");
let norm = List("String");

say d;      // This will show: []
say norm;   // This will show: List instance

norm.direct(d);

// Any information that was in the "d" list. Will now be in the "norm" list.
~~~

> * The difference between a normal list, and one made by the API, is the uppercase L... As you may see.
* I, as the creator of this language... Surgest using the Standard Library list, over the api version. This is due to being able to use the prebuilt functions such as add, replace etc, with ease.


## Maps
~~~ moon
let smap = Map("String"); // Creates a String map
let omap = Map("Object"); // Creates an Object map
~~~

### Adding items
~~~ moon
// Usage : mapItem.add(key, value);

smap.add("firstname", "Brayden"); // This will add a key value pair to the String map

// You can do the same with an object, to the object map.
~~~
> The key must always be a string.

### Showing the map
~~~ moon
// Usage : mapItem.show();

say smap; // This will only say: Map instance
smap.show(); // Will show you the contents of the map.
~~~

### Get map item
~~~ moon
// Usage : mapItem.get(key);

smap.get("firstname");      // This will not display anything, as it returns the information instead.
say smap.get("firstname");  // This will say : Brayden

let res = smap.get("firstname"); // This will assign the result "Brayden" to the variable "res"
~~~

> Once again, the key will always be a string

## Files
~~~ moon
// Usage File(filename);

let f = File("file.txt"); // This will create a file instance.
~~~

### Loading the file
~~~ moon
f.load(); // Reads the file that we designated earlier.
~~~
> This does not return the contents of the file or anything.

### Seeing file contents
~~~ moon
// After running f.load();

say f.contents; // This will return exactly what was in the file.
~~~

### Splitting to list
~~~ moon
// Make sure file is loaded first.

// Usage file.toList(listObj);

let l = List("String"); // Used to store the file contents

f.toList(l); // Will split by each line, to the list object.

list.show(); // Will display an array with the file contents, split by each line.
~~~

> The file will only be split by the line.

# Basics
## Comments, Variables, Value Types and Assignment
~~~~ moon
// This is a comment
~~~~

~~~ moon
let a;      // declare and assign nil to a
let b = 50; // declare and assign 50 to b

~~~

~~~~ moon
let a = nil;    // value nil
let a = true;   // boolean true
let a = false;  // boolean false
let a = 123;    // number
let a = 123.32; // number
let a = "hello"; // string
~~~~

~~~~ moon
a = 32;   // assign to 32
a = true; // assign a to true
~~~~
> * Variables are always mutable.
* Global variables can be re-declared but local variables can't be.

## Operators

~~~~ moon
let i = 1;

say i++;                // 2
say i--;                // 0
say i += 1;             // 2
say i -= 1;             // 0
say 1 + 2;              // 3
say 1 - 2;              // -1
say 5 * 30;             // 150
say 30/6;               // 5
say 22.0/7.0;           // 3.142857142857143
say "hello" + "hi";     // hellohi
say 1 + "one";          // 1one
say "hello" * 5;        // prints hello five times
say 5 * "hello";        // Runtime Error
~~~~

## The print Statement

~~~~ moon
say "hello"; // prints hello
say 1+2+3;   //prints 6
~~~~

## Block Statement 

~~~~ moon
let x = 0;
{
    x = x + 1;
    say x;     // prints 1
    let y = "hey";
    say y;     // prints hey
}
say x;         //prints 1
say y;         // Undefined variable;
~~~~

## Conditonal statements

~~~~ moon
// if

if(a == 42) {
    say "solved";
}

// if-else

if(a == 42) {
    say "solved";
} else {
    say "failed";
}

~~~~

## Loops
 
~~~~ moon
// While Loop

while(a < 42) {
    a = a + 1;
}

while(true) say "infinite loop";

// for loop

for( let i = 0 ; i < 50 ; i = i + 1 ) {
    say i;
}

for(;;) say "infinite loop";
~~~~ 
## Break Keyword
~~~~ moon
let x = 0;
while(true) {
    say x;
    x = x + 1;
    if (x == 10) {
        break; 
    }
} // should print 1 .. 10
~~~~
## Functions

~~~~ moon
fn hello() {
    say "hello";
}

// parameters

fn greet(name) {
    say "Hello " + name;
}
~~~~

## Closures

~~~~ moon
// A function that returns updated `count` on every call
fn makeCounter() {
    let count = 0;
    fn increment() {
        count = count + 1;
        say count;
    }
    return increment;
}
~~~~

## Function calls

~~~~ moon
hello();      // without argument(s)
greet("Mir")  // with argument(s)
~~~~
## Inbuilt Functions

~~~~ moon
let rand   = random(10);            // Takes a number, n, and returns a random number between 0 and n
let name   = readString();          // reads a string from user
let number = readInt();             // reads a number from user otherwise `nil`
let result = ask("Who are you?");   // Prints "Who are you?" to console, and waits for text response.
~~~~
> Other built in functions can be found under the Stand Library section

## Classes

~~~~ moon
class Greeting {
    Greeting(name) { // The Constructor
        this.name = name; // Instance field
    }

    sayHello() { // Instance method
        return "Hello " + name;
    }

    sayNight() {
        return "Good Night " + name;
    }
}
~~~~
## Class Instance

~~~~ moon
let greeting = Greeting("John");
say greeeting.sayHello();     // prints Hello John
say greeting.sayNight();      // prints Good Night John
~~~~

> * A value cannot be retured from the constrcutor.

## Inheritance

~~~~ moon
class A {
    methodA1() {
        say "methodA1";
    }
}

class B <  A {
    methodB1() {
        super.methodA1();
        say "methodB1";
    }
}

let inst = B();
inst.methodB1();  // prints methodA1
                  //        methodB1
~~~~
