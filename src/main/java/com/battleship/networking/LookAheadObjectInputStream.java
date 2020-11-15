package com.battleship.networking;

import com.battleship.game.playerpack.PlayerData;

import javax.swing.*;
import java.io.*;
import java.util.HashSet;

/**
 * Strengthened version of ObjectInputStream to allow
 * only whitelisted classes to be deserialized.
 */
public class LookAheadObjectInputStream extends ObjectInputStream {

    private final HashSet<String> whitelist = new HashSet<>(4);

    /**
     * Creates an ObjectInputStream that reads from the specified InputStream.
     * A serialization stream header is read from the stream and verified.
     * This constructor will block until the corresponding ObjectOutputStream
     * has written and flushed the header.
     *
     * <p>The serialization filter is initialized to the value of
     * {@linkplain ObjectInputFilter.Config#getSerialFilter() the system-wide filter}.
     *
     * <p>If a security manager is installed, this constructor will check for
     * the "enableSubclassImplementation" SerializablePermission when invoked
     * directly or indirectly by the constructor of a subclass which overrides
     * the ObjectInputStream.readFields or ObjectInputStream.readUnshared
     * methods.
     *
     * @param in input stream to read from
     * @throws StreamCorruptedException if the stream header is incorrect
     * @throws IOException              if an I/O error occurs while reading stream header
     * @throws SecurityException        if untrusted subclass illegally overrides
     *                                  security-sensitive methods
     * @throws NullPointerException     if {@code in} is {@code null}
     */
    public LookAheadObjectInputStream(InputStream in) throws IOException {
        super(in);
        buildWhitelist();
    }

    /**
     * Builds the whitelist that allows only game objects to be sent.
     */
    private void buildWhitelist() {
        whitelist.add(PlayerData.class.getName());
        whitelist.add(ImageIcon.class.getName());
        whitelist.add(String.class.getName());
        whitelist.add(int[].class.getName());
    }

    /**
     * Load the local class equivalent of the specified stream class
     * description if it's in the whitelist.
     *
     * @param desc an instance of class {@code ObjectStreamClass}
     * @return a {@code Class} object corresponding to {@code desc}
     * @throws IOException            any of the usual Input/Output exceptions.
     * @throws ClassNotFoundException if class of a serialized object cannot
     *                                be found.
     */
    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {

        if (!whitelist.contains(desc.getName())) {
            throw new InvalidClassException("Unauthorized deserialization attempt", desc.getName());
        }

        return super.resolveClass(desc);
    }

}
