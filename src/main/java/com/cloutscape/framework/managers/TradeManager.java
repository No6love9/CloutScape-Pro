package com.cloutscape.framework.managers;

import com.cloutscape.framework.core.CloutScape;
import com.cloutscape.framework.core.BotState;
import com.cloutscape.framework.utils.CommandParser;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.trade.TradeUser;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;

import java.util.LinkedList;
import java.util.Queue;

public class TradeManager {
    private final CloutScape script;
    private final GameManager gameManager;
    private String status = "Idle";
    private final Queue<String> tradeQueue = new LinkedList<>();
    
    private String currentTrader = null;
    private long expectedBet = 0;
    private String expectedGame = null;

    public TradeManager(CloutScape script, GameManager gameManager) {
        this.script = script;
        this.gameManager = gameManager;
    }

    public void handlePendingTrades() {
        if (Trade.isOpen()) return;

        Player trader = Players.all(p -> p != null && p.getCharacterInteractingWithMe() != null && p.getCharacterInteractingWithMe().equals(Players.getLocal())).stream().findFirst().orElse(null);
        if (trader != null && !tradeQueue.contains(trader.getName())) {
            if (currentTrader == null) {
                Logger.log("Accepting trade from: " + trader.getName());
                Trade.tradeWithPlayer(trader);
                Sleep.sleepUntil(Trade::isOpen, 5000);
            }
        }
    }

    public void processTrade() {
        if (!Trade.isOpen()) {
            currentTrader = null;
            script.setState(BotState.IDLE_ADVERTISING);
            return;
        }

        if (currentTrader == null) {
            currentTrader = Trade.getTradingWith();
        }

        // Safety Notification Logic
        if (Trade.contains(true, "Coins")) {
            long offered = Trade.getValue(true);
            if (offered == expectedBet) {
                Keyboard.type("Clout♧Scape: Safe to accept! Player: " + currentTrader + " | Bet: " + script.getProfitTracker().formatValue(offered) + " | Game: " + expectedGame);
            } else {
                Keyboard.type("Clout♧Scape: Warning! Offered " + script.getProfitTracker().formatValue(offered) + " but expected " + script.getProfitTracker().formatValue(expectedBet));
            }
        }

        if (Trade.hasAcceptedTrade(TradeUser.US) && Trade.hasAcceptedTrade(TradeUser.THEM)) {
            script.setState(BotState.PROCESSING_GAME);
        }
    }

    public void onChatMessage(Message msg) {
        if (msg.getUsername().equals(Players.getLocal().getName())) return;

        String text = msg.getMessage();
        CommandParser.ParsedCommand cmd = CommandParser.parse(text);
        
        if (cmd.valid) {
            this.expectedBet = cmd.amount;
            this.expectedGame = cmd.gameType;
            Logger.log("Queued game: " + expectedGame + " for " + cmd.amount + " from " + msg.getUsername());
            
            if (script.getWebhook() != null) {
                script.getWebhook().sendEmbed("Game Queued", "Player: " + msg.getUsername() + "\nGame: " + expectedGame + "\nBet: " + cmd.amount, 16776960);
            }
            
            Keyboard.type("Clout♧Scape: Game set to " + expectedGame + " for " + script.getProfitTracker().formatValue(expectedBet) + ". Trade me to start!");
        } else {
            String aiResponse = com.cloutscape.framework.utils.ChatAI.getResponse(text);
            if (aiResponse != null) {
                Keyboard.type(aiResponse);
            }
        }
    }

    public void handlePayouts() {
        // Payout logic
    }

    public String getStatus() {
        return status;
    }
}
