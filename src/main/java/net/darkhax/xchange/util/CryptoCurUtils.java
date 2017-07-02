package net.darkhax.xchange.util;

import java.util.*;

import net.darkhax.xchange.*;
import net.darkhax.xchange.lib.*;
import sx.blah.discord.api.internal.json.objects.*;
import sx.blah.discord.util.*;

public class CryptoCurUtils {
      
	public static final Comparator<CurrencyData> PRICE_COMPARATOR = (CurrencyData first, CurrencyData second) -> Double.compare(Double.parseDouble(first.getPriceBtc()), Double.parseDouble(second.getPriceBtc()));
    
	public static String getCurrencyUrl(String id) {
        
        //TODO maybe add other currency types for comparison
        return String.format("https://api.coinmarketcap.com/v1/ticker/%s/?convert=%s", id, "USD");
    }
    
    public static String getCurrencyImage(String id) {
        
        return String.format("https://files.coinmarketcap.com/static/img/coins/32x32/%s.png", id);
    }
    
    public static EmbedObject getCurrencyMessage(String id) {
        
        CurrencyData data = CurrencyData.get(id);
        
        if (data == null) {
        	
        	return null;
        }
        
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
        return embed.build();
    }
    
    public static CurrencyData[] getCurrencies(int amount) {
    	
        String jsonString = DataUtils.readJson(CryptoCurUtils.getCurrencyUrl("https://api.coinmarketcap.com/v1/ticker/?convert=USD&limit=" + amount));
        
        if (jsonString.equals("{}")) {

        	return new CurrencyData[0];
        }
        	
        return Main.GSON.fromJson(jsonString, CurrencyData[].class);
    }
}
