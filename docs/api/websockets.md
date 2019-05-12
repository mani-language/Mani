## Websockets
Websockets are the exact same as [Sockets](api/sockets.md), except they allow `ws` and `wss` requests.
They also have slightly different functions.

> Sockets cannot be used at the same time as websockets!

### How to import
~~~ mani
    load "sockets";
~~~

### List of all points
~~~ mani
    newSocket();
~~~

### List of all extensions
~~~ mani
    .open();
    .isOpen();
    .sendPing();
    .sendPong();
    .getState();
    .on();
    .setHeaders();
    .clearHeaders();
    .close();
    .send();
~~~

### Function descriptions

#### newSocket();
Usage: `newSocket("url");`
Used to create a new socket connection with the url provided.
> It wont naturally start the connection. You must then invoke `.open()`

### Extension descriptions

#### .open();
Usage: `socketObject.open( );`
Used to start the connection to the provided url.

#### .isOpen();
Usage: `socketObject.isOpen( );`
Used to return if the socket connection is open or not.
> Returns a boolean.

#### .sendPing();
Usage: `socketObject.sendPing( );`
Used to send a ping message to the server.

#### .sendPong();
Usage: `socketObject.sendPong( );`
Used to return a pong message to the server.

#### .getState();
Usage: `socketObject.getState( );`
Used to return the current state of the connection.

#### .on();
Usage: `socketObject.on( function );`
Used to accept any messages from the server.
There is no way to check what kind of message it is, so we must push it to a function with 1 parameter. This function will then be invoked every time we get a message. From there, you can do as you like with the message.

#### .setHeaders();
Usage: `socketObject.setHeaders( mapObject );`
Used to set the headers of the socket requests.
> Map must have a string and value pair, both of string type.

#### .clearHeaders();
Usage: `socketObject.clearHeaders( );`
Used to clear any set headers on the socket request.

#### .close();
Usage: `socketObject.close( );`
Used to close the socket connection to the provided url.

#### .send();
Usage: `socketObject.send( message );`
Used to send a message to the server.


### Working example.
Here we are going to use the discord gateway API.

~~~ mani
load "webSocket";

// Creates a socket object.
let connection = newSocket( "wss://gateway.discord.gg/?v=6&encoding=json" );

// The handler for all the data returned.
fn handler( data ) {
    say data;
}

// Seting the handler.
connection.on( handler );

// Opening the connection.
connection.open( );
~~~