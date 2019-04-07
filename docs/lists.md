## Lists
Lists (AKA. arrays, or vectors) are a sequence of objects. They are dynamically sized and will always start with an index of 0.

~~~ mani
    let l = [1, 2.54, "some string", true, false];

    // lists also have a size method.
    let n = l.size();   // n is now 5.
~~~

### Accessing elements
You can access an element of a list with the at method.
~~~ mani
    let pets = ["horse", "cat", "dog", "fish", "birds"];

    pets.at(0);     // "horse"
    pets.at(3);     // "fish"
~~~

### Adding elements
You can easily add any object as an element to a list. Simply use the add() method.
~~~ mani
    let l = [1, 2, 3, 4];

    l.add(5);   // 5 will now be added to the back of the list.
~~~

### Contains elements
You can check if the list you are working with contains an element through the has() method.
~~~ mani
    let l = [1, 2.54, "some string", true, false];

    l.has(2.54);    // true
    l.has("dog");   // false
~~~

### Deleting elements
You can delete an element as easily as you created one. Simply use the del() method.
~~~ mani
    let l = ["one", "two", "three", "four"];

    l = l.del(2);       // l is now ["one", "two", "four"];
~~~

### Join
To make things simple for people output the contents of a list, you can use the join() method.
~~~ mani
    let l = [1, 2, 3, 4];

    l = l.join(" + ");      // l is now "1 + 2 + 3 + 4";
~~~

> This returns a string.

### As a stack
For the people wanting to use a stack, push() and pop() methods are avaliable.
~~~ mani
    let l = [10, 20, 30, 40];

    l.push(50); // Adds 50 to the stack.

    l.pop();    // Returns 50, and removes it from the stack.
    l.pop();    // Returns 40, and removes it from the stack.
~~~