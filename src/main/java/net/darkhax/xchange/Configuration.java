package net.darkhax.xchange;

import java.io.*;

import com.google.gson.*;

public class Configuration {
    
    public static Configuration config;
    
    public String authToken = "Enter your auth token!";
    public String key = "!xchange";
    public String[] channels = {};
    public String[] currency = {"bitcoin", "ethereum", "curecoin"};
    
    public static Configuration updateConfig() {
        
        if (config == null) {
            
            File file = new File("./config.json");
            
            //Read the config if it exists
            if (file.exists()) {
                
                System.out.println("Reading existing config");
                try (Reader reader = new FileReader(file)) {
                    
                    config = Main.GSON.fromJson(reader, Configuration.class);
                }
                
                catch (IOException e) {
                  
                    e.printStackTrace();
                }
            }
            
            //Otherwise make a new config file
            else {
                
                System.out.println("Creating new config");
                config = new Configuration();
                
                try (FileWriter writer = new FileWriter(file)) {

                    Main.GSON.toJson(config, writer);
                } 
                
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return config;
    }
}
