/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirley;

import com.shirley.animal.Animal;
import com.shirley.taskhandler.FindAnimal;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author Chantal Shirley
 */
public class RequestHandler implements Runnable {
    private Socket socket;
    private FindAnimal _findAnimalManager = new FindAnimal();
    
    /**
     * 
     * @param socket 
     */
    public RequestHandler(Socket socket) {
        if (null == socket) {
            throw new IllegalArgumentException("Socket cannot be null.");
        }
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try(
            DataInputStream inputStream = new DataInputStream(
                    socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(
                    socket.getOutputStream());
        ) {
            // code tester
            
            // MySQL code
            String id = inputStream.readUTF();
            String animal = _findAnimalManager.getAnimalByAnimalId(id);
            if (animal == null) {
                outputStream.writeUTF("The animal does not exist"
                        + " in the database.");
            } else {
                outputStream.writeUTF(animal);
                outputStream.flush();
            }
            // XML code 
            
        } catch (SocketTimeoutException ste) {
            System.out.println("\tSocket connection timed out: " + 
                    ste.getMessage());
        } catch (IOException ioe) {
            System.out.println("\tIO Error: " + ioe.getMessage());
        } catch (Exception ex) {
            System.out.println("\tError: " + ex.getMessage());
        }
    }
    
}
