package com.cloutscape.dreambot;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EnterpriseGUI {
    private JFrame frame;
    private final String version = "2026.1.0";

    public void display(Runnable onStart) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        frame = new JFrame("Clout♧Scape Enterprise v" + version);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(25, 25, 25));

        // Header
        JLabel header = new JLabel("CLOUT♧SCAPE PRO", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(new Color(0, 255, 127));
        mainPanel.add(header, BorderLayout.NORTH);

        // Config Area
        JPanel configPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        configPanel.setOpaque(false);
        
        addConfigRow(configPanel, "Min Bet:", "100,000");
        addConfigRow(configPanel, "Max Bet:", "100,000,000");
        addConfigRow(configPanel, "Webhook:", "https://discord.com/api/...");
        addConfigRow(configPanel, "Jagex ID:", "user@jagex.com");

        mainPanel.add(configPanel, BorderLayout.CENTER);

        // Footer
        JButton startBtn = new JButton("INITIALIZE ENTERPRISE ENGINE");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        startBtn.setBackground(new Color(0, 255, 127));
        startBtn.setForeground(Color.BLACK);
        startBtn.addActionListener(e -> {
            onStart.run();
            frame.dispose();
        });
        mainPanel.add(startBtn, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void addConfigRow(JPanel panel, String label, String value) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        JTextField txt = new JTextField(value);
        txt.setBackground(new Color(45, 45, 45));
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(Color.WHITE);
        panel.add(lbl);
        panel.add(txt);
    }
}
