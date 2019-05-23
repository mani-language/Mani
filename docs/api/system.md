## System
The system API is used to toggle internal functions
of Máni.

### How to import
~~~ mani
    load "system";
~~~

### List of all points
~~~ mani
    online( );
    compiled( );
~~~

### Function descriptions

#### online();
Usage: `online( boolean );`
Used to change the internal internet check status
to the provided boolean value.

> True means there is internet, False means the opposite.

#### compiled();
Usage: `compiled( boolean );`
Used to toggle compiled mode or not. This can be used for debugging your
code to see if it will work inside a compiled program,
or working out why it isn't working.