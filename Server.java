/* It is the server */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            // ignore
            shutdown();
        }
    }

    public void broadcast(String message) {
        for (ConnectionHandler ch : connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }

        
        if (message.equals("/quit")) {
            synchronized (connections) {
                connections.removeIf(ch -> ch != null && ch.isDone());
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            pool.shutdownNow();
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections) {
                ch.shutdown();
            }
        } catch (IOException e) {
            // Ignore
        }
    }

    class ConnectionHandler implements Runnable {

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private boolean done;
        private String nickname;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter a nickname");
                nickname = in.readLine();
                System.out.println(nickname + " connected!");
                broadcast(nickname + " joined the chat!");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equals("/quit")) {
                        break; 
                    } 
                    else if (message.startsWith("/nick ")) {
                        String[] messageSplit = message.split(" ", 2);
                        
                        if (messageSplit.length == 2) {
                            String newNickname = messageSplit[1];
                            broadcast(nickname + " renamed themselves to " + newNickname);
                            System.out.println(nickname + " renamed themselves to " + newNickname);
                            nickname = newNickname;
                            out.println("Successfully changed nickname to " + nickname);
                        } 
                        else {
                            out.println("No nickname provided!");
                        }
                    } 
                    else {
                        broadcast(nickname + ":" + message);
                    }
                }
            } catch (IOException e) {
                // ignore
            } 
            finally {
                done = true;
                broadcast(nickname + " has left the chat!");
                shutdown(); 
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void shutdown() {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                // Ignore
            }
        }

        public boolean isDone() {
            return done;
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
