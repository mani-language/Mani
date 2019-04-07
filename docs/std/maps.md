## Maps
The maps library is used to extend the regular maps object. It provides some helpful methods for your use.

The library itself is a class written in Máni, that uses the [Maps API](api/maps.md)

### How to import
~~~ mani
    # "maps";
~~~

### What it includes
This Library does not require any others to run

### Extra functions provided
~~~ mani
    map.update();
    map.getKeys();
    map.copy();
    map.exists();
    map.combine();
    map.reset();
~~~

### Creating an Advanced Map
Its very simple to create a new Advanced Map. Just initialize the Class, and assign it to a variable.

Inside the Map class, there is an attribute called `map`, you can directly access this as it is a regular map object.

~~~ mani
    let m = Map();
~~~

### Add Objects
Just as simple as the regular map, you can call the `add()` method, and provide your key-value pair.

~~~ mani
    let m = Map();

    m.add("key", "value");
~~~

### Delete Objects
Deleting is the same as adding, in the way that it is the same method as the original Map. `del()`

~~~ mani
    let m = Map();
    m.add("key", "value");

    m.del("key");
~~~

### Update Objects
Updating an object can be done one of two ways. Either use the `add()` method again, as their can only be one each key. Or use the `update()` method.

~~~ mani
    let m = Map();
    m.add("key", "value");

    m.update("key", "new_value");
~~~

### Get keys
The `getKeys()` function will return a list (regular list object) of all the keys used in the advanced map.

~~~ mani
    let m = Map();
    m.add("key", 1);
    m.add(2, 3);
    m.add("keeey" : "valll");
~~~

~~~ mani
    let keys = m.getKeys();

    say keys.join(", ");    // "key, 2, keeey"
~~~

### Check if a key exists
Checking if a key exists in the map is as easy as `exists()`.
This will return a true or false value, not the actual value.

~~~ mani
    let m = Map();
    m.add("key", 1);
    m.add(2, 3);
    m.add("keeey" : "valll");
~~~

~~~ mani
    m.exists("key");            // true.
    m.exists("crazywolf");      // false.
~~~

### Make a map from two lists
Making a map from two lists has never been easier. Rather than using 2 for loops, and hopeing they are the same length. Simply use the `combine()` function we have provided for you.

~~~ mani
    let keys = [1, 3, 5, 7, 9];
    let vals = [2, 4, 6, 8, 10];

    let m = Map();

    m.combine(keys, vals);
~~~

### Get size
Maps have a built in `count()` method, that will return the size of the map. The same goes for the Advanced maps.

### Map reset
It is extremely easy to start fresh with the map. Simply call the `reset()` method, and the map will be reset back to an empty map.

~~~ mani
    let m = Map();
    m.add("key", 1);
    m.add(2, 3);
    m.add("keeey" : "valll");
~~~

~~~ mani
    m.reset();
~~~

### Direct Map
The direct map method `copy()` allows you to go straight from a regular map to an advanced map, without all the hastle.

~~~ mani
    let orig = {"key" : 1, 2 : 3, "keeey" : "valll};

    let new = Map();

    new.copy(orig);
~~~