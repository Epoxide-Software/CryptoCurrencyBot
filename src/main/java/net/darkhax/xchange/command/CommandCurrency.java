package net.darkhax.xchange.command;

import net.darkhax.xchange.*;
import net.darkhax.xchange.util.*;
import sx.blah.discord.handle.obj.*;

public class CommandCurrency implements Command {

    @Override
    public void processCommand (IMessage message, String[] params) {
        
        for (String currency : Main.config.currency) {
            
            MessageUtils.sendMessage(message.getChannel(), CryptoCurUtils.getCurrencyMessage(currency));
        }
    }

    @Override
    public String getDescription () {
        
        return "none";
    }
}
