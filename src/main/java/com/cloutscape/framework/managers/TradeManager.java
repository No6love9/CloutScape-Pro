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
        // Logic to detect trades and switch state
    }

    public void processTrade() {
        // Logic for active trade window
    }

    public void handlePayouts() {
        // Logic for paying out winners
    }

    public void onChatMessage(Message msg) {
        // Logic for chat commands
    }

    public String getStatus() {
        return status;
    }
}
