package com.cloutscape.common;

import java.util.Random;

public class AntiBanSystem {
    private final Random random = new Random();

    public int getAdaptiveDelay(int sessionGames) {
        // Base delay + session fatigue + randomness
        int base = 400;
        int fatigue = Math.min(1000, sessionGames * 5);
        int jitter = random.nextInt(200);
        return base + fatigue + jitter;
    }

    public boolean shouldTakeBreak(int sessionGames) {
        // 1% chance to take a break after 50 games
        return sessionGames > 50 && random.nextDouble() > 0.99;
    }

    public String getRandomAd(String baseAd) {
        String[] variations = {"", " - Fast Payouts", " - 24/7", " - Trusted", "!"};
        return baseAd + variations[random.nextInt(variations.length)];
    }
}
