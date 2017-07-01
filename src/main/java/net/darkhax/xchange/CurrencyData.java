package net.darkhax.xchange;

import com.google.gson.annotations.*;

public class CurrencyData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("price_usd")
    @Expose
    private String priceUsd;
    @SerializedName("price_btc")
    @Expose
    private String priceBtc;
    @SerializedName("24h_volume_usd")
    @Expose
    private String _24hVolumeUsd;
    @SerializedName("market_cap_usd")
    @Expose
    private String marketCapUsd;
    @SerializedName("available_supply")
    @Expose
    private String availableSupply;
    @SerializedName("total_supply")
    @Expose
    private String totalSupply;
    @SerializedName("percent_change_1h")
    @Expose
    private String percentChange1h;
    @SerializedName("percent_change_24h")
    @Expose
    private String percentChange24h;
    @SerializedName("percent_change_7d")
    @Expose
    private String percentChange7d;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    
    private String logo;

    public String getId () {

        return this.id;
    }

    public void setId (String id) {

        this.id = id;
    }

    public String getName () {

        return this.name;
    }

    public void setName (String name) {

        this.name = name;
    }

    public String getSymbol () {

        return this.symbol;
    }

    public void setSymbol (String symbol) {

        this.symbol = symbol;
    }

    public String getRank () {

        return this.rank;
    }

    public void setRank (String rank) {

        this.rank = rank;
    }

    public String getPriceUsd () {

        return this.priceUsd;
    }

    public void setPriceUsd (String priceUsd) {

        this.priceUsd = priceUsd;
    }

    public String getPriceBtc () {

        return this.priceBtc;
    }

    public void setPriceBtc (String priceBtc) {

        this.priceBtc = priceBtc;
    }

    public String get24hVolumeUsd () {

        return this._24hVolumeUsd;
    }

    public void set24hVolumeUsd (String _24hVolumeUsd) {

        this._24hVolumeUsd = _24hVolumeUsd;
    }

    public String getMarketCapUsd () {

        return this.marketCapUsd;
    }

    public void setMarketCapUsd (String marketCapUsd) {

        this.marketCapUsd = marketCapUsd;
    }

    public String getAvailableSupply () {

        return this.availableSupply;
    }

    public void setAvailableSupply (String availableSupply) {

        this.availableSupply = availableSupply;
    }

    public String getTotalSupply () {

        return this.totalSupply;
    }

    public void setTotalSupply (String totalSupply) {

        this.totalSupply = totalSupply;
    }

    public String getPercentChange1h () {

        return this.percentChange1h;
    }

    public void setPercentChange1h (String percentChange1h) {

        this.percentChange1h = percentChange1h;
    }

    public String getPercentChange24h () {

        return this.percentChange24h;
    }

    public void setPercentChange24h (String percentChange24h) {

        this.percentChange24h = percentChange24h;
    }

    public String getPercentChange7d () {

        return this.percentChange7d;
    }

    public void setPercentChange7d (String percentChange7d) {

        this.percentChange7d = percentChange7d;
    }

    public String getLastUpdated () {

        return this.lastUpdated;
    }

    public void setLastUpdated (String lastUpdated) {

        this.lastUpdated = lastUpdated;
    }

    public static CurrencyData get(String id) {
        
        String jsonString = Utilities.readJson(Utilities.getCurrencyUrl(id));
        
        if (jsonString.startsWith("[")) {
            
            jsonString = jsonString.substring(1, jsonString.length() -1);
        }
        
        CurrencyData data = Main.GSON.fromJson(jsonString, CurrencyData.class);
        data.logo = Utilities.getCurrencyImage(id);
        return data;
    }

    public String getLogo () {
        
        return this.logo;
    }
}