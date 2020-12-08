package com.shirley;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Chantal Shirley
 */
public class AnimalDataServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port = 49999;
        int threadCount = 100;
        int timeoutLength = 3000;
        
        ExecutorService executorService = 
                Executors.newFixedThreadPool(threadCount);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.printf("ServerSocket listening on port %d\n", port);
            
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                socket.setSoTimeout(timeoutLength);
                executorService.execute((Runnable) new RequestHandler(socket));
            }
        } catch (IOException ex) {
            System.out.println("CATASTROPHIC FAILURE: Server Stopping!");
            System.out.println(ex.getMessage());
        }
    }
    
}
