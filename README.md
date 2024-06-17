# Java TCP Chat Box

## Overview

This project implements a simple TCP-based chat application in Java. The chat application allows multiple clients to connect to a server and communicate with each other in real-time. 
The server handles all client connections and broadcasts messages from any client to all connected clients.

## Features

- **Multi-client support:** Multiple clients can connect to the server simultaneously.
- **Real-time messaging:** Clients can send and receive messages in real-time.
- **Server broadcast:** Messages from any client are broadcasted to all connected clients.
- **Simple command interface:** Easy-to-use text-based interface for sending and receiving messages.

## Requirements

- Java Development Kit (JDK) 8 or higher
- A text editor or Integrated Development Environment (IDE)

## Setup and Installation

### 1. Clone the repository

```bash
git clone https://github.com/your-username/java-tcp-chat-box.git
cd java-tcp-chat-box
```

### 2. Compile the source code

Navigate to the directory where the source code is located and compile the server and client classes:

```bash
javac Server.java
javac Client.java
```

### 3. Run the server

Start the server by running:

```bash
java Server
```

The server will start and listen for client connections on a predefined port (default: 12345).

### 4. Run the client

Start the client by running:

```bash
java Client
```

You will be prompted to enter the server's IP address and port number. Enter the details to connect to the server.

## Usage

### Server

The server waits for client connections and manages the communication between clients. When a client sends a message, the server broadcasts it to all connected clients.

### Client

The client connects to the server and allows the user to send and receive messages. Simply type your message and press Enter to send it. All incoming messages from other clients will be displayed in the console.

### Example

1. Start the server:

   ```bash
   java Server
   ```

2. Start multiple clients in separate terminal windows:

   ```bash
   java Client
   ```

3. Each client can send messages that will be received by all other connected clients.

## Customization

### Changing the Server Port

To change the default server port, modify the `PORT` variable in the `Server.java` file:

```java
private static final int PORT = 12345; // Change this value to your desired port
```

### Changing the Buffer Size

To change the buffer size for reading messages, modify the `BUFFER_SIZE` variable in the `Client.java` and `Server.java` files:

```java
private static final int BUFFER_SIZE = 1024; // Change this value to your desired buffer size
```

## Troubleshooting

- **Connection issues:** Ensure that the server is running and the IP address and port number are correct.
- **Firewall:** Check if your firewall settings are blocking the port used by the server.
- **Java version:** Ensure you are using a compatible version of Java (JDK 8 or higher).

## Contributing

Contributions are welcome! If you have any suggestions, bug reports, or improvements, please create an issue or submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- This project was inspired by the need for simple, easy-to-understand examples of TCP-based communication in Java.
- Thanks to all the contributors who have helped improve this project.

