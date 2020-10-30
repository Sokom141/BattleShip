package com.battleship.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BSConfigFile {

    private static final Properties props = new Properties();
    public static Path bsConfigFile;

    // TODO: write JavaDoc
    public static String readFile(String key) {

        String value;

        try {
            bsConfigFile = Paths.get("config.properties");
            Reader reader = Files.newBufferedReader(bsConfigFile);
            props.load(reader);
            reader.close();
            value = props.getProperty(key);
        } catch (IOException e) {
            newFile();
            value = readFile(key);
        }

        return value;
    }

    public static void newFile() {

        Properties props = new Properties();

        props.setProperty("Name", "UserName");
        props.setProperty("Avatar_Path", "src/resources/anonymous.png");
        props.setProperty("Resolution_Width", "1000");
        props.setProperty("Resolution_Height", "500");
        props.setProperty("Color", "BLUE");

        Path propertyFile = Paths.get("config.properties");

        try {
            Writer propWriter = Files.newBufferedWriter(propertyFile);
            props.store(propWriter, "Settings");
            propWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // TODO: Compare this one and line #26
    public static String readProperties(String property) {

        return props.getProperty(property);
    }

    public static void modifyFile(String key, String value) {

        try {
            props.setProperty(key, value);
            Writer writer = Files.newBufferedWriter(bsConfigFile);
            props.store(writer, "Settings");
            writer.close();
        } catch (IOException | NullPointerException ex) {
            newFile();
        }
    }
}