package com.cloutscape.framework.core;

import com.cloutscape.framework.gui.CloutGUI;
import com.cloutscape.framework.managers.*;
import com.cloutscape.framework.humanization.AntiBanSystem;
import com.cloutscape.framework.utils.DiscordWebhook;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.Client;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.utilities.Timer;

import javax.swing.*;
import java.awt.*;

@ScriptManifest(
    name = "Clout♧Scape Casino Pro",
    author = "ikingsnipe",
    version = 1.1,
    description = "The definitive 2026 OSRS Casino Framework. Advanced anti-ban, multi-game support, and professional GUI.",
    category = Category.MISC
)
public class CloutScape extends AbstractScript implements ChatListener {

    private CloutGUI gui;
    private GameManager gameManager;
    private TradeManager tradeManager;
    private AntiBanSystem antiBan;
    private ProfitTracker profitTracker;
    private Timer runtime;
    private DiscordWebhook webhook;

    @Override
    public void onStart() {
        log("Initializing Clout♧Scape Framework...");
        runtime = new Timer();
        profitTracker = new ProfitTracker();
        antiBan = new AntiBanSystem();
        gameManager = new GameManager(this);
        tradeManager = new TradeManager(this, gameManager);

        SwingUtilities.invokeLater(() -> {
            gui = new CloutGUI(this);
            gui.setVisible(true);
        });
        
        log("Clout♧Scape is ready.");
    }

    public void initializeWebhook(String url) {
        this.webhook = new DiscordWebhook(url);
        this.webhook.sendEmbed("Bot Started", "Clout♧Scape is now online.", 65280, 
            "User", Players.getLocal().getName(),
            "World", String.valueOf(Client.getCurrentWorld()));
    }

    @Override
    public int onLoop() {
        if (antiBan.shouldTakeBreak()) {
            if (webhook != null) webhook.sendEmbed("Anti-Ban", "Taking a micro-break.", 16753920);
            antiBan.executeBreak();
            return 1000;
        }

        antiBan.performPassiveActions();
        tradeManager.handlePendingTrades();
        gameManager.processActiveGames();

        return 600;
    }

    @Override
    public void onMessage(Message msg) {
        tradeManager.onChatMessage(msg);
    }

    @Override
    public void onPaint(Graphics2D g) {
        drawOverlay(g);
    }

    private void drawOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(10, 30, 220, 140, 15, 15);
        g.setColor(new Color(0, 255, 127));
        g.drawRoundRect(10, 30, 220, 140, 15, 15);

        g.setFont(new Font("Verdana", Font.BOLD, 14));
        g.drawString("Clout♧Scape Pro", 25, 55);
        
        g.setFont(new Font("Verdana", Font.PLAIN, 12));
        g.setColor(Color.WHITE);
        g.drawString("Runtime: " + runtime.formatTime(), 25, 80);
        g.drawString("Profit: " + profitTracker.getFormattedProfit() + " GP", 25, 100);
        g.drawString("Games: " + profitTracker.getTotalGames(), 25, 120);
        g.drawString("Status: " + tradeManager.getStatus(), 25, 140);
    }

    public DiscordWebhook getWebhook() { return webhook; }
    public ProfitTracker getProfitTracker() { return profitTracker; }

    @Override
    public void onExit() {
        if (webhook != null) webhook.sendEmbed("Bot Stopped", "Clout♧Scape has shut down.", 16711680,
            "Total Profit", profitTracker.getFormattedProfit(),
            "Total Games", String.valueOf(profitTracker.getTotalGames()));
        if (gui != null) gui.dispose();
        log("Clout♧Scape Shutting Down.");
    }
}
