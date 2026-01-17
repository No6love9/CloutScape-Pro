package com.cloutscape.framework.gui;

import com.cloutscape.framework.core.CloutScape;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class CloutGUI extends JFrame {
    private final CloutScape script;
    private JTextField webhookField;
    private JTextArea adArea;
    private JTextField minBetField;
    private JTextField maxBetField;

    public CloutGUI(CloutScape script) {
        this.script = script;
        setTitle("Clout♧Scape | Enterprise Casino Framework");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        UIManager.put("Panel.background", new Color(30, 30, 30));
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TextField.background", new Color(45, 45, 45));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextArea.background", new Color(45, 45, 45));
        UIManager.put("TextArea.foreground", Color.WHITE);
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", createDashboard());
        tabs.addTab("Games", createGamesPanel());
        tabs.addTab("Discord Webhook", createWebhookPanel());
        tabs.addTab("Advertising", createAdPanel());
        tabs.addTab("Settings", createSettingsPanel());

        add(tabs, BorderLayout.CENTER);
        
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton copyLogsBtn = new JButton("Copy Session Logs");
        copyLogsBtn.addActionListener(e -> {
            StringSelection selection = new StringSelection("CloutScape Session Log Data...");
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
            JOptionPane.showMessageDialog(this, "Logs copied to clipboard!");
        });
        
        JButton startBtn = new JButton("Apply & Start");
        startBtn.setBackground(new Color(0, 150, 0));
        startBtn.setForeground(Color.WHITE);
        startBtn.addActionListener(e -> {
            script.getConfig().updateFromGui(
                getWebhookUrl(), 
                getAdMessages(), 
                minBetField.getText(), 
                maxBetField.getText(), 
                "500m"
            );
            
            String url = getWebhookUrl();
            if (url != null && !url.isEmpty()) {
                script.initializeWebhook(url);
            }
            setVisible(false);
        });
        
        footer.add(copyLogsBtn);
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

    private JPanel createWebhookPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(new JLabel("Discord Webhook URL (Supports Copy/Paste):"));
        webhookField = new JTextField();
        top.add(webhookField);
        
        panel.add(top, BorderLayout.NORTH);
        
        JTextArea info = new JTextArea("Relay bot status, game results, and trade actions directly to your Discord channel.\n\n" +
                "• Real-time Game Embeds\n" +
                "• Profit Milestones\n" +
                "• Anti-Ban Trigger Alerts\n" +
                "• Trade Verification Logs");
        info.setEditable(false);
        info.setOpaque(false);
        panel.add(new JScrollPane(info), BorderLayout.CENTER);
        
        return panel;
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

    private JPanel createAdPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        adArea = new JTextArea("Clout♧Scape | Best Odds | Fast Payouts | !help");
        adArea.setLineWrap(true);
        panel.add(new JLabel("Advertising Messages (One per line):"), BorderLayout.NORTH);
        panel.add(new JScrollPane(adArea), BorderLayout.CENTER);
        
        JButton clearBtn = new JButton("Clear Ads");
        clearBtn.addActionListener(e -> adArea.setText(""));
        panel.add(clearBtn, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Min Bet:"));
        minBetField = new JTextField("100k");
        panel.add(minBetField);
        
        panel.add(new JLabel("Max Bet:"));
        maxBetField = new JTextField("100m");
        panel.add(maxBetField);
        
        panel.add(new JLabel("Mule Threshold:"));
        panel.add(new JTextField("500m"));
        
        return panel;
    }

    public String getWebhookUrl() { return webhookField.getText(); }
    public String getAdMessages() { return adArea.getText(); }
}
