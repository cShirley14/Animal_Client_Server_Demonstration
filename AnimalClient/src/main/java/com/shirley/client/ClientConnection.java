/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirley.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Chantal Shirley
 */
public class ClientConnection {
    private static final int PORT = 49999;
    private static final String HOST_NAME = "localhost";
    
    public static String getAnimalFromServer(String id) throws
            UnknownHostException, IOException {
        String animalData = null;
        
        Socket socket = new Socket(HOST_NAME, PORT);
        // Get info from form submit
        
        DataInputStream inputStream = new DataInputStream(
                socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(
        socket.getOutputStream());
        
        outputStream.writeUTF(id);
        outputStream.flush();
        animalData = inputStream.readUTF();
        inputStream.close();
        outputStream.close();
        
        // read XML code into hashmap 
        // convert hashmap data into readable text

        return animalData;
    }

}
