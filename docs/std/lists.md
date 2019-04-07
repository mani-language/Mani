## Lists
The lists library is used to access some more advanced methods of the regular list object.

The library itself is a class written in Máni, that uses the [Lists API](api/lists.md).

### How to import
~~~ mani
    # "lists";
~~~

### What it includes
This Library does not require any others to run

### Extra functions provided
~~~ mani
    list.reset();
    list.addLots();
    list.forEach();
    list.trim();
~~~

### Creating an Advaced list
Its very simple to create a new Advanced List. Just initialize the Class, and assign it to a variable.

Inside the List class, there is an attribute called `list`, you can directly access this as it is a regular list object.

~~~ mani
    let l = List();     // Creates a new list.
~~~

### Add objects
Just like the regular list, Advanced lists have the method `add()` that you can access to add Objects.

~~~ mani
    let l = List();

    l.add("item");
    l.add(1);
    l.add(3.30);
    l.add(true);
    l.add(false);

    say l;              // ["item", 1, 3.30, true, false]
~~~

### Adding multiple objects
One of the advantages to using advanced lists is the ability to add multiple objects in one go.
This is achieved by using the `addLots()` method.

~~~ mani
    let x = List();
    let y = [1, 2, 3, 4];

    say x;                  // []. Nothing is currently in X.
    
    // Adding to X, with an array of objects to add.
    x.addLots(y);

    say x;                  // [1, 2, 3, 4]

    x.addLots([5, 6, 7]);

    say x;                  // [1, 2, 3, 4, 5, 6, 7]
~~~

### Delete items from the list
Once again, this is another port the same as the Add function. The advanced list uses the same `del()` function the regular one does.

~~~ mani
    let x = List();

    x.addLots(["one", "two", "three", "four"]);

    x.del(2);

    say x;                  // ["one", "two", "four"]
~~~

### List reset
This function allows the user to reset the list to a blank list.
This can be handy if you want to quickly wipe it from memory. This can be achieved with `reset()`

~~~ mani
    let x = List();
    x.addLots([1, 2, 3, 4]);

    say x;                 // [1, 2, 3, 4]

    x.reset();

    say x;                  // []
~~~

### List trimming
This function is used to remove the whitespace off every string in the list. This can be handy if you have read each line of a file into the list, and want to remove the padding. Simply use `trimEach()`

~~~ mani
    let x = List();
    x.addLots(["    spaceBeforeString", "spaceAfterString     "]);

    say x;                  // ["    spaceBeforeString", "spaceAfterString     "]

    x.trimEach();

    say x;                  // ["spaceBeforeString", "spaceAfterString"]
~~~

### Direct listing
This feature is used if ou want to load a regular list straight into an advaced list, without using the add functions. To do this, simply use `direct()`

~~~ mani

    let premade = [1, 2, 3, 4, "String", true, false];

    let new = List();

    say new;            // []

    new.direct(premade);

    say new;            // [1, 2, 3, 4, "String", true, false]
~~~

### For Each
If you were ever wanting to run a function for each item in the list. Rather than using a for loop, you can supply your own function to the list to work through it.

The only requirements are, the function must have 1 argument.

Use the `forEach()` function to be able to do this.

~~~ mani
    let i = 0;

    fn eachItem(item) {
        i += item;
    }
~~~

~~~ mani
    let x = List();
    x.addLots([1, 2, 3, 4]);

    x.forEach(eachItem);

    say i;                      // 10
~~~