## Types - API
The types API gives access to converting different objects between types, aswell as checking what type an object is.

### How to import
~~~ mani
    load "types";
~~~

### String
Included with this API is a `toString()` function. This is used to convert anything to a string. This can make it easier to work with in some cases.

~~~ mani
    let x = 45;
    ley y = toString(45);   // y is now "45".
~~~

There is also a function called `isString()`, this function will return a boolean value, based on whether or not an object is a String.

~~~ mani
    let x = "String";
    let y = 45;
    isString(x);            // true.
    isString(y);            // false.
~~~

### Number
To help convert those pesky string values to a Number, we have included a function called `toNumber()`. This can be very handy if you are using a cheat way of creating large numbers. As shown below.

~~~ mani
    let x = "34";
    let y = 34;
    for (let i = 0; i < 10; i++) {
        x += toString(i);
        y += i;
    }

    x = toNumber(x);

    say x;                  // Result : 340123456789
    say y;                  // Result : 79

~~~

There is also a handly function called `isNumber()` for checking if an object is actually a number.

~~~ mani
    let x = "34";
    let y = 34;

    isNumber(x);            // false.
    isNumber(y);            // true.
~~~

There is also a `isDigit()` to work with characters. This will check if the character provided is between 0-9. If it is, it will return true, otherwise false.

### ASCII
If you are looking to get the ascii code for a character. Simply use the `toAscii()` function provided when you import the API.

~~~ mani
    let char = "a";
    let res = toAscii(char);
~~~

### Other
Wanting to work with Chars? No problem. We have got two functions at your disposal. `toChar()` and `isChar()`.

If you have read the above, you should be able to guess what they do.

~~~ mani
    let s = "String";
    let x = "x";

    // toChar, will only take the first character if you provide a string.
    let res = toChar(s);        // res is now "S".
    let res = toChar(x);        // res is now "x".
~~~
When checking if something is a char. Unless converted with the method, it will always be counted as a string.
~~~ mani
    let x = "String";
    let y = toChar("x");

    isChar(x);                  // false.
    isChar(y);                  // true
~~~