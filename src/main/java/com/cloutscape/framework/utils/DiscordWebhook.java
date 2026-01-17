package com.cloutscape.framework.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook {
    private final String url;

    public DiscordWebhook(String url) {
        this.url = url;
    }

    public void sendEmbed(String title, String description, int color, String... fields) {
        if (url == null || url.isEmpty() || !url.startsWith("http")) return;

        new Thread(() -> {
            try {
                StringBuilder json = new StringBuilder();
                json.append("{ \"embeds\": [{ ");
                json.append("\"title\": \"").append(title).append("\", ");
                json.append("\"description\": \"").append(description).append("\", ");
                json.append("\"color\": ").append(color);

                if (fields.length > 0) {
                    json.append(", \"fields\": [");
                    for (int i = 0; i < fields.length; i += 2) {
                        if (i + 1 >= fields.length) break;
                        json.append("{ \"name\": \"").append(fields[i]).append("\", \"value\": \"").append(fields[i+1]).append("\", \"inline\": true }");
                        if (i + 2 < fields.length) json.append(",");
                    }
                    json.append("]");
                }

                json.append(" }] }");

                URL urlObj = new URL(this.url);
                HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                con.getResponseCode();
                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
