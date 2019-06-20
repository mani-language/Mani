## Syntax

### Comments
Máni has both single line comments
~~~ mani
	// This is a single line comment.
~~~
and multi-line comments.
~~~ mani
	/*
	This is a 
	multi-line comment
	*/
~~~

### Load

The Load function can have 2 purposes. To load a local file, or to load an API point.

> Máni will always load a file over an API if the names are the same.

~~~ mani
	// name.mni
	fn myName(name) {
		say "name is " + name;
	}
~~~
~~~ mani
	// main.mni
	say "my " + myName("steve!");
~~~

### Import
Import is used to import Standard Libraries created in Máni.

~~~ mani
	# "lists";
~~~

For more information, visit the [Standard Libraries Page.](standardLibs.md)

### Scopes
Inside of every set of `{}` is a scope. ``"What happens in scope, stays in scope, unless there is a leak!" - Crazywolf``
What this means is. If there is a variable created inside of the scope, nothing from the outside will ever know it happened. The only way for that to happen, is if the variable was created on the outside. AKA the "leak".

### Blocks
Máni allows the use of blocks, that dont need a name and will run when loaded.
~~~ mani
	let x = 0;
	{
	    x = x + 1;
	    say x;     // prints 1
	    let y = "hey";
	    say y;     // prints hey
	}
	say x;         //prints 1
	say y;         // Undefined variable;
~~~
As we can see with the above, this includes and example of Scopes.

Where the `y` variable was never created before the scope, so nothing ever knows about it, or its changes.
Whereas `x` is a good case of the "leak", x was created before the scope, therefore any changes that happen to it inside the scope, also get remembered outside.

### Reserved Words
This is a set of words that cannot be used as variable names, just like most other languages.
~~~ mani
	if or and else fn let true false break return
	nil STRICT CHANGE_LANG class for say super this
	internal while loop load is # as nameset
~~~

### Identifiers
Identifiers in this language are case sensitive. They are used to define objects such as variables, classes and functions.
An indentifier starts with a letter or an underscore, they can then procceed to use letters, numbers or underscores.

~~~ mani
    t
    A_VALID_EXAMPLE
    _So_am_I
    IH4V3_Numbers
    a2
~~~