
<p align="center">
<img src="docs/assets/images/logo_outlined.png" height="200px" alt="Máni" title="Máni">
</p>

<b>Version</b> <small>1.2.7</small>

> An awesome, super simple language!

* Simple and lightweight
* No external dependencies
* Straight recursive descent paser
* Multi-language support

[![](https://forthebadge.com/images/badges/built-by-codebabes.svg)](#)
[![](https://forthebadge.com/images/badges/made-with-crayons.svg)](#)
[![](https://forthebadge.com/images/badges/contains-technical-debt.svg)](#)
[![](https://forthebadge.com/images/badges/check-it-out.svg)](#)


## What is it?
Máni is an interpreted language that is simple to learn, and easy to use.

The idea behind it is, take some of the good parts of other languages like nodejs, c++, python etc.
Implement them into this, and try and build an "Alpha" language... if that is even a such thing.

# Importing, and Loading
## Importing
~~~ JS
# "lists";  // Imports the lists library
# "maps";   // Imports the maps library
~~~
> The `#` is used to import something from the STDLIB.
The name of the library needs to be presented as a string (wrapped in "")

## Loading
~~~ JS
load "std";     // Loads the std API
load "atomic";  // Loads the atomic API

load "./somefile.mn"  // Loads a local file.
~~~
> The load function will load a file before an API. This means, that if there
is a local file with the same name as an API. It will load that instead of the API
this can be useful for overloading how STDLIB's work. It can also be dangerous
for certain file names.



# Standard Library

## Important notes.
The standard library works in a way, that it doesn't require the library to be
on the machine running it. This can be helpful to minimise the footprint left
by the language.

The way it works, is. If you have an active internet connection. It will load
the latest version of the library from the web and use that. If you do not have
an active connection. It will look for the file (library) locally on the machine.
## Lists

~~~ JS
let l = List();   // Creates an Object list.
~~~
> This means that you can add anything to these lists.<br>
Anything from Functions, Numbers and Strings.


### Displaying the list

~~~ JS
say l;      // Will show: List instance
l.show();   // Will show: []
~~~

### Adding items to the list

~~~ JS
l.add("some string");  // This will add "some string" to the String list

say l; // Will now show [some string]

l.add("something else"); // This will add "something else" to the list

l.show(); // Will now show [some string, something else]
~~~

### Replacing a string

~~~ JS
// Usage is: list.replace(position, string);
l.replace(1, "banana");

l.show(); // We will now see... [some string, banana]
~~~

### Deleting a position

~~~ JS
// Usage is: list.delete(position);
l.delete(1);

l.show(); // Will now show [some string]
~~~

### Reset the list
~~~ JS
// Usage is: list.reset();
l.reset();

l.show(); // Will now once again show []
~~~

### Triming items in list
~~~ JS
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
~~~ JS

// Usage list.direct(non_list_object);

let d = [1, 2, 3, 4];
let norm = List("String");

say d;      // This will show: [1, 2, 3, 4]
say norm;   // This will show: []

norm.direct(d);

// Any information that was in the "d" list. Will now be in the "norm" list.

say norm;   // This will show: [1, 2, 3, 4]
~~~

### For Each
~~~ JS
// This function is used for iterating through a list, and running a function each time.

// This is the function we want to run each time.
fn someFunction(item) {
    say item + 1;
}

// Create a list and populating it.
let arry = List();
for (let i = 0; i < 10; i++) {
    array.add(i); // Adding each number to the array.
}

// This is the function to iterate through the list and run a function.
array.forEach(someFunction); // This will run the "someFunction" parsing the data from the position in the list.
~~~

> * The difference between a normal list, and one made by the API, is the uppercase L... As you may see.
* I, as the creator of this language... Surgest using the Standard Library list, over the api version. This is due to being able to use the prebuilt functions such as add, replace etc, with ease.


## Maps
~~~ JS
let map = Map(); // Creates an Object map
~~~

### Adding items
~~~ JS
// Usage : mapItem.add(key, value);

map.add("firstname", "Brayden"); // This will add a key value pair to the map

~~~

### Get item
~~~ JS
// Usage : mapItem.get(key);

let item = map.get("firstname"); // returns "Brayden" to the "item" variable
~~~

### Get Keys
~~~ JS
// Usage : mapItem.getKeys();
// For this, you need to use a List for this feature.
# "lists";

let l = List();
l.direct(map.getKeys()); // Will return a list object to the newly created list.

~~~

### Get size
~~~ JS
// Usage : mapItem.size();

let size = map.size(); // returns the size of the map to "size" variable.
~~~

### Exists
~~~ JS
// Usage : mapItem.exists(key);

let result = map.exists("firstname"); // Returns true or false if the key exists in the map.
~~~

### Combine
~~~ JS
// Usage : mapItem.combine(list1, list2);
# "lists";

// Both Lists must be the same length.
let keys = List(); // The keys of the map.
let vals = List(); // The values of the map.

// Filling the 2 lists with values...
for (let i = 0; i < 10; i++) {
    keys.add(i);
    vals.add(i*10);
}

// Using keys List, as the keys.
// Using the vals List, as the values.
map.combine(keys, vals); // changes the contents of the map to these keys and values.

~~~

### Delete items
~~~ JS
// Usage : mapItem.del(key);

map.del("firstname"); // This will delete the key-value pair of "firstname"

~~~

### Reset
~~~ JS
// Usage : mapItem.reset();

// Resets the entire map.
map.reset();
~~~

### Showing the map
~~~ JS
// Usage : mapItem.show();

say map; // This will only say: Map instance
map.show(); // Will show you the contents of the map.
~~~

### Get map item
~~~ JS
// Usage : mapItem.get(key);

map.get("firstname");      // This will not display anything, as it returns the information instead.
say map.get("firstname");  // This will say : Brayden

let res = map.get("firstname"); // This will assign the result "Brayden" to the variable "res"
~~~

## Files
~~~ JS
// Usage File(filename);

let f = File("file.txt"); // This will create a file instance.
~~~

### Loading the file
~~~ JS
f.load(); // Reads the file that we designated earlier.
~~~
> This does not return the contents of the file or anything.

### Seeing file contents
~~~ JS
// After running f.load();

say f.contents; // This will return exactly what was in the file.
~~~

### Splitting to list
~~~ JS
// Make sure file is loaded first.

// Usage file.toList(listObj);

let l = List(); // Used to store the file contents

f.toList(l); // Will split by each line, to the list object.

list.show(); // Will display an array with the file contents, split by each line.
~~~

> The file will only be split by the line.

# Basics
## Comments, Variables, Value Types and Assignment
~~~~ JS
// This is a comment

/*
This is
a multi-line
comment
*/
~~~~

~~~ JS
let a;      // declare and assign nil to a
let b = 50; // declare and assign 50 to b

~~~

~~~~ JS
let a = nil;    // value nil
let a = true;   // boolean true
let a = false;  // boolean false
let a = 123;    // number
let a = 123.32; // number
let a = "hello"; // string
~~~~

~~~~ JS
a = 32;   // assign to 32
a = true; // assign a to true
~~~~
> * Variables are always mutable.
> * Global variables can be re-declared but local variables can't be.

## Operators

### Basics
~~~~ JS
let i = 1;

say i++;                // 2
say i--;                // 0
say ++i;                // 3    (Adds 2)
say --i;                // -1   (Removes 2)
say i += 1;             // 2
say i -= 1;             // 0
say 1 + 2;              // 3
say 1 - 2;              // -1
say 100 % 10;           // 0    (Modulus)
say 2 ** 6;             // 64   (Power)
say 5 * 30;             // 150
say 30/6;               // 5
say 22.0/7.0;           // 3.142857142857143
say "hello" + "hi";     // hellohi
say 1 + "one";          // 1one
say "hello" * 5;        // prints hello five times
say 5 * "hello";        // Runtime Error
~~~~

### Copy operator

~~~ JS
let a = 12;
let b;

say b;      // nil

a -> b;     // The actual copy operation.

say b;      // 12
~~~
> Need an element to copy from, and an element to copy to.
> This means you need to create an element, even if it is blank like below.

> This is not just a regular copy though.
This is a full memory copy.

> This means that if we have an instance of something,
and edit the original. It will also be edited in the
other variable. As seen below.

~~~ JS
# "lists";

// Creating an original element.
let a = List();
// Creating a blank element to copy to.
let b;

// Adding some items to the list.
for (let i = 0; i <= 3; i++) {
    a.add(i);
}

a.show();       // [0.0, 1.0, 2.0, 3.0]
say b;          // nil

a -> b;         // Copying the list instance to b;

b.show();       // [0.0, 1.0, 2.0, 3.0]

a.add(4);       // Adding an item to one of the instances.

a.show();       // [0.0, 1.0, 2.0, 3.0, 4.0]
b.show();       // [0.0, 1.0, 2.0, 3.0, 4.0]
~~~
> It is the same even if we add to b.


## The print Statement

~~~~ JS
say "hello"; // prints hello
say 1+2+3;   //prints 6
~~~~

## Block Statement

~~~~ JS
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

~~~~ JS
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

// if-else if-else

if(a == 42) {
    say "solved";
} else if (a == 24) {
    say "oh... its actually 24";
} else {
    say "failed";
}

let b <- true;
// if and
if (a == 42 and b) {
  say "solved";
} else {
  say "failed";
}

// if or

if (a == 42 or a == 33) {
  say "solved";
} else {
  say "failed";
}

~~~~

## Loops

~~~~ JS
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
~~~~ JS
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

~~~~ JS
fn hello() {
    say "hello";
}

// parameters

fn greet(name) {
    say "Hello " + name;
}
~~~~

## Closures

~~~~ JS
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

~~~~ JS
hello();      // without argument(s)
greet("Mir")  // with argument(s)
~~~~
## Inbuilt Functions

~~~~ JS
let rand   = random(10);            // Takes a number, n, and returns a random number between 0 and n
let name   = readString();          // reads a string from user
let number = readInt();             // reads a number from user otherwise `nil`
let result = ask("Who are you?");   // Prints "Who are you?" to console, and waits for text response.
~~~~
> Other built in functions can be found under the Stand Library section

## Classes

### Basics
~~~~ JS
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

### Private
~~~~ JS
class Person {
    Person(first, last) {
        this.first = first;
        this.last = last;
    }

    internal reverse() {
        say this.last + " " + this.first;
    }

    normal() {
        say this.first + ", " + this.last;
    }

    both() {
        this.reverse();
        this.normal();
    }
}

let p = Person("John", "Doe");

p.normal();     // John, Doe
p.reverse();    // Sorry, this is a private function!
p.both();       // Doe John ... John Doe
~~~~

> As we can see. The use of `internal` makes a function private. You can only access it from other functions inside of the same class. It also works with inheritance.


#### Class Instance

~~~~ JS
let greeting = Greeting("John");
say greeeting.sayHello();     // prints Hello John
say greeting.sayNight();      // prints Good Night John
~~~~

> * A value cannot be retured from the constrcutor.


### Inheritance

~~~~ JS
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
