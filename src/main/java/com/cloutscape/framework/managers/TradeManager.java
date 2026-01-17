package com.cloutscape.framework.managers;

import com.cloutscape.framework.core.CloutScape;
import org.dreambot.api.wrappers.widgets.message.Message;

public class TradeManager {
    private final CloutScape script;
    private final GameManager gameManager;
    private String status = "Idle";

    public TradeManager(CloutScape script, GameManager gameManager) {
        this.script = script;
        this.gameManager = gameManager;
    }

    public void handlePendingTrades() {
        // Logic to handle trades
    }

    public void onChatMessage(Message msg) {
        // Logic for chat commands
    }

    public String getStatus() {
        return status;
    }
}
