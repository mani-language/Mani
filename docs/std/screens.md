## Screens
The screens library is the Máni equivalent to a basic GUI libary.

> This library still needs alot of work and is very buggy

### How to import
~~~ mani
    # "screens";
~~~

### What it includes
~~~ mani
    Maps
~~~

### Creating a Screen
Screens are very easy to create. Just initialize the Class and assign it to a variable.

Make sure you provide the title of the screen as an argument.

~~~ mani
    let view = Screen("Screen Title");
~~~

### Show and hide screen
Showing and hiding the screen is one of the easiest tasks.

Simply call `show()` to show the screen, and `hide()` to hide it.

~~~ mani
    let view = Screen("My screen");

    view.hide();

    view.show();
~~~

### Adding buttons
There are two ways to currently add a button to the screen.
There is a basic way, `addBasicBtn()` where you just provide the title on the button.
THere is also an advanced way `addBtn()` where you prodivde `title, x, y, width, heigh`

~~~ mani
    let view = Screen("My Screen");

    view.addBasicBtn("First button");

    view.addBtn("Second button", 300, 300, 100, 100);
~~~

### Elements list
The screens library keeps a list of all the elements added to the screen in a map.

You can easily access this with `showMap()`

~~~ mani
    let view = Screen("My Screen");

    view.addBasicBtn("Button name");

    let m = view.showMap();
~~~

You can then access this map like any other regular map.