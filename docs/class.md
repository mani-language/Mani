## Classes
Everything handled by Máni is an Object, and every object is an instance of a Class of some sorts.

So its only natural for you to want to be able to create your own Classes, to have instances of.

Like Functions, classes are assigned to a variable.

### Defining a class
To define a class, we follow a standard created by most other languages. The `class` keyword is used.
~~~ mani
    class Vehicle {
    }
~~~

### Constructors
Each class can have a constructor, but only 1.
To define a construct, simply create a function with the same name as the Class inside the class.

There is no required amount of arguments for a constructor.

~~~ mani
    class Vehicle {
        // This is the constructor.
        Vehicle(arg1, arg2) {
            // This is where we define our class attributes.
        }
    }
~~~

### Initialize the class
Executing a class in Máni is as easy as simply adding `();` to the end of the variable name.

It is best to assign it to a variable, although it is not required.

~~~ mani
    var inst = Vehicle();

    // This is assuming the constructor in the class will run
    // a function or something. Otherwise nothing will happen.
    Vehicle();      
~~~

### Methods
Adding a method to a function is ultra easy. Even easier than a regular function.

Simply create a variable name, and add `()` after it. There is no need to append the `fn` prefix.

~~~ mani
    class Vehicle() {
        Vehicle() {
            this.type;
            this.year;
        }

        getType() {
            return this.type;
        }

        getYear() {
            return this.year;
        }

        setType(ty) { this.type = ty; }
        setYear(yr) { this.year = yr; }
    }

    let car = Vehicle();
    car.setType("sedan");   // Changes the type attribute of the class Vehicle.
    car.setYear(1995);      // Changes the year attribute of the class Vehicle.

    car.getType();          // returns "sedan";
~~~

### Private methods
Naturally every method in a class is counted as a Public method. This can be changed with a single prefix though.
Simply adding `internal` to the front of a method, means nothing outside of that function will be able to access it.

~~~ mani
    class Priv {
        Priv() {
            this.firstName = "bob";
            this.lastName = "sky";
        }

        // This function can only be accessed by other methods
        // inside of this class.
        internal first() {
            return this.firstName;
        }

        last() {
            return this.lastName;
        }

        fullName() {
            // This method can acces this.first() because it
            // is in the same class.
            return this.last() + ", " this.first();
        }
    }

    let p = Priv();

    say p.last();           // sky
    say p.first();          // Error message: "This function is private."
    say.fullName();         // sky, bob
~~~

### Super classes
Superclasses in Máni act like the "extends" feature in Java classes.
To create a super-class, simply use the `<` symbol.

~~~ mani
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
    inst.methodB1();
~~~

### ToString method
Much like Java's toString method, Máni has its own. If this method is not found inside of a class... when saying a class instance it will print something along the lines of `Maps instance` or similar.

Simply use the `show` method in a class to edit the output in a say statement. 

Unlike Java, you do not return what you want it to say. You must use a say statement.

~~~ mani
    class Points {
        Points() {
            this.a = 12;
            this.b = 2;
            this.c = 3;
        }

        show() {
            say "a : " + this.a + ", b: " + this.b + ", c: " + this.c;
        }
    }

    let P = Points();
    say P;                  // Executes the show() method.
~~~

### Accessing Class Attributes
Not only can we access class functions from outside.

We can also access class attributes just as easily.
~~~ mani
    class A {
        A() {
            this.a = 12;
            this.b = b + 2;
            this.c = b + 2;
        }
    }

    let a = A();

    say a.a;            // 12.
    say a.b;            // 14.
    say a.c;            // 16.
~~~