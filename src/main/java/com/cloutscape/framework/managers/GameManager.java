package com.cloutscape.framework.managers;

import com.cloutscape.framework.core.CloutScape;
import com.cloutscape.framework.games.impl.CrapsGame;

public class GameManager {
    private final CloutScape script;
    private final CrapsGame craps;

    public GameManager(CloutScape script) {
        this.script = script;
        this.craps = new CrapsGame();
    }

    public void processActiveGames() {
        // Example: If a game is triggered (this would be called by TradeManager)
    }

    public void runCraps(String player, long bet) {
        CrapsGame.CrapsResult result = craps.play();
        script.getProfitTracker().addProfit(result.win ? -bet * 2 : bet); // Simplified profit logic
        
        if (script.getWebhook() != null) {
            script.getWebhook().sendEmbed("Game Result: Craps", 
                "Player: " + player + "\nResult: " + (result.win ? "WIN" : "LOSS"), 
                result.win ? 65280 : 16711680,
                "Bet", String.valueOf(bet),
                "Roll", result.message);
        }
    }
}
