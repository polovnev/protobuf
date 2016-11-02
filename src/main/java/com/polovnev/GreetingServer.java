package com.polovnev;

import com.polovnev.data.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GreetingServer extends Thread {
    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
    }

    public void run() {
        try {
            Socket socket = serverSocket.accept();
            InputStream sin = socket.getInputStream();
            Data.MessageData messageData = Data.MessageData.parseDelimitedFrom(sin);
            String s = messageData.getData();
            System.out.println(s);

            OutputStream sout = socket.getOutputStream();
            Data.MessageData response = Data.MessageData.newBuilder().setData("world").build();
            response.writeDelimitedTo(sout);
        } catch (SocketTimeoutException s) {
            s.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 1234;
        try {
            Thread t = new GreetingServer(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}