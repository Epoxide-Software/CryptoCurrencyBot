package net.darkhax.xchange.command;

import net.darkhax.xchange.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

public class CommandCurrency implements Command {

    @Override
    public void processCommand (IMessage message, String[] params) {
        
        for (String currency : Main.config.currency) {
            
            CurrencyData data = CurrencyData.get(currency);
            final EmbedBuilder embed = new EmbedBuilder();
            embed.withTitle(data.getName() + " ( " + data.getSymbol() + " )");
            embed.appendField("Price USD", data.getPriceUsd(), true);
            embed.appendField("Price BTC", data.getPriceBtc(), true);
            embed.appendField("1 USD", "" + (1f / new Float(data.getPriceUsd())), true);
            embed.appendField("Last Hour", data.getPercentChange1h() + "%", true);
            embed.appendField("Last Day", data.getPercentChange24h() + "%", true);
            embed.appendField("Last Week", data.getPercentChange7d() + "%", true);
            embed.withThumbnail(data.getLogo());
            embed.withFooterText("Powered by CoinMarketCap");
            Utilities.sendMessage(message.getChannel(), embed.build());
        }
    }

    @Override
    public String getDescription () {
        
        return "none";
    }
}
