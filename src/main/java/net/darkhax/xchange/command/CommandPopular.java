package net.darkhax.xchange.command;

import java.util.*;

import net.darkhax.xchange.*;
import net.darkhax.xchange.lib.*;
import net.darkhax.xchange.util.*;
import sx.blah.discord.handle.obj.*;

public class CommandPopular implements Command {

    @Override
    public void processCommand (IMessage message, String[] params) {
        
    	int amount = (params.length == 1) ? Integer.parseInt(params[0]) : 10;
    	CurrencyData[] data = CryptoCurUtils.getCurrencies(amount);
    	Arrays.sort(data, CryptoCurUtils.PRICE_COMPARATOR);
    	
    	StringBuilder builder = new StringBuilder();
    	
    	for (CurrencyData currency : data) {
    		
    		builder.append(currency.getName() + " (" + currency.getSymbol() + ") - $" + currency.getPriceUsd() + " USD" + Main.SEPERATOR);
    	}
    	
    	String theMessage = builder.toString();
    	System.out.println(theMessage);
    	MessageUtils.sendMessage(message.getChannel(), theMessage.substring(0, Math.min(theMessage.length(), 2000)));
    }

    @Override
    public String getDescription () {
        
        return "none";
    }
}
