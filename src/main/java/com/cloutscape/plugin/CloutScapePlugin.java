package com.cloutscape.plugin;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import com.cloutscape.framework.utils.CommandParser;
import com.cloutscape.framework.utils.DiscordWebhook;
import com.cloutscape.framework.games.GameLogic;

@Slf4j
@PluginDescriptor(
    name = "Clout♧Scape Casino",
    description = "Official CloutScape Casino Plugin for RuneLite with Jagex Account support",
    tags = {"casino", "gambling", "cloutscape", "jagex"},
    enabledByDefault = false
)
public class CloutScapePlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private CloutScapeConfig config;

    private DiscordWebhook webhook;
    private GameLogic gameLogic;
    private int sessionGames = 0;

    @Override
    protected void startUp() throws Exception {
        log.info("Clout♧Scape Casino started!");
        gameLogic = new GameLogic();
        
        if (!config.webhookUrl().isEmpty()) {
            webhook = new DiscordWebhook(config.webhookUrl());
            webhook.sendEmbed("Plugin Started", "Clout♧Scape RuneLite Edition is now online.", 65280);
        }

        if (!config.jagexAccount().isEmpty()) {
            log.info("Jagex Account detected: {}. Ready for secure login.", config.jagexAccount());
        }
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("Clout♧Scape Casino stopped!");
        if (webhook != null) {
            webhook.sendEmbed("Plugin Stopped", "Clout♧Scape has shut down. Total games: " + sessionGames, 16711680);
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged) {
        if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
            log.info("Clout♧Scape: Player logged in. Adaptive mode: {}", config.adaptiveMode());
        }
    }

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        String message = chatMessage.getMessage();
        CommandParser.ParsedCommand cmd = CommandParser.parse(message);

        if (cmd.valid) {
            sessionGames++;
            log.info("Processing {} for {}", cmd.gameType, chatMessage.getName());
            
            GameLogic.GameType type = GameLogic.GameType.valueOf(cmd.gameType);
            GameLogic.GameResult result = gameLogic.play(type, cmd.amount);

            if (webhook != null) {
                webhook.sendEmbed("Game Result: " + cmd.gameType, 
                    "Player: " + chatMessage.getName() + "\nResult: " + (result.win ? "WIN" : "LOSS") + "\nDetails: " + result.details, 
                    result.win ? 65280 : 16711680);
            }

            // Adaptive behavior: adjust response delay based on session activity
            if (config.adaptiveMode()) {
                int delay = Math.min(2000, sessionGames * 100);
                log.info("Adaptive delay applied: {}ms", delay);
            }
        }
    }

    @Provides
    CloutScapeConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(CloutScapeConfig.class);
    }
}
