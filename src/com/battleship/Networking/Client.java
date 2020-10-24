package com.battleship.Networking;

import java.io.*;
import java.net.Socket;

public class Client {

    private String serverIp;
    private int serverPort;

    public Client(String server, int port) {
        serverIp = server;
        serverPort = port;
    }

    public static void main(String[] args) {
        Client c = new Client("localhost", 1234);
        c.run();
    }

    public void run() {
        try {
            System.out.println("Connecting to " + serverIp + " on port " + serverPort);
            Socket client = new Socket(serverIp, serverPort);

            System.out.println("Connected to " + client.getRemoteSocketAddress());

            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + client.getLocalSocketAddress());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server Says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
