package net.darkhax.xchange;

import java.util.*;

import com.google.gson.*;

import net.darkhax.xchange.command.*;
import sx.blah.discord.api.*;

public class Main {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final String SEPERATOR = System.lineSeparator();
    
    public static Configuration config;
    public static IDiscordClient instance;
    
    public static void main(String... main) {
        
        config = Configuration.updateConfig();
        
        instance = new ClientBuilder().withToken(config.authToken).login();
        instance.getDispatcher().registerListener(new CommandHandler());
    }
    
    public static void registerCommands(Map<String, Command> registry) {
        
        registry.put("currency", new CommandCurrency());
        registry.put("lookup", new CommandLookup());
        registry.put("popular", new CommandPopular());
    }
}
