package main;

import data.ServerSocketFuncs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {


        ServerSocketFuncs serverSocketFuncs = new ServerSocketFuncs();
        ServerSocket serverSocket = new ServerSocket(3333);

        System.out.println("Server Started !");




        while (true) {

            try
            {
                Socket socket = serverSocket.accept();

                if (socket.isBound()) {
                    new Thread(()->{
                       serverSocketFuncs.listenClient(socket);
                    }).start();
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }


    }
}