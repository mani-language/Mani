## Loops

### While loop
A while loop will continue executing a task / list of tasks until the condition becomes false.
~~~ mani
    let i = 0;
    while (i <= 50) {
        i++;
    }

    say i;
~~~


### For loop
A for-loop can be used to iterate over different objects. A good use for a for-loop is to iterate over every object in a list.

~~~ mani
    let list = [1, 2, 3, 4];

    for (let i = 0; i < list.size(); i++) {
        say list.at(i);
    }
~~~

### For each
A for-each loop can be used to iterate through objects and automatically assign them to a variable for you to
work with.

~~~ mani
    let list = [1, 2, 3, 4];

    for (item : list) {
        say item;
    }
~~~

### For each map
A for-each map loop can do the same as the regular for each loop, just it allows for both keys and values
at the same time.

~~~ mani
    let map = {"key1" : "val1", "keeey2" : "vallll2"};

    for (keyItem, valItem : map) {
        say "Key: " + keyItem;
        say "Value: " + valItem;
    }
~~~

> Small warning with both of the for-each loops. The current solution to an issue is to forcefully
create the temporary variable used by the for-each loop. Then it will delete it from the scope once
it is done. This means, if you use the same variable name you used elsewhere. You will need to redefine
it after using a for-each loop as it will be unknown to the system now.

> This is why we recommend using a unique variable name in the for-each loop.
