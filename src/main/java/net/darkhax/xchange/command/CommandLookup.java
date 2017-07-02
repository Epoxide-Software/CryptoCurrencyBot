package net.darkhax.xchange.command;

import java.util.*;

import net.darkhax.xchange.*;
import net.darkhax.xchange.util.*;
import sx.blah.discord.api.internal.json.objects.*;
import sx.blah.discord.handle.obj.*;

public class CommandLookup implements Command {

    @Override
    public void processCommand (IMessage message, String[] params) {
        
    	Set<String> failed = new HashSet<>();
    	List<EmbedObject> messages = new ArrayList<>();
    	
    	for (String arg : params) {
    		
			EmbedObject curMessage = CryptoCurUtils.getCurrencyMessage(arg);
			
			if (curMessage != null) {
				
				messages.add(curMessage);
			}
			
			else failed.add(arg);
    	}
    	
    	for (EmbedObject obj : messages) {
    		
    		MessageUtils.sendMessage(message.getChannel(), obj);
    	}
    	
    	MessageUtils.sendMessage(message.getChannel(), "The following were not valid! " +  String.join(", ", failed));
    }

    @Override
    public String getDescription () {
        
        return "none";
    }
}
