package com.cloutscape.framework.utils;

import org.dreambot.api.methods.Calculations;
import java.util.HashMap;
import java.util.Map;

public class ChatAI {
    private static final Map<String, String[]> RESPONSES = new HashMap<>();

    static {
        RESPONSES.put("help", new String[]{
            "Clout♧Scape: Use !c [amt] for Craps, !fp [amt] for Flower Poker, or !bj [amt] for Blackjack!",
            "Clout♧Scape: Commands: !craps, !flower, !dice, !blackjack, !55, !hc, !war. Shorthand works too!"
        });
        RESPONSES.put("odds", new String[]{
            "Clout♧Scape: We offer the best 2026 official odds. !help for game list.",
            "Clout♧Scape: All games are Provably Fair. Check our Discord for logic details!"
        });
        RESPONSES.put("hello", new String[]{
            "Clout♧Scape: Welcome! Ready to play? Use !help for commands.",
            "Clout♧Scape: Hey there! Good luck today."
        });
    }

    public static String getResponse(String message) {
        message = message.toLowerCase();
        for (String key : RESPONSES.keySet()) {
            if (message.contains(key)) {
                String[] options = RESPONSES.get(key);
                return options[Calculations.random(options.length)];
            }
        }
        return null;
    }
}
