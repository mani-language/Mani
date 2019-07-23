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
    hadError( );
    latestError( );
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

#### hadError();
Usage `hadError();`
Returns true when a runtime error has occurred otherwise returns false. This is most useful when
testing and you want the script to quit on a runtime error. The `wipeMemory();` function in [STD API](api/std.md)
resets this runtime error flag.

#### latestError();
Usage: `latestError();`
Returns the latest error message as a string. Can be used for testing.