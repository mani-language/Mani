## Math
The math library is used to perform complex arithmetic operations.

### How to import
~~~ mani
    load "math";
~~~

### What it includes
This Library does not require any others to run.

### Round
Rounds a value to the nearest whole number.
~~~ mani
    let x = 101.23;
    let y = 101.67;
    let z = 101.50;
    
    let xRounded = round(x);  // Returns 101
    let yRounded = round(y);  // Returns 102
    let zRounded = round(z);  // Returns 102
~~~

### Floor
Floor rounds a decimal number down to the nearest whole number.
~~~ mani
    let x = 2.31;
    let result = floor(x);
    
    say result; // result = 2
~~~

### Ceil
Ceil rounds a decimal number up to the nearest whole number.
~~~ mani
    let x = 2.31;
    let result = ceil(x);
    
    say result; // result = 3
~~~

### Common values
The math library keeps some common values which can be accessed using the appropriate keywords as shown.
~~~ mani
    let x = PI;  // Stores the value of pi (i.e. 3.14159265358979323846)
    let y = E;  // Stores the value of E (i.e. 2.7182818284590452354)
    
    say x;
    say y;
~~~

### Trigonometry
The trigonometry functions work as you would expect. They return the resultant value of the parameters provided. Examples are below:
~~~ mani
    let x = 1.23;

    asin(1) // Returns 1.5707963267948966
    acos(1) // Returns 0.0
    atan(1) // Returns 0.7853981633974483
    atan2(x, x) // Returns 0.7853981633974483
    cbrt(x) // Returns 1.0714412696907731
    cos(x) // Returns 0.3342377271245026
    sin(x) // Returns 0.9424888019316975
    tan(x) // Returns 2.819815734268152
    cosh(x) // Returns 1.8567610569852664
    exp(x) // Returns 3.4212295362896734
    expm(x) // Returns 2.4212295362896734
~~~









