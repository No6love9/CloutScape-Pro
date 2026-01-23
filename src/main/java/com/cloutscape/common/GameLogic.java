package com.cloutscape.common;

import java.util.Random;
import java.util.Arrays;

public class GameLogic {
    private final Random random = new Random();

    public enum GameType {
        CRAPS, FLOWER_POKER, DICE_DUEL, BLACKJACK, FIFTY_FIVE, HOT_COLD, DICE_WAR
    }

    public static class GameResult {
        public final boolean win;
        public final String details;
        public final double multiplier;

        public GameResult(boolean win, String details, double multiplier) {
            this.win = win;
            this.details = details;
            this.multiplier = multiplier;
        }
    }

    public GameResult play(GameType type, long bet) {
        switch (type) {
            case CRAPS:
                return playCraps();
            case FLOWER_POKER:
                return playFlowerPoker();
            case DICE_DUEL:
                return playDiceDuel();
            case BLACKJACK:
                return playBlackjack();
            case FIFTY_FIVE:
                return playFiftyFive();
            case HOT_COLD:
                return playHotCold();
            case DICE_WAR:
                return playDiceWar();
            default:
                return new GameResult(false, "Unknown Game", 0);
        }
    }

    private GameResult playCraps() {
        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;
        int total = d1 + d2;
        // Official OSRS Casino Craps: 7, 9, 11 are wins
        boolean win = (total == 7 || total == 9 || total == 11);
        return new GameResult(win, "Rolled: " + d1 + "+" + d2 + "=" + total, win ? 2.0 : 0);
    }

    private GameResult playFlowerPoker() {
        String[] flowers = {"Red", "Blue", "Yellow", "Purple", "Orange", "Mixed", "Black", "White"};
        String[] hand = new String[5];
        for (int i = 0; i < 5; i++) hand[i] = flowers[random.nextInt(flowers.length)];
        
        // Simplified win logic: if there's at least a pair
        Arrays.sort(hand);
        boolean hasPair = false;
        for (int i = 0; i < 4; i++) {
            if (hand[i].equals(hand[i+1])) {
                hasPair = true;
                break;
            }
        }
        
        boolean win = hasPair && random.nextInt(100) > 40; // House edge
        return new GameResult(win, "Hand: " + Arrays.toString(hand), win ? 2.0 : 0);
    }

    private GameResult playDiceDuel() {
        int player = random.nextInt(100) + 1;
        int host = random.nextInt(100) + 1;
        if (player == host) return playDiceDuel(); // Reroll on tie
        boolean win = player > host;
        return new GameResult(win, "P: " + player + " vs H: " + host, win ? 2.0 : 0);
    }

    private GameResult playBlackjack() {
        int player = random.nextInt(10) + 15; // 15-24
        int dealer = random.nextInt(10) + 15;
        boolean win = player <= 21 && (player > dealer || dealer > 21);
        return new GameResult(win, "P: " + player + " vs D: " + dealer, win ? 2.0 : 0);
    }

    private GameResult playFiftyFive() {
        int roll = random.nextInt(100) + 1;
        boolean win = roll > 55;
        return new GameResult(win, "Rolled: " + roll, win ? 2.0 : 0);
    }

    private GameResult playHotCold() {
        String[] options = {"Hot", "Cold"};
        String result = options[random.nextInt(2)];
        boolean win = random.nextBoolean(); // 50/50
        return new GameResult(win, "Result: " + result, win ? 2.0 : 0);
    }

    private GameResult playDiceWar() {
        int p1 = random.nextInt(6) + 1;
        int p2 = random.nextInt(6) + 1;
        int h1 = random.nextInt(6) + 1;
        int h2 = random.nextInt(6) + 1;
        int pSum = p1 + p2;
        int hSum = h1 + h2;
        boolean win = pSum > hSum;
        return new GameResult(win, "P: (" + p1 + "+" + p2 + ")=" + pSum + " vs H: (" + h1 + "+" + h2 + ")=" + hSum, win ? 2.0 : 0);
    }
}
