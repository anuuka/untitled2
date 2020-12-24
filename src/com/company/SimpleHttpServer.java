package com.company;

import com.company.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class SimpleHttpServer {
    /**
     * @param args
     */
    public static void main(String[] args) {

            try {
                ServerSocket serverConnect = new ServerSocket(8080);
                System.out.println("The socket is created on port 8080");
                while (true){
                    ClientHandler myServer = new ClientHandler(serverConnect.accept());
                    System.out.println("The connection is established");
                    Thread thread = new Thread(myServer);
                    thread.start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


    }
}
