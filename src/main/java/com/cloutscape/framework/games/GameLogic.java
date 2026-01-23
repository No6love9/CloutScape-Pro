package com.cloutscape.framework.games;

import java.util.Random;

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
        int roll = random.nextInt(12) + 1;
        boolean win = (roll == 7 || roll == 9 || roll == 11);
        return new GameResult(win, "Rolled: " + roll, win ? 2.0 : 0);
    }

    private GameResult playFlowerPoker() {
        // Simplified hand evaluation logic
        int handStrength = random.nextInt(100);
        boolean win = handStrength > 60;
        String hand = win ? "Full House" : "Two Pair";
        return new GameResult(win, "Hand: " + hand, win ? 2.0 : 0);
    }

    private GameResult playDiceDuel() {
        int player = random.nextInt(100) + 1;
        int host = random.nextInt(100) + 1;
        boolean win = player > host;
        return new GameResult(win, "P: " + player + " vs H: " + host, win ? 2.0 : 0);
    }

    private GameResult playBlackjack() {
        int player = random.nextInt(10) + 12; // 12-21
        int dealer = random.nextInt(10) + 12;
        boolean win = player <= 21 && (player > dealer || dealer > 21);
        return new GameResult(win, "P: " + player + " vs D: " + dealer, win ? 2.0 : 0);
    }

    private GameResult playFiftyFive() {
        int roll = random.nextInt(100) + 1;
        boolean win = roll > 55;
        return new GameResult(win, "Rolled: " + roll, win ? 2.0 : 0);
    }

    private GameResult playHotCold() {
        boolean isHot = random.nextBoolean();
        boolean choice = random.nextBoolean();
        boolean win = isHot == choice;
        return new GameResult(win, "Result: " + (isHot ? "Hot" : "Cold"), win ? 2.0 : 0);
    }

    private GameResult playDiceWar() {
        int pSum = (random.nextInt(6)+1) + (random.nextInt(6)+1);
        int hSum = (random.nextInt(6)+1) + (random.nextInt(6)+1);
        boolean win = pSum > hSum;
        return new GameResult(win, "P: " + pSum + " vs H: " + hSum, win ? 2.0 : 0);
    }
}
