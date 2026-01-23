package com.cloutscape.framework.managers;

import com.cloutscape.framework.games.impl.CrapsGame;
import com.cloutscape.framework.utils.DiscordWebhook;

public class GameManager {
    private final CrapsGame craps;
    private final ProfitTracker profitTracker;
    private final DiscordWebhook webhook;

    public GameManager(ProfitTracker profitTracker, DiscordWebhook webhook) {
        this.craps = new CrapsGame();
        this.profitTracker = profitTracker;
        this.webhook = webhook;
    }

    public void runCraps(String player, long bet) {
        CrapsGame.CrapsResult result = craps.play();
        profitTracker.addProfit(result.win ? -bet * 2 : bet); 
        
        if (webhook != null) {
            webhook.sendEmbed("Game Result: Craps", 
                "Player: " + player + "\nResult: " + (result.win ? "WIN" : "LOSS"), 
                result.win ? 65280 : 16711680,
                "Bet", String.valueOf(bet),
                "Roll", result.message);
        }
    }
}
