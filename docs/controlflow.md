## Control flow
Máni has a variety of control flow statemnets to help you with your code. Control flow is used to determine which chunks of code are run, and how many times.

### If statement
It can be handy to have the ability to run different sections of code, when under different conditions. An example of this might be an age checker for a movie.

~~~ mani
    let age = 17;

    if (age >= 60) {
        say "Free movie!";
    }
~~~

Along with the if-statement, there is a counter. An else statement. This will run in the event that the if-statement didnt meet the set conditions.
~~~ mani
    let age = 15;
    if (age >= 20) {
        say "Your an adult now!";
    } else {
        say "Your still a child!";
    }
~~~

If you are wanting complex control flow statement, with more conditions. You can use "else if" statements.

~~~ mani
    let age = 15;
    if (age >= 18) {
        say "You can see R rated movies";
    } else if (age >= 15) {
        say "You can see MA15+ movies";
    } else {
        say "You can see PG movies!";
    }
~~~