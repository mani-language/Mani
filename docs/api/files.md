## Files
The files API is used by the Files STDLIB. This is how we get that extra functionality on the files.

### How to import
~~~ mani
    load "files";
~~~

### List of all points
~~~ mani
    fopen();
    fread();
    fwrite();
    fgetPath();
~~~

### Function descriptions

#### fopen();
Usage: `fopen( filename );`
Used to open a file for reading and writing. Returns a file object.

#### fread();
Usage: `fread( filename );`
Used to read the contents of an opened file. Returns the contents of the file as a string.

#### fwrite();
Usage: `fwrite( fileObject, value, boolean );`
Used to write to the opened file. Takes the file object as the first parameter and the contents to write to it as the second parameter. Set the boolean to true to append to the file or false to write to it as a blank file.

#### fgetPath();
Usage: `fgetPath( fileObject );`
Used to return the path to the file created.

