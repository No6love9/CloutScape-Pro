package com.cloutscape.framework.gui;

import com.cloutscape.framework.core.CloutScape;
import javax.swing.*;
import java.awt.*;

public class CloutGUI extends JFrame {
    private final CloutScape script;
    private JTextField webhookField, minBetField, maxBetField, jagexEmailField;
    private JPasswordField jagexPasswordField;
    private JTextArea adArea;
    private JCheckBox jagexEnabledBox;

    public CloutGUI(CloutScape script) {
        this.script = script;
        setTitle("Clout♧Scape Pro v2.0 - RuneLite Edition");
        setSize(500, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("General", createGeneralPanel());
        tabs.addTab("Games", createGamesPanel());
        tabs.addTab("Jagex Account", createJagexPanel());
        tabs.addTab("Discord", createDiscordPanel());

        add(tabs, BorderLayout.CENTER);

        JButton startBtn = new JButton("Build & Start Clout♧Scape");
        startBtn.setBackground(new Color(0, 150, 0));
        startBtn.setForeground(Color.WHITE);
        startBtn.addActionListener(e -> {
            script.getConfig().jagexEnabled = jagexEnabledBox.isSelected();
            script.getConfig().jagexEmail = jagexEmailField.getText();
            script.getConfig().jagexPassword = new String(jagexPasswordField.getPassword());
            script.getConfig().updateFromGui(webhookField.getText(), adArea.getText(), minBetField.getText(), maxBetField.getText(), "500m");
            
            if (!webhookField.getText().isEmpty()) {
                script.initializeWebhook(webhookField.getText());
            }
            setVisible(false);
        });
        add(startBtn, BorderLayout.SOUTH);
    }

    private JPanel createGeneralPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createLabel("Min Bet:"));
        minBetField = new JTextField("100k");
        panel.add(minBetField);

        panel.add(createLabel("Max Bet:"));
        maxBetField = new JTextField("100m");
        panel.add(maxBetField);

        panel.add(createLabel("Ad Messages:"));
        adArea = new JTextArea("Clout♧Scape | Best Odds | !help");
        panel.add(new JScrollPane(adArea));

        return panel;
    }

    private JPanel createJagexPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        jagexEnabledBox = new JCheckBox("Enable Jagex Account Login");
        jagexEnabledBox.setForeground(Color.WHITE);
        jagexEnabledBox.setBackground(new Color(45, 45, 45));
        panel.add(jagexEnabledBox);

        panel.add(createLabel("Jagex Email:"));
        jagexEmailField = new JTextField();
        panel.add(jagexEmailField);

        panel.add(createLabel("Jagex Password:"));
        jagexPasswordField = new JPasswordField();
        panel.add(jagexPasswordField);

        return panel;
    }

    private JPanel createGamesPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(45, 45, 45));
        panel.add(createLabel("Game Logic: Official 2026 Verified"));
        return panel;
    }

    private JPanel createDiscordPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 45, 45));
        panel.add(createLabel("Webhook URL:"), BorderLayout.NORTH);
        webhookField = new JTextField();
        panel.add(webhookField, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }
}
