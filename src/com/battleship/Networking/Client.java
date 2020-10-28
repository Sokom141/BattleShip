package com.battleship.Networking;

import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends NetworkConnection {

    private final String ip;
    private final int port;

    public Client(Consumer<Serializable> onReceiveCallback, String ip, int port) {
        super(onReceiveCallback);
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }


    /*
    private String serverIp;
    private int serverPort;

    public Client(String server, int port) {
        serverIp = server;
        serverPort = port;
    }

    public static void main(String[] args) {
        Client c = new Client("79.45.239.1", 6969);
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
    */
}
