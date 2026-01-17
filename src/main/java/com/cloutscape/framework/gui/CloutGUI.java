package com.cloutscape.framework.gui;

import com.cloutscape.framework.core.CloutScape;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CloutGUI extends JFrame {
    private final CloutScape script;

    public CloutGUI(CloutScape script) {
        this.script = script;
        setTitle("Clout♧Scape | Enterprise Casino Framework");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Custom Dark Theme
        UIManager.put("Panel.background", new Color(30, 30, 30));
        UIManager.put("Label.foreground", Color.WHITE);
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", createDashboard());
        tabs.addTab("Games", createGamesPanel());
        tabs.addTab("Anti-Ban", createAntiBanPanel());
        tabs.addTab("Advertising", createAdPanel());
        tabs.addTab("Settings", createSettingsPanel());

        add(tabs, BorderLayout.CENTER);
        
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton startBtn = new JButton("Apply & Start");
        startBtn.setBackground(new Color(0, 150, 0));
        startBtn.setForeground(Color.WHITE);
        footer.add(startBtn);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createDashboard() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.add(createStatCard("Total Profit", "0 GP"));
        panel.add(createStatCard("Win Rate", "0%"));
        panel.add(createStatCard("Active Players", "0"));
        panel.add(createStatCard("Session Time", "00:00:00"));
        return panel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(45, 45, 45));
        card.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        JLabel v = new JLabel(value, SwingConstants.CENTER);
        v.setFont(new Font("Arial", Font.BOLD, 18));
        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);
        return card;
    }

    private JPanel createGamesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] games = {"Craps (Official)", "Flower Poker (Escalating)", "Dice Duel", "Blackjack", "55x2", "Hot/Cold", "Dice War"};
        for (String game : games) {
            JCheckBox cb = new JCheckBox(game, true);
            cb.setForeground(Color.WHITE);
            panel.add(cb);
        }
        return panel;
    }

    private JPanel createAntiBanPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Humanization Settings"));
        panel.add(new JCheckBox("Dynamic Micro-Breaks", true));
        panel.add(new JCheckBox("Camera Jitter & Fatigue", true));
        panel.add(new JCheckBox("Randomized Click Offsets", true));
        panel.add(new JCheckBox("Idle Chat Interaction", true));
        return panel;
    }

    private JPanel createAdPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea adArea = new JTextArea("Clout♧Scape | Best Odds | Fast Payouts | !help");
        panel.add(new JLabel("Advertising Messages:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(adArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Min Bet:"));
        panel.add(new JTextField("100k"));
        panel.add(new JLabel("Max Bet:"));
        panel.add(new JTextField("100m"));
        panel.add(new JLabel("Mule Threshold:"));
        panel.add(new JTextField("500m"));
        return panel;
    }
}
