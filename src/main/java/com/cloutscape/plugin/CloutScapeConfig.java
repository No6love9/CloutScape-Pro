package com.cloutscape.plugin;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("cloutscape")
public interface CloutScapeConfig extends Config {
    @ConfigItem(
        keyName = "webhookUrl",
        name = "Discord Webhook URL",
        description = "The URL for Discord notifications",
        position = 1
    )
    default String webhookUrl() {
        return "";
    }

    @ConfigItem(
        keyName = "adMessages",
        name = "Advertising Messages",
        description = "Messages to broadcast",
        position = 2
    )
    default String adMessages() {
        return "Clout♧Scape | Best Odds | !help";
    }

    @ConfigItem(
        keyName = "minBet",
        name = "Minimum Bet",
        description = "Minimum bet amount in GP",
        position = 3
    )
    default int minBet() {
        return 100000;
    }

    @ConfigItem(
        keyName = "maxBet",
        name = "Maximum Bet",
        description = "Maximum bet amount in GP",
        position = 4
    )
    default int maxBet() {
        return 100000000;
    }

    @ConfigItem(
        keyName = "jagexAccount",
        name = "Jagex Account Email",
        description = "Email for Jagex Account login",
        position = 5
    )
    default String jagexAccount() {
        return "";
    }

    @ConfigItem(
        keyName = "adaptiveMode",
        name = "Adaptive Logic",
        description = "Automatically adjust behavior based on player activity",
        position = 6
    )
    default boolean adaptiveMode() {
        return true;
    }
}
