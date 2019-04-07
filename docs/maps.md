## Maps
Maps, like most other languages are a list of key - value pairs.

The keys and values can be any Object.

~~~ mani
    // Creating a filled map.
    let m = { "key" : 1, 2 : "value" };

    // Creating an empty map.
    let empty = {};

    // Each map has a count() method.
    m.count();      // returns 2.
~~~

### Looking up values.
You can access a key-value pair from a map very easily with the at() method.

~~~ mani
    let legs = { "horse" : 4, "human" : 2, "fish" : 0};

    legs.at("horse");   // 4.
    legs.at("fish");    // 0.
~~~

### Adding values.
To add a key-value pair to the map, simply use the add() method.
~~~ mani
    let nums2 = {1 : 2, 2 : 4, 3 : 6};

    nums2.add(4, 8);    // Adds 4 : 8, to the end of the map.
~~~

### Removing pairs.
To remove a pair from a map, simply use the .del() method.
~~~ mani
    let namesAndAges = {"bob" : 27, "steve" : 31, "frank" : 46};

    namesAndAges.del("bob");    // Removes the "bob" pair from the map.
~~~