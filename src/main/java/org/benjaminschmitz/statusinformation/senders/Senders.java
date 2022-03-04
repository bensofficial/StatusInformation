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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.benjaminschmitz.statusinformation.configuration.Configuration;

import java.util.LinkedList;
import java.util.List;

public class Senders {
    private static final Logger logger = LogManager.getLogger();
    private final Configuration configuration;
    private final List<Sender> senders;


    public Senders(Configuration configuration) {
        logger.debug("Initialising");
        this.configuration = configuration;
        senders = new LinkedList<>();

        senders.add(new TelegramSender());
        senders.add(new StdOutSender());
    }

    public void sendToAll(String message) {
        for (Sender sender : senders) {
            logger.debug("Sending to " + sender);
            try {
                sender.send(message, configuration);
            } catch (SendingException e) {
                logger.error("Error while sending to " + sender + ". Error message: " + e);
            }
        }
    }

    public List<Sender> getSenders() {
        return senders;
    }
}
