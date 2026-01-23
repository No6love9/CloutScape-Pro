package com.cloutscape.common;

import lombok.Builder;
import lombok.Data;

public class TradeVerifier {
    
    @Data @Builder
    public static class TradeValidation {
        private final boolean valid;
        private final long amount;
        private final String reason;
    }

    public static TradeValidation validate(long offeredAmount, long minBet, long maxBet) {
        if (offeredAmount < minBet) {
            return TradeValidation.builder()
                    .valid(false)
                    .amount(offeredAmount)
                    .reason("Bet below minimum: " + minBet)
                    .build();
        }
        if (offeredAmount > maxBet) {
            return TradeValidation.builder()
                    .valid(false)
                    .amount(offeredAmount)
                    .reason("Bet above maximum: " + maxBet)
                    .build();
        }
        return TradeValidation.builder()
                .valid(true)
                .amount(offeredAmount)
                .reason("Valid bet")
                .build();
    }
}
