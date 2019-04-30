## Threads API
The threads API gives access to creating and sleeping different threads in Máni.

### How to import
~~~ mani
    load "threads";
~~~

### Creating a thread
Threads can be created 1 of 2 ways. You can either call the `thread()` function with the parameter of a function to run on that thread. Or you can call `thread()` and assign it to a variable with the paramater of a function.

~~~ mani
    fn thread1() {
        let i = 0;
        while (i < 10) {
            say i;
            i++;
        }
    }

    thread(thread1);
~~~

As we can see, we are calling the `thread` method with the parameter of `thread1`, which is a function. This function will then run on the newly created thread. From inside of here, you can call the `sleep()` function. As described below.

Apart from this, you could do it like this example, with the variable declaration.

~~~ mani
    let t = thread(thread1);
~~~


### Sleeping a thread
Threads wouldnt be much good if you couldn't sleep them. The `sleep()` functions provides this functionality. Depending on how you initialized the thread, your usage will be different.

If you used the first method, we will modify the function to use the `sleep()` function.

~~~ mani
    fn thread1() {
        let i = 0;
        while (i < 10){
            say i;
            i++;
            sleep(1000);        //counts in milliseconds.
        }
    }
~~~

The above function will now sleep for `1000` milliseconds every time it runs.


If you used the other method, you can perform the sleep function like so:
~~~ mani
    t.sleep(1000);      // counts in milliseconds.
~~~