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