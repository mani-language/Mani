## Compiler
The compiler for Máni allows you to build your Máni script into a standalone file which can be run anywhere.

### Steps to compile Máni
 1. Write your scripts
   - If you are using other files you have made, don't forget to load them into your script using ```load "fileName.mni"```
   - Write your Máni scripts for example:

~~~ mani
// This is main.mni
# "maps";
load "summation.mni";

let v1 = addTogether(5, 2);
let v2 = addTogether(7, 1);

let myMap = Map();
myMap.add("firstResult", v1);
myMap.add("secondResult", v2);
say myMap.map;
~~~
 
~~~ mani
// This is summation.mni
fn addTogether(a, b) {
    return a + b;
}
~~~
2. Name your main script 'main.mni'
  - The main script is the one you want to run. It is the entry point for the program
3. Copy your scripts to the resources directory
  - This is located under ```src/com/mani/compiler/resources```
4. Compile your code using gradle
  - Run the gradle task buildCompiler using the command ```gradle clean buildCompiler```
  - This will output your compiled program into ```build/libs/Mani-Stable.jar```. This is your compiled file.
5. Run your compiled code
  - This .jar file can be run like a normal .jar file using the command ```java -jar Mani-Stable.jar``` in the directory the file is located.
  
### Arguments
Much like the language Máni is built on, java. Máni can handle program arguments.

The way you access these arguments is through the `args` variable.
The `args` variable is a list, so you can use [all the list methods](lists.md) to access the data.

> This variable is only avaliable to compiled programs.