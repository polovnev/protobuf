package com.polovnev;

import com.polovnev.data.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class GreetingClient {
    public static void main(String[] ar) {
        int serverPort = 1234;
        String address = "127.0.0.1";
        Socket socket = null;
        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            socket = new Socket(ipAddress, serverPort);
            OutputStream sout = socket.getOutputStream();
            Data.MessageData messageData = Data.MessageData.newBuilder().setData("hello").build();
            messageData.writeDelimitedTo(sout);

            InputStream sin = socket.getInputStream();
            Data.MessageData response = Data.MessageData.parseDelimitedFrom(sin);
            System.out.println(response.getData());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }
}
