## Operators

### Arithmetic Operators
* Addition (+)
* Subtraction (-)
* Multiplication (*)
* Devision (/)
* Remainder (%)
* Power (**)

~~~ mani
    let add = 2 + 2;        // equals 4
    let min = 9 - 7;        // equals 2
    let tim = 6 * 2;        // equals 12
    let div = 6 / 3;        // equals 2
    let rem = 100 % 10;     // equals 0
    let pow = 2 ** 4;       // equals 16
~~~

### Assignment Operators
Much like a few other languages Máni has a regular assignment operator, that allows the user to assign a value to the variable.

~~~ mani
    let x = 12;
    ley y = 2;
    let z = 12 * 2;
~~~

### Compound Assignment Operators
To make things quicker, assignment operators can be merged together.
~~~ mani
    let y = 2;
    y += 2;         // equals 4
    y *= 3;         // equals 12;
    y -= 6;         // equals 6;
    y /= 3;         // equals 2;
~~~

### Comparison Operators
Comparison operators are used to return either true or false (Boolean).

* Equal (==)
* Not Equal (!=)
* Less Than (<)
* Greater Than (>)
* Less Than or Equal to (<=)
* Greater Than or Equal to (>=)
* Type check (is)
~~~ mani
    1 == 1          // true, because 1 does equal 1.
    1 != 2          // true, because 1 does not equal 2.
    1 < 2           // true, because 1 is than 2.
    1 > 2           // false, because 1 is not greater than 2.
    1 <= 2          // true, because 1 is less than 2.
    1 >= 1          // true, because 1 is equal to 1.
    1 is Number     // true, because 1 is a Number
~~~

## Logic Operators
Logic operators also return a bool (true or false).

* Not (!)
* And (and)
* Or (or)
~~~ mani
    !1              // False. 1 is true, 0 is false. The oposite to true is false.
    1 and 0         // False. Both must be true to result in true.
    1 || 0          // True. One or both must be true to continye.
~~~