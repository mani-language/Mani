## Sockets
Sockets are a really handy feature of most languages.
As many people use sockets in their projects, we added them to ours.

> Websockets cannot be used at the same time as sockets!

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
    .emit();
    .send();
    .close();
    .id();
    .on();
    .logging();
~~~

### Function descriptions

#### newSocket();
Usage: `newSocket("url");` or `newSocket("url", options_map);`
Used to create a new socket connection with the url provided.
> Go to bottom of this page for information on the options avaliable.

> So far, we dont support `ws` or `wss` urls.

### Extension descriptions

#### .open();
Usage: `socketObject.open( );`
Used to start the connection to the provided url.

#### .emit();
Usage: `socketObject.emit( event, message )`
Used to emit a packet to the server, with an event message.

#### .send();
Usage: `socketObject.send( package );`
Used to send a package to the server, much like `emit`, but without the event name.

#### .close();
Usage: `socketObject.close( );`
Used to close the connection to the provided url.

#### .id();
Usage: `socketObject.id( );`
Used to return the ID of the connected socket.

#### .on();
Usage: `socketObject.on( event, function );`
Used to run a function when we receive a package with a specified event.

#### .logging();
Usage: `socketObject.logging( boolean );`
Used to toggle on or off the default logging. Simply provide either true or false as the argument.

### Options for newSocket()

#### rememberUpgrade
> Requires `Number`.
#### secure
> Requires `Number`.
#### timestampRequests
> Requires `Number`.
#### upgrade
> Requires `Number`.
#### policyPort
> Requires `Number`.
#### port
> Requires `Number`.
#### host
> Requires `String`.
#### hostname
> Requires `String`.
#### path
> Requires `String`.
#### query
> Requires `String`.
#### timestampParam
> Requires `String`.
#### transports
> Requires `List of Strings`.

### Working example.
In this example, we are using [Asher.Ai](https://github.com/crazywolf132/Asher.Ai) as our socket server.
The path for this will be `http://localhost:4416`.

~~~ mani
    load "sockets";
    
    // Creating a connection to the Asher server.
    let s = newSocket("http://localhost:4416/");
    
    // This is the function used 
    fn handleMessage(msg) {
        say "Asher >> " + msg;
    }
    
    // This is the function that will get run once we are connected.
    fn main() {
        let t = ask("What do you want to ask? ");
        s.emit("command", t);
    }
    
    // This function will run on the connection event.
    fn connected() {
      // Letting the user know we are now connected.
      say "We've connected successfully";
      // We are now going to run the main method.
      main();
    }
    
    // Opening the connection to the server.
    s.open();
    
    // We are defining the event handlers here.
    // The first event handler handles the connection event.
    s.on(EVENT_CONNECT, connected);
    // This event handler handles the packets with the event type of "response"
    s.on("response", handleMessage);
    
~~~