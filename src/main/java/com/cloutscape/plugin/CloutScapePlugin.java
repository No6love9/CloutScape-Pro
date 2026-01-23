package com.cloutscape.plugin;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import com.cloutscape.framework.utils.CommandParser;
import com.cloutscape.framework.utils.DiscordWebhook;

@Slf4j
@PluginDescriptor(
    name = "Clout♧Scape Casino",
    description = "Official CloutScape Casino Plugin for RuneLite",
    tags = {"casino", "gambling", "cloutscape"},
    enabledByDefault = false
)
public class CloutScapePlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private CloutScapeConfig config;

    private DiscordWebhook webhook;

    @Override
    protected void startUp() throws Exception {
        log.info("Clout♧Scape Casino started!");
        if (!config.webhookUrl().isEmpty()) {
            webhook = new DiscordWebhook(config.webhookUrl());
            webhook.sendEmbed("Plugin Started", "Clout♧Scape RuneLite Edition is now online.", 65280);
        }
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("Clout♧Scape Casino stopped!");
        if (webhook != null) {
            webhook.sendEmbed("Plugin Stopped", "Clout♧Scape has shut down.", 16711680);
        }
    }

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        String message = chatMessage.getMessage();
        CommandParser.ParsedCommand cmd = CommandParser.parse(message);

        if (cmd.valid) {
            log.info("Received command: {} with amount: {}", cmd.gameType, cmd.amount);
            // In a real plugin, you would handle the game logic here
            // For now, we just log it and send a webhook if configured
            if (webhook != null) {
                webhook.sendEmbed("Game Command", 
                    "Player: " + chatMessage.getName() + "\nGame: " + cmd.gameType + "\nBet: " + cmd.amount, 
                    16776960);
            }
        }
    }

    @Provides
    CloutScapeConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(CloutScapeConfig.class);
    }
}
