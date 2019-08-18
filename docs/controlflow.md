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

### Switch-case statements
They are used to match discrete conditions and execute the code matching those discrete conditions. Although this can be achieved by an if-else-if ladder, but using switch-case can be more readable.

Consider the following if-else-if ladder code:
~~~ mani
    let day = 1;
    if (day == 1) {
        say "Sunday";
    } else if (day == 2) {
        say "Monday";
    } else if (day == 3) {
        say "Tuesday";
    } else if (day == 4) {
        say "Wednesday";
    } else if (day == 5) {
        say "Thursday";
    } else if (day == 6) {
        say "Friday";
    } else if (day == 7) {
        say "Saturday";
    } else {
        say "Invalid day";
    }
~~~
The above code can be re-written as switch-case code as follows:
~~~ mani
    let day = 1;
    switch (a) {
        case 1 :
            say "Sunday";
            break;
        case 2 :
            say "Monday";
            break;
        case 3 :
            say "Tuesday";
            break;
        case 4 :
            say "Wednesday";
            break;
        case 5 :
            say "Thursday";
            break;
        case 6 :
            say "Friday";
            break;
        case 7 :
            say "Saturday";
            break;
        default :
            say "Invalid day";
    }
~~~
