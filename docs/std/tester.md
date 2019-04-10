## Tester
Tester is Máni's version of a unit tester.
Thats right, we created our own unit tester for our language. This was the easiest approach, instead of trying to hook it up with JUnit or a differnet one.

### How to import
~~~ mani
    # "tester";
~~~

### What it includes
This Library does not require any others to run

### Creating a unit test
Its very simple to create a unit test. Just initialize the Class, and assign it to a variable.

~~~ mani
    let t = Tester();
~~~

### Adding a message
Just like in other unit tests, Tester can display a message before it does the test. Simpy use the `msg()` function.
This can be helpful to anyone trying to figure out where it failed in the test.

~~~ mani
    t.msg("Your message here");
~~~

### Adding a test
Tester's `match()` function takes 2 arguments. The object your working with, and what it should equal.

In this example, we will test the add function on a regular map.
~~~ mani
    let t = Tester();

    t.msg("Testing add function on map");
    let x = [1, 2, 3];
    x.add(4);
    t.match(x, [1, 2, 3, 4]);
~~~

As you can see it is very simple.