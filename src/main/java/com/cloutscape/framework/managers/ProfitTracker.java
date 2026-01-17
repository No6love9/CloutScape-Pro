package com.cloutscape.framework.managers;

public class ProfitTracker {
    private long totalProfit = 0;
    private int totalGames = 0;

    public String getFormattedProfit() {
        if (totalProfit >= 1000000) return (totalProfit / 1000000) + "M";
        if (totalProfit >= 1000) return (totalProfit / 1000) + "K";
        return String.valueOf(totalProfit);
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void addProfit(long amount) {
        this.totalProfit += amount;
        this.totalGames++;
    }

    public String formatValue(long value) {
        if (value >= 1_000_000_000) return String.format("%.1fB", value / 1_000_000_000.0);
        if (value >= 1_000_000) return String.format("%.1fM", value / 1_000_000.0);
        if (value >= 1_000) return String.format("%.1fK", value / 1_000.0);
        return String.valueOf(value);
    }
}
