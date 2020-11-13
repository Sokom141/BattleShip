package com.battleship.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private final ConnectionThread connectionThread = new ConnectionThread();
    private final Consumer<Serializable> onReceiveCallback;
    private boolean isConnected = false;

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
     */
    public void startConnection() {

        connectionThread.start();
    }

    /**
     * Checks if the socket is connected
     *
     * @return true if connected - false otherwise
     */
    public boolean isConnected() {
        return isConnected;
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

            try (
                    ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                    Socket socket = isServer() ? server.accept() : createClient(getIP(), getPort(), 1000); // TODO: implement maxRetry
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new LookAheadObjectInputStream(socket.getInputStream())
            ) {

                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);
                isConnected = true;

                while (true) {
                    Serializable data = (Serializable) in.readObject();
                    onReceiveCallback.accept(data);
                }

            } catch (Exception e) {

                onReceiveCallback.accept("Connection closed.");
            }
        }

        /**
         * Create the new Socket for the client, retry if the connection is not successful.
         *
         * @param ip       the hostname
         * @param port     the port number
         * @param maxRetry maximum tries before returning a null socket
         * @return the Socket connected or null if no connection was made
         */
        private Socket createClient(String ip, int port, int maxRetry) {
            Socket socket = null;
            int counter = 0;

            while (counter < maxRetry) {
                try {
                    counter++;
                    socket = new Socket(ip, port);
                    break;
                } catch (ConnectException connectionException) {
                    System.out.println(counter + " - Waiting for server...");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return socket;
        }
    }
}
