## Files
The files library is used to read and write to files.

The library itself is a class written in M�ni, that uses the [Files API](api/files.md).

### How to import
~~~ mani
    # "files";
~~~

### What it includes
This Library does not require any others to run

### Extra functions provided
~~~ mani
    file.open();
    file.read();
    file.toList();
    file.write();
    file.append();
    file.path();
    file.exists();
    file.canWrite();
    file.canRead();
    file.canExecute();
~~~

### Creating a file object.
Its very simple to create a new File. Just initialize the Class, and assign it to a variable.

~~~ mani
    let f = File("pathToFile.txt");     // Creates a file object.
~~~

### Open a file
Before reading and writing to a file you must open it. The method `f.open()` does this for you.

~~~ mani
    let f = List("filename.txt");

    f.open();  // File can now be writen to or read from
~~~

### Reading files
The method `f.read()` allows you to get the contents of a file. The file must be opened with `f.open()` before attempting to read.

~~~ mani
    let f = List("filename.txt");
    f.open();

    say f.read();  // Outputs the contents of the file
~~~

### File contents to list
The `f.toList()` method in the stdlib allows you to get the contents of the file with each line as a separate element in the list.
~~~ mani
    let f = List("filename.txt");
    f.open();

    let fileList = f.toList();  // Returns the contents of the file as a list
~~~

### Writing to a file
To write to a file, use the method `f.write()` with the string to write to the file as a parameter. Note that this will overrite any content already in the file.
The file must also be opened with `f.open()` before writing to it.

~~~ mani
    let f = List("filename.txt");
    f.open();

    f.write("this is a string")  // Writes "this is a string" to the file
~~~

### Appending to a file
To append to a file, use the method `f.append()` with the string to write to the file as a parameter. Note that this will add the string to the end of the existing content of the file.
The file must also be opened with `f.open()` before appending to it.

~~~ mani
    let f = List("filename.txt");
    f.open();

    f.append("this is a string")  // Writes "this is a string" to the end of the file
~~~

### File path
To get the file path, use the method `f.path()`. This will return the path of the file as a string.

~~~ mani
    let f = List("filename.txt");
    f.open();

    f.path(); // Returns the path ....../filename.txt
~~~

### File flags
To get the state of the file, Máni can detect several file states. To access these, use the functions `f.exists()`, `f.canWrite()`, `f.canRead()`, `f.canExecute()`.
These all return a boolean value matching the state of the file.

~~~ mani
    let f = List("filename.txt");
    f.open();

    f.exists();
    f.canWrite();
    f.canRead();
    f.canExecute();
~~~