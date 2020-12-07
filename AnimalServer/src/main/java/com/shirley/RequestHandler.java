/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirley;

import java.net.Socket;

/**
 *
 * @author Chantal Shirley
 */
public class RequestHandler {
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
}
