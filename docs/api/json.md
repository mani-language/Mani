## JSON
This API is used for generating and reading regular JSON code.

### How to import
~~~ mani
    load "json";
~~~

### List of all points
~~~ mani
    json_parse( );
    json_encode( );
~~~

### Function descriptions

#### json_parse();
Usage: `json_parse( someJSONText );`

Returns a map created from the json provided.

#### json_encode();
Usage: `json_encode( mapObject );`

Returns a string in JSON format created from the provided map object.

