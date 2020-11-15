package com.battleship.networking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LookAheadObjectInputStreamTest {

    ServerSocket server;
    Socket client;
    Socket serverSocket;
    ObjectOutputStream out;
    LookAheadObjectInputStream in;

    @DisplayName("Create Socket")
    @BeforeEach
    public void createSocket() {
        try {
            server = new ServerSocket(5555);
            client = new Socket("127.0.0.1", 5555);
            serverSocket = server.accept();
            out = new ObjectOutputStream(client.getOutputStream());
            in = new LookAheadObjectInputStream(serverSocket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @DisplayName("Close Socket")
    @AfterEach
    public void closeSocket() {
        try {
            client.close();
            serverSocket.close();
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @DisplayName("Test LookAheadObjectInputStream readObject InvalidClassException")
    @Test
    public void readObjectFailure() {
        try {
            out.writeObject(new boolean[]{false, false, true});
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        assertThrows(InvalidClassException.class, () -> in.readObject());
    }

    @DisplayName("Test LookAheadObjectInputStream readObject success")
    @Test
    public void readObjectSuccess() {

        String startString = "Test_String";
        String str = null;

        try {
            out.writeObject(startString);
            str = (String) in.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            throw new AssertionError("The Look Ahead has failed.");
        }

        assertEquals(str, startString);
    }

}