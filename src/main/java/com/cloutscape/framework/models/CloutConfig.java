package com.cloutscape.framework.models;

import java.util.HashSet;
import java.util.Set;

public class CloutConfig {
    public String webhookUrl = "";
    public String adMessages = "Clout♧Scape | Best Odds | !help";
    public long minBet = 100000;
    public long maxBet = 100000000;
    public long muleThreshold = 500000000;
    
    public Set<String> enabledGames = new HashSet<>();
    
    public boolean antiBanEnabled = true;
    public boolean microBreaksEnabled = true;
    
    public boolean jagexEnabled = false;
    public String jagexEmail = "";
    public String jagexPassword = "";
    
    public void updateFromGui(String webhook, String ads, String min, String max, String mule) {
        this.webhookUrl = webhook;
        this.adMessages = ads;
        try {
            this.minBet = parseAmount(min);
            this.maxBet = parseAmount(max);
            this.muleThreshold = parseAmount(mule);
        } catch (Exception ignored) {}
    }
    
    private long parseAmount(String s) {
        s = s.toLowerCase().replace(" ", "");
        if (s.endsWith("k")) return (long) (Double.parseDouble(s.replace("k", "")) * 1000);
        if (s.endsWith("m")) return (long) (Double.parseDouble(s.replace("m", "")) * 1000000);
        if (s.endsWith("b")) return (long) (Double.parseDouble(s.replace("b", "")) * 1000000000);
        return Long.parseLong(s);
    }
}
