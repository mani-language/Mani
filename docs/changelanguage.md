## Changing Language

What does this mean?

This means you dont need to know English to work with Máni.

### Changing the language
It can be very simple to change the language simply append
the following somewhere in your code.
~~~ mani
    CHANGE_LANG "Language name";
~~~

> We recommend adding this to the top of the file.

### Language list
Currently there is only a few languages supported.
~~~ mani
    - German
    - Japanese
    - Spanish
~~~

### Adding a language
Adding a language is just as easy as loading one.
1) Add a new Java file to the `com.mani.lang.language` directory.
Create your class with the appropriate name.

2) use the following example as a guide for how to go about adding the translations.

~~~ Java
public class Spanish implements Lang {

    @Override
    public Object init(Map<String, String> keywords) {
        keywords.put("say", "contar");
        keywords.put("break", "descanso");
        keywords.put("return", "regreso");
        keywords.put("class", "clase");
        keywords.put("and", "y");
        keywords.put("or", "o");
        keywords.put("false", "falso");
        keywords.put("true", "cierto");
        keywords.put("this", "esto");
        keywords.put("loop", "lazo");
        keywords.put("while", "mientras");
        keywords.put("STRICT", "ESTRICTO");
        keywords.put("internal", "privado");
        keywords.put("for", "para");
        keywords.put("if", "si");
        keywords.put("nil", "null");
        keywords.put("let", "dejar");
        keywords.put("load", "carga");


        return keywords;
    }
}
~~~

As we can see, it is simpily a map, with the key value being the english word to replace, then the value being the translated version. 
