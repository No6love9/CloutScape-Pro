package com.cloutscape.common;

import java.util.*;
import lombok.Builder;
import lombok.Data;

public class GameLogic {
    private final Random random = new Random();

    public enum GameType {
        CRAPS, FLOWER_POKER, DICE_DUEL, BLACKJACK, FIFTY_FIVE, HOT_COLD, DICE_WAR
    }

    @Data @Builder
    public static class GameResult {
        private final boolean win;
        private final String details;
        private final double multiplier;
        private final long timestamp;
        private final String gameId;
    }

    public GameResult play(GameType type, long bet) {
        String gameId = UUID.randomUUID().toString().substring(0, 8);
        switch (type) {
            case CRAPS: return playCraps(gameId);
            case FLOWER_POKER: return playFlowerPoker(gameId);
            case DICE_DUEL: return playDiceDuel(gameId);
            case BLACKJACK: return playBlackjack(gameId);
            case FIFTY_FIVE: return playFiftyFive(gameId);
            case HOT_COLD: return playHotCold(gameId);
            case DICE_WAR: return playDiceWar(gameId);
            default: return GameResult.builder().win(false).details("Unknown").gameId(gameId).build();
        }
    }

    private GameResult playCraps(String id) {
        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;
        int total = d1 + d2;
        boolean win = (total == 7 || total == 9 || total == 11);
        return GameResult.builder()
                .win(win)
                .details(String.format("Roll: %d+%d=%d", d1, d2, total))
                .multiplier(win ? 2.0 : 0)
                .timestamp(System.currentTimeMillis())
                .gameId(id)
                .build();
    }

    private GameResult playFlowerPoker(String id) {
        String[] flowers = {"Red", "Blue", "Yellow", "Purple", "Orange", "Mixed", "Black", "White"};
        List<String> hand = new ArrayList<>();
        for (int i = 0; i < 5; i++) hand.add(flowers[random.nextInt(flowers.length)]);
        
        Map<String, Integer> counts = new HashMap<>();
        for (String f : hand) counts.put(f, counts.getOrDefault(f, 0) + 1);
        
        int maxCount = Collections.max(counts.values());
        boolean win = maxCount >= 2 && random.nextDouble() > 0.45; // 45% win rate for pairs+
        
        return GameResult.builder()
                .win(win)
                .details("Hand: " + hand)
                .multiplier(win ? 2.0 : 0)
                .timestamp(System.currentTimeMillis())
                .gameId(id)
                .build();
    }

    private GameResult playDiceDuel(String id) {
        int p = random.nextInt(100) + 1;
        int h = random.nextInt(100) + 1;
        if (p == h) return playDiceDuel(id);
        boolean win = p > h;
        return GameResult.builder()
                .win(win)
                .details(String.format("P:%d vs H:%d", p, h))
                .multiplier(win ? 2.0 : 0)
                .timestamp(System.currentTimeMillis())
                .gameId(id)
                .build();
    }

    private GameResult playBlackjack(String id) {
        int p = random.nextInt(10) + 15;
        int d = random.nextInt(10) + 16;
        boolean win = p <= 21 && (p > d || d > 21);
        return GameResult.builder()
                .win(win)
                .details(String.format("P:%d vs D:%d", p, d))
                .multiplier(win ? 2.0 : 0)
                .timestamp(System.currentTimeMillis())
                .gameId(id)
                .build();
    }

    private GameResult playFiftyFive(String id) {
        int roll = random.nextInt(100) + 1;
        boolean win = roll > 55;
        return GameResult.builder()
                .win(win)
                .details("Roll: " + roll)
                .multiplier(win ? 2.0 : 0)
                .timestamp(System.currentTimeMillis())
                .gameId(id)
                .build();
    }

    private GameResult playHotCold(String id) {
        boolean win = random.nextDouble() > 0.52; // House edge
        return GameResult.builder()
                .win(win)
                .details("Result: " + (win ? "Correct" : "Incorrect"))
                .multiplier(win ? 2.0 : 0)
                .timestamp(System.currentTimeMillis())
                .gameId(id)
                .build();
    }

    private GameResult playDiceWar(String id) {
        int p = (random.nextInt(6)+1) + (random.nextInt(6)+1);
        int h = (random.nextInt(6)+1) + (random.nextInt(6)+1);
        boolean win = p > h;
        return GameResult.builder()
                .win(win)
                .details(String.format("P:%d vs H:%d", p, h))
                .multiplier(win ? 2.0 : 0)
                .timestamp(System.currentTimeMillis())
                .gameId(id)
                .build();
    }
}
