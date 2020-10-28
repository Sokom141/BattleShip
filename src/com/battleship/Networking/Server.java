package com.battleship.Networking;

import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection {

    public int port;

    public Server(Consumer<Serializable> onReceiveCallback, int port) {
        super(onReceiveCallback);
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIP() {
        try {
            return IpChecker.getIp();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getPort() {
        return port;
    }
}
