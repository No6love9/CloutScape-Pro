package com.cloutscape.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    
    public static class ParsedCommand {
        public final String gameType;
        public final long amount;
        public final boolean valid;

        public ParsedCommand(String gameType, long amount, boolean valid) {
            this.gameType = gameType;
            this.amount = amount;
            this.valid = valid;
        }
    }

    private static final Pattern COMMAND_PATTERN = Pattern.compile("^!([a-zA-Z]+)\\s*(\\d+\\.?\\d*[kKmMbB]?)$");

    public static ParsedCommand parse(String message) {
        message = message.trim().toLowerCase();
        Matcher matcher = COMMAND_PATTERN.matcher(message);
        
        if (matcher.find()) {
            String cmd = matcher.group(1);
            String amtStr = matcher.group(2);
            
            String gameType = resolveGameType(cmd);
            long amount = parseAmount(amtStr);
            
            if (gameType != null && amount > 0) {
                return new ParsedCommand(gameType, amount, true);
            }
        }
        return new ParsedCommand(null, 0, false);
    }

    private static String resolveGameType(String cmd) {
        switch (cmd) {
            case "c": case "craps": return "CRAPS";
            case "f": case "fp": case "flower": return "FLOWER_POKER";
            case "d": case "dice": return "DICE_DUEL";
            case "bj": case "blackjack": return "BLACKJACK";
            case "55": case "55x2": return "FIFTY_FIVE";
            case "hc": case "hotcold": return "HOT_COLD";
            case "dw": case "war": return "DICE_WAR";
            default: return null;
        }
    }

    private static long parseAmount(String s) {
        try {
            double multiplier = 1;
            if (s.endsWith("k")) multiplier = 1_000;
            else if (s.endsWith("m")) multiplier = 1_000_000;
            else if (s.endsWith("b")) multiplier = 1_000_000_000;
            
            String numericPart = s.replaceAll("[kKmMbB]", "");
            return (long) (Double.parseDouble(numericPart) * multiplier);
        } catch (Exception e) {
            return 0;
        }
    }
}
