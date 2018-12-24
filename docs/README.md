# Máni
## What is it?
Máni is an interpreted language that is simple to learn, and easy to use.

The idea behind it is, take some of the good parts of other languages like nodejs, python etc.
Implement them into this, and try and build an "Alpha" language... if that is even a such thing.

# Importing, Loading and Using
## Use
~~~ mani
use("std");  // Loads the API end points for the STD Library.
use("maps"); // Loads the API end points for the Maps Library.
~~~
## Importing
~~~ mani
import("lists"); // Loads the lists library
import("files"); // Loads the files library
~~~
## Loading
~~~ mani
load("somefile.mn"); // Loads the local file called "somefile.mn" if it exists.
~~~

> Whats the difference? <br>
The difference is, Importing loads the standard libraries, whereas Loading loads local files.
Usage loads the java API points for the select library. Eg, the Maps or STD. These are the raw
connections that the Mani STDLib uses.


# Standard Library
## Lists

~~~ mani
let l = List();   // Creates an Object list.
~~~
> This means that you can add anything to these lists.<br>
Anything from Functions, Numbers and Strings.


### Displaying the list

~~~ mani
say l;      // Will show: List instance
l.show();   // Will show: []
~~~

### Adding items to the list

~~~ mani
l.add("some string");  // This will add "some string" to the String list

say l; // Will now show [some string]

l.add("something else"); // This will add "something else" to the list

l.show(); // Will now show [some string, something else]
~~~

### Replacing a string

~~~ mani
// Usage is: list.replace(position, string);
l.replace(1, "banana");

l.show(); // We will now see... [some string, banana]
~~~

### Deleting a position

~~~ mani
// Usage is: list.delete(position);
l.delete(1);

l.show(); // Will now show [some string]
~~~

### Reset the list
~~~ mani
// Usage is: list.reset();
l.reset();

l.show(); // Will now once again show []
~~~

### Triming items in list
~~~ mani
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
~~~ mani
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

### For Each
~~~ mani
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
~~~ mani
let map = Map(); // Creates an Object map
~~~

### Adding items
~~~ mani
// Usage : mapItem.add(key, value);

map.add("firstname", "Brayden"); // This will add a key value pair to the map

~~~

### Get item
~~~ mani
// Usage : mapItem.get(key);

let item = map.get("firstname"); // returns "Brayden" to the "item" variable
~~~

### Get Keys
~~~ mani
// Usage : mapItem.getKeys();
// For this, you need to use a List for this feature.
import("lists");

let l = List();
l.direct(map.getKeys()); // Will return a list object to the newly created list.

~~~

### Get size
~~~ mani
// Usage : mapItem.size();

let size = map.size(); // returns the size of the map to "size" variable.
~~~

### Exists
~~~ mani
// Usage : mapItem.exists(key);

let result = map.exists("firstname"); // Returns true or false if the key exists in the map.
~~~

### Combine
~~~ mani
// Usage : mapItem.combine(list1, list2);
import("lists");

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
~~~ mani
// Usage : mapItem.del(key);

map.del("firstname"); // This will delete the key-value pair of "firstname"

~~~

### Reset
~~~ mani
// Usage : mapItem.reset();

// Resets the entire map.
map.reset();
~~~

### Showing the map
~~~ mani
// Usage : mapItem.show();

say map; // This will only say: Map instance
map.show(); // Will show you the contents of the map.
~~~

### Get map item
~~~ mani
// Usage : mapItem.get(key);

map.get("firstname");      // This will not display anything, as it returns the information instead.
say map.get("firstname");  // This will say : Brayden

let res = map.get("firstname"); // This will assign the result "Brayden" to the variable "res"
~~~

## Files
~~~ mani
// Usage File(filename);

let f = File("file.txt"); // This will create a file instance.
~~~

### Loading the file
~~~ mani
f.load(); // Reads the file that we designated earlier.
~~~
> This does not return the contents of the file or anything.

### Seeing file contents
~~~ mani
// After running f.load();

say f.contents; // This will return exactly what was in the file.
~~~

### Splitting to list
~~~ mani
// Make sure file is loaded first.

// Usage file.toList(listObj);

let l = List("String"); // Used to store the file contents

f.toList(l); // Will split by each line, to the list object.

list.show(); // Will display an array with the file contents, split by each line.
~~~

> The file will only be split by the line.

# Basics
## Comments, Variables, Value Types and Assignment
~~~~ mani
// This is a comment
~~~~

~~~ mani
let a;      // declare and assign nil to a
let b = 50; // declare and assign 50 to b

~~~

~~~~ mani
let a = nil;    // value nil
let a = true;   // boolean true
let a = false;  // boolean false
let a = 123;    // number
let a = 123.32; // number
let a = "hello"; // string
~~~~

~~~~ mani
a = 32;   // assign to 32
a = true; // assign a to true
~~~~
> * Variables are always mutable.
* Global variables can be re-declared but local variables can't be.

## Operators

### Basics
~~~~ mani
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

~~~ mani
let a = 12;
let b;

say b;      // nil

a -> b;     // The actual copy operation.

say b;      // 12
~~~
> Need an element to copy from, and an element to copy to.
This means you need to create an element, even if it is blank like below.

> This is not just a regular copy though.
This is a full memory copy.

This means that if we have an instance of something, 
and edit the original. It will also be edited in the
other variable. As seen below.

~~~ mani
import("lists");

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

~~~~ mani
say "hello"; // prints hello
say 1+2+3;   //prints 6
~~~~

## Block Statement 

~~~~ mani
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

~~~~ mani
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

~~~~

## Loops
 
~~~~ mani
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
~~~~ mani
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

~~~~ mani
fn hello() {
    say "hello";
}

// parameters

fn greet(name) {
    say "Hello " + name;
}
~~~~

## Closures

~~~~ mani
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

~~~~ mani
hello();      // without argument(s)
greet("Mir")  // with argument(s)
~~~~
## Inbuilt Functions

~~~~ mani
let rand   = random(10);            // Takes a number, n, and returns a random number between 0 and n
let name   = readString();          // reads a string from user
let number = readInt();             // reads a number from user otherwise `nil`
let result = ask("Who are you?");   // Prints "Who are you?" to console, and waits for text response.
~~~~
> Other built in functions can be found under the Stand Library section

## Classes

### Basics
~~~~ mani
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
~~~~ mani
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


### Class Instance

~~~~ mani
let greeting = Greeting("John");
say greeeting.sayHello();     // prints Hello John
say greeting.sayNight();      // prints Good Night John
~~~~

> * A value cannot be retured from the constrcutor.


### Inheritance

~~~~ mani
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
