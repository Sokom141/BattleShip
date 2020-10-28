package com.battleship.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BSConfigFile {

   private static Properties props;
   public static Path bsConfigFile;

   public static String readFile(String key) throws IOException {
       String value;
       try {
           props = new Properties();
           bsConfigFile = Paths.get("config.properties");
           Reader reader = Files.newBufferedReader(bsConfigFile);
           props.load(reader);
           reader.close();

           value = props.getProperty(key);
           return value;

       } catch (Exception ex) {
           newFile();
           value = readFile(key);
       }
       return value;
   }
   public static void newFile() {

       Properties props = new Properties();
       props.setProperty("Name", "UserName");
       props.setProperty("Resolution_Width", "1000");
       props.setProperty("Resolution_Height", "500");
       props.setProperty("Color", "BLUE");

       Path PropertyFile = Paths.get("config.properties");
       try{
           Writer PropWriter = Files.newBufferedWriter(PropertyFile);
           props.store(PropWriter,"Settings");
           PropWriter.close();
       }
       catch(IOException Ex){
           System.out.println("IO Exception : "+ Ex.getMessage());
       }
   }

   public static String readProperties(String property){

           return props.getProperty(property);

   }
   
   public static void modifyFile(String key, String value) throws IOException {
       try {

           props.setProperty(key, value);
           Writer writer = Files.newBufferedWriter(bsConfigFile);
           props.store(writer, "Settings");
           writer.close();
       }
       catch (FileNotFoundException ex){
           newFile();
       }
   }
}