package com.muc;

import com.muc.ServerMain;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.*;


public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private String login  = null;

    public ServerWorker(Socket clientSocket){

        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void handleClient() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)); // Reads line by line
        String line;
        while((line = br.readLine()) != null) {
            String[] tokens = line.split("/");
            if (tokens.length > 0 && tokens != null) {
                String cmd = tokens[0];
                if ("quit".equalsIgnoreCase(cmd)) {
                    break;
                } else if("login".equalsIgnoreCase(cmd)) {
                    handleLogin(outputStream, tokens);
                }else {
                    String msg = "Unknown " + cmd + " \n";
                    outputStream.write(msg.getBytes());
                }
            }

        }

                clientSocket.close();
            }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if(tokens.length == 3){
            String loginUser = tokens[1];
            String password = tokens[2];

            if(loginUser.equals("guest") && password.equals("guest")){
                String msg = "ok login";
                outputStream.write(msg.getBytes());
                this.login = loginUser;
                System.out.println("User has logged in: " + loginUser);

            }else{
                String msg = "error! login";
                outputStream.write(msg.getBytes());
            }
        }
    }

}