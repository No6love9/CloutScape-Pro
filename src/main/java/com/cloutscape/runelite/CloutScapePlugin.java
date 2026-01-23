package com.cloutscape.runelite;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import com.cloutscape.common.*;

@Slf4j
@PluginDescriptor(
    name = "Clout♧Scape Enterprise",
    description = "Full Robust Casino System for RuneLite",
    tags = {"casino", "enterprise", "cloutscape"}
)
public class CloutScapePlugin extends Plugin {
    @Inject private Client client;
    @Inject private CloutScapeConfig config;

    private final StateManager stateManager = new StateManager();
    private final GameLogic gameLogic = new GameLogic();
    private DiscordWebhook webhook;

    @Override
    protected void startUp() throws Exception {
        log.info("Clout♧Scape Enterprise Started.");
        stateManager.transitionTo(StateManager.State.IDLE_ADVERTISING);
        if (!config.webhookUrl().isEmpty()) {
            webhook = new DiscordWebhook(config.webhookUrl());
        }
    }

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        CommandParser.ParsedCommand cmd = CommandParser.parse(chatMessage.getMessage());
        if (cmd.isValid()) {
            log.info("Processing Enterprise Game: {}", cmd.getGameType());
            GameLogic.GameType type = GameLogic.GameType.valueOf(cmd.getGameType());
            GameLogic.GameResult result = gameLogic.play(type, cmd.getAmount());
            
            if (webhook != null) {
                webhook.sendEmbed("Enterprise Game Result", 
                    "Player: " + chatMessage.getName() + "\nResult: " + (result.isWin() ? "WIN" : "LOSS") + "\nDetails: " + result.getDetails(), 
                    result.isWin() ? 65280 : 16711680);
            }
        }
    }

    @Provides
    CloutScapeConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(CloutScapeConfig.class);
    }
}
