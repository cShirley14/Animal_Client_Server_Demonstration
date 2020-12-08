/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirley;

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
