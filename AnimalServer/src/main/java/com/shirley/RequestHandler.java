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
import java.util.ArrayList;
import java.util.List;
import javax.json.stream.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

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
            String stringRead = inputStream.readUTF();
            if (stringRead.equalsIgnoreCase("XML Request")) {
                // XML code 
                
            } 
            else if (stringRead.contains("petId")) {
                String id = null;
                // Extract pet id from json array
                JSONArray jArray = new JSONArray(stringRead);
                id = jArray.getJSONObject(0).get("petId").toString();
                
                if (id != null) {
                    // Process as a query search
                    String animal = _findAnimalManager.getAnimalByAnimalId(id);
                    if (animal == null) {
                        outputStream.writeUTF("The animal does not exist"
                                + " in the database.");
                    } else {
                        outputStream.writeUTF(animal);
                        outputStream.flush();
                    }
                }
                
            } else {
                outputStream.writeUTF("Invalid Request");
                    outputStream.flush();
            }
            
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
