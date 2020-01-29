package com.muc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerMain {
    public static void main(String[] args) {
        // To create a network server first have to create a server socket
        int port = 8818;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) { // Need to put this in a loop because you will continuously accept from client
                System.out.println("About to accept a client accepting...");
                Socket clientSocket = serverSocket.accept(); // Represent the connection to the client
                System.out.println("Accepted connection from" + clientSocket);
                ServerWorker worker = new ServerWorker(clientSocket);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }// do exception handling
    }
}


