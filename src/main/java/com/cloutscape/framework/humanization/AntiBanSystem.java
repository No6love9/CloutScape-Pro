package com.cloutscape.framework.humanization;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Logger;
import java.awt.Point;

public class AntiBanSystem {
    private long lastBreakTime = System.currentTimeMillis();
    private final long breakInterval = Calculations.random(45, 90) * 60000L;
    private boolean onBreak = false;

    public boolean shouldTakeBreak() {
        return System.currentTimeMillis() - lastBreakTime > breakInterval;
    }

    public boolean isOnBreak() { return onBreak; }

    public void executeBreak() {
        onBreak = true;
        long duration = Calculations.random(5, 15) * 60000L;
        Logger.log("Clout♧Scape: Taking a human-like break for " + (duration / 60000) + " minutes.");
        Mouse.moveOutsideScreen();
        Sleep.sleep(duration);
        lastBreakTime = System.currentTimeMillis();
        onBreak = false;
    }

    public void performPassiveActions() {
        int roll = Calculations.random(1, 500);
        if (roll == 1) {
            Camera.rotateTo(Calculations.random(0, 2048), Calculations.random(128, 383));
        } else if (roll == 2) {
            Mouse.move(new Point(Calculations.random(0, 765), Calculations.random(0, 503)));
        }
    }
}
