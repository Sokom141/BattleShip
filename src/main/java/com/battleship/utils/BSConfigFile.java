package com.battleship.utils;

import java.awt.*;
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

    public static void newFile() {

        Properties props = new Properties();

        props.setProperty("Name", "UserName");
        props.setProperty("Avatar_Path", "src/main/resources/anonymous.png");
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

    public static String readProperties(String key) {

        String value;

        try {
            bsConfigFile = Paths.get("config.properties");
            Reader reader = Files.newBufferedReader(bsConfigFile);
            props.load(reader);
            reader.close();
            value = props.getProperty(key);
        } catch (IOException e) {
            newFile();
            value = props.getProperty(key);
        }

        return value;
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
    public static Color manageColors(String colorString) {
        return switch (colorString) {
            case "CYAN" -> Color.CYAN;
            case "GREEN" -> Color.GREEN;
            case "MAGENTA" -> Color.MAGENTA;
            case "ORANGE" -> Color.ORANGE;
            case "BLACK" -> Color.BLACK; // this color has been removed because it's the default color for destroyed ships
            default -> Color.BLUE;
        };
    }
    public static void updateConfiguration(String newName,String setColor, String resolutionWidth, String resolutionHeight){
        BSConfigFile.modifyFile("Name", newName);
        BSConfigFile.modifyFile("Color", setColor);
        BSConfigFile.modifyFile("Resolution_Width", resolutionWidth);
        BSConfigFile.modifyFile("Resolution_Height", resolutionHeight);
    }
}