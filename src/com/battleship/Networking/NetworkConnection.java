package com.battleship.Networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private final ConnectionThread connectionThread = new ConnectionThread();
    private final Consumer<Serializable> onReceiveCallback;

    /**
     * Constructor of the class
     *
     * @param onReceiveCallback function called when a message is received
     */
    public NetworkConnection(Consumer<Serializable> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connectionThread.setDaemon(true);
    }

    /**
     * Starts the connection
     *
     * @throws Exception connection failed
     */
    public void startConnection() {

        connectionThread.start();
    }

    /**
     * Sends a serializable object
     *
     * @param data data to write on the ObjectOutputStream
     * @throws Exception failed to send data
     */
    public void send(Serializable data) throws Exception {

        connectionThread.out.writeObject(data);
    }

    /**
     * Close the connection
     *
     * @throws Exception connection failed
     */
    public void closeConnection() throws Exception {

        connectionThread.socket.close();
    }

    /**
     * Checks if the socket is connected
     *
     * @return true if the socket is connected
     */
    public boolean isConnected() {
        return connectionThread.socket.isConnected();
    }

    /**
     * @return true if the class is a server, true otherwise
     */
    protected abstract boolean isServer();

    /**
     * @return a String containing the IP
     */
    protected abstract String getIP();

    /**
     * @return an int specifying the Port
     */
    protected abstract int getPort();

    /**
     * Private class to handle the connection, extends Thread
     */
    private class ConnectionThread extends Thread {

        private Socket socket;
        private ObjectOutputStream out;

        @Override
        public void run() {

            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                 Socket socket = isServer() ? Objects.requireNonNull(server).accept() : new Socket(getIP(), getPort());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);

                while (true) {
                    Serializable data = (Serializable) in.readObject();
                    onReceiveCallback.accept(data);
                }

            } catch (Exception e) {

                onReceiveCallback.accept("Connection closed.");
            }
        }
    }
}
