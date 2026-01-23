package com.cloutscape.framework.games.impl;

import java.util.Random;

public class CrapsGame {
    private final Random random = new Random();

    public static class CrapsResult {
        public final int d1, d2, total;
        public final boolean win;
        public final String message;

        public CrapsResult(int d1, int d2, boolean win, String message) {
            this.d1 = d1;
            this.d2 = d2;
            this.total = d1 + d2;
            this.win = win;
            this.message = message;
        }
    }

    public CrapsResult play() {
        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;
        int total = d1 + d2;

        // Official OSRS Casino Craps Logic: 7, 11, 12 are often wins in specific variants
        // Standard "Chasing Craps" variant: 7, 9, 12 win x3
        boolean win = (total == 7 || total == 9 || total == 12);
        String msg = "Rolled " + d1 + " + " + d2 + " = " + total + (win ? " (WIN!)" : " (LOSS)");
        
        return new CrapsResult(d1, d2, win, msg);
    }
}
