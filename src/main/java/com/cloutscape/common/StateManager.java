package com.cloutscape.common;

import lombok.Getter;
import lombok.Setter;

public class StateManager {
    public enum State {
        INITIALIZING,
        IDLE_ADVERTISING,
        WAITING_FOR_TRADE,
        VERIFYING_TRADE,
        PROCESSING_GAME,
        PAYING_OUT,
        ERROR_HANDLING,
        TAKING_BREAK
    }

    @Getter @Setter
    private State currentState = State.INITIALIZING;

    @Getter
    private long stateStartTime;

    public void transitionTo(State newState) {
        System.out.println("[StateManager] Transitioning: " + currentState + " -> " + newState);
        this.currentState = newState;
        this.stateStartTime = System.currentTimeMillis();
    }

    public long getTimeInState() {
        return System.currentTimeMillis() - stateStartTime;
    }
}
