## Unit Testing
Unit testing is very important for a project. Especially when multiple different people are adding new features left right and centre.

### How to
Unit testing in Máni is very easy as we have setup our own library for it.
Simply import the `tester` library and follow the guide over [here](std/tester.md)

~~~ mani
    # "tester";
    let t = Tester();   // This is an instance of our tester library.
~~~

### What to test
Everyone who implements a new feature, whether it be a new library or a change to the language itself.

Please implement a new unit test in the `test/` directory of the project.
Every time the CI runs the project, it will also run each of the unit tests.