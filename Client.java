/*It is the Client */

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 9999);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Thread inputThread = new Thread(this::handleInput);
            inputThread.start();

            String inMessage;
            while (!done && (inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            //ignore
        }
    }

    private void handleInput() {
        try {
            BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (!done && (message = inReader.readLine()) != null) {
                out.println(message);
                if (message.trim().equals("/quit")) { 
                    
                    break;       
                }
            }
            inReader.close();   
            shutdown();        
        } catch (IOException e) {
          
            shutdown();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
