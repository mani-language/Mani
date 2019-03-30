# WELCOME TO THE IDEAS PAGE

## What is this?
This is where we are adding ideas on how we plan to do the functions part of the system.

## What has been done so far?

So far, when you create a list:
```JS
let x = [1, 2, 3, 4, 5];
```
you can use the `at()` function to get the element at a certain position.
The code that runs this is in the `Inbuilt.java` file, around line 210.

What happens is, the Interpreter goes through the code. And it finds the `.`
then it checks to see if it is referencing a `ManiFunction` or not.

The system used to return an error saying `Only instances have properties.`. We decided to change this.
Rather than having lots of Standard libraries with functions that referenced the API's, (In my opinion, double handling).
It now looks to see if there is a `Local` that suits what is beeing sort after.

It checks the type of the Object being passed, then what the function name is.

If it does not exist, it will return the same error as before. Otherwise it will run the code todo with that funciton.

An example of a local is this:
```JAVA
locals.put("posOf", new ManiCallableInternal() {
    @Override
    public int arity() { return 1; } //If you dont need any arguments. Dont include this.

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        // Do your code here.
        return null;
    }
});
```
### Whats the problem with this?
Sure, everything may seem all well and good, but the problem is how it works.
When the user defines something or imports an API (which all standard libraries do), all the variables get added to a big hashmap (dictionay, or object).
This can be really bad if you dont want all the functions from the system to be added, as it could flood the map and make it slow.

### Whats the goal?
The goal is to be able to create a system that is small and powerful.
We want the end user to be able to use all of these features, but we dont want
to flood the map with all of the functions if it doesnt have to be.

### Our current solution.
Our current solution to this is with the API's.
Each api can be imported with the following code:
```JS
load "arrays";
```
This will load the arrays API. This means it will only import the functions defined in that module.

The only problem with this system is that it doesnt allow us to do extensions.
The easiest way to explain what i mean by that is with a demonstration.

With extensions:
```JS
let x = [1, 2, "some string", 3, 4];
let y = x.at(2); // This is the example of the extension. (It just has a '.')
say y; // prints: "some string"
```

Without extensions:
```JS
let x = [1, 2, "some string", 3 4];
let y = arrayPos(x, 2); // This is what the API currently does.
say y; // prints: "some string"
```

As we can see. The API creates an actual function to use, rather than a property of the original object.

Its simply just not intuitive enough for any user.