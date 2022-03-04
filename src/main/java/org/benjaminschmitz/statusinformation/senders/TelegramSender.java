/*
 * Copyright (c) 2022 Benjamin Schmitz <dev@benjamin-schmitz.org>.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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
            throw new SendingException("HTTP request failed: " + response.statusCode());
        }
    }

}
