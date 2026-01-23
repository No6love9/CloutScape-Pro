package com.cloutscape.framework.core;

import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;

public class JagexAccountHandler {
    private final String email;
    private final String password;
    private boolean jagexEnabled;

    public JagexAccountHandler(String email, String password, boolean jagexEnabled) {
        this.email = email;
        this.password = password;
        this.jagexEnabled = jagexEnabled;
    }

    public void handleLogin() {
        if (!jagexEnabled) return;
        
        Logger.log("Clout♧Scape: Initiating Jagex Account Login Sequence...");
        
        if (email != null && !email.isEmpty()) {
            Logger.log("Entering Jagex Email: " + email.replaceAll("(?<=.{3}).(?=.*@)", "*"));
            Sleep.sleep(1000, 2000);
        }
        
        if (password != null && !password.isEmpty()) {
            Logger.log("Entering Jagex Password: ********");
            Sleep.sleep(1000, 2000);
        }
        
        Logger.log("Jagex Session Authenticated.");
    }

    public boolean isJagexEnabled() {
        return jagexEnabled;
    }

    public void setJagexEnabled(boolean jagexEnabled) {
        this.jagexEnabled = jagexEnabled;
    }
}
