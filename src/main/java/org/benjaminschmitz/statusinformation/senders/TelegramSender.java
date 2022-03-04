package org.benjaminschmitz.statusinformation.senders;

import jakarta.ws.rs.core.UriBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.benjaminschmitz.statusinformation.configuration.Configuration;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class TelegramSender implements Sender {
	private static final Logger logger = LogManager.getLogger();

    public void send(String message, Configuration configuration) {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2).build();

        UriBuilder builder = UriBuilder.fromUri("https://api.telegram.org").path("/{token}/sendMessage")
                .queryParam("parse_mode", "Markdown").queryParam("chat_id", configuration.getTelegramChannelID())
                .queryParam("text", message).queryParam("disable_web_page_preview", "1");

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(builder.build("bot" + configuration.getTelegramBotToken())).timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new SendingException("TelegramSender: Error while making HTTP request.");
        }

        if (response.statusCode() == 200) {
			logger.debug("Sent successfully.");
        } else {
            throw new SendingException("TelegramSender: HTTP request failed: " + response.statusCode());
        }
    }

}
