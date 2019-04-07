## Functions
functions are crucial to most modern languages. They can be stored in variables, and be passed function parameters.

Functions are called by their variable name.

~~~ mani
    fn myName(name) {
        say "my name is " + name;
    }

    myName("bob");
    myName("Frank");
~~~

~~~ mani
    fn f() {
        return "string";
    }

    fn ff() {
        return f;
    }

    // A now links to function "f"
    let a = ff;

    // b is now the returned value of a.
    let b = a();

    // return value is f(), which is "string".
    return b();
~~~

### Main Function
Unlike some other languages, Máni does not need a main function to start. It will act more like python and run whatever isnt in a function.

This changes once you use our compiler though. The compiler will load all of the code, then look for a function called `main` and run that. This is to prevent the code from being initialized out of order.

### Function parameters
Functions are great for allowing re-use of code, without typing it multiple times. To allow that, you generally need Parameters.

There is no limit to how many parameters you can have on your functions.

~~~ mani
    fn sum(a, b) {
        return a + b;
    }

    // Executes the sum function.
    sum(10, 20);    // returns 30.
~~~

If a function is called without some of the required parameters, or has too many. You will encounter an error with the following message.

~~~
Expected X argument(s) but got Y.
~~~


### Recursion
Functions calling themselves is fully supported with Máni. A good example of this is with the following.
~~~ mani
    fn fibonacci (n) {
        if (n<2) return n;

        return fibonacci(n-2) + fibonacci(n-1);
    }

    fibonacci(20);
~~~

### Returning values
Unless there is a return statement inside of the function, the function will naturally return Nil.
