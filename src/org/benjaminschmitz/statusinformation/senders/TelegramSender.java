package org.benjaminschmitz.statusinformation.senders;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.benjaminschmitz.statusinformation.Configuration;
import jakarta.ws.rs.core.UriBuilder;

public class TelegramSender implements Sender {
  public void send(String message) {
    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5))
        .version(HttpClient.Version.HTTP_2).build();

    UriBuilder builder = UriBuilder.fromUri("https://api.telegram.org").path("/{token}/sendMessage")
        .queryParam("parse_mode", "Markdown")
        .queryParam("chat_id", Configuration.TELEGRAM_CHANNEL_ID).queryParam("text", message)
        .queryParam("disable_web_page_preview", "1");

    HttpRequest request =
        HttpRequest.newBuilder().GET().uri(builder.build("bot" + Configuration.TELEGRAM_BOT_TOKEN))
            .timeout(Duration.ofSeconds(5)).build();

    HttpResponse<String> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("TelegramSender: Error while making HTTP request.");
    }

    System.out.println(response.statusCode());
    System.out.println(response.body());
  }
}
