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

package org.benjaminschmitz.statusinformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.benjaminschmitz.statusinformation.api.API;
import org.benjaminschmitz.statusinformation.configuration.Configuration;
import org.benjaminschmitz.statusinformation.senders.Senders;

import java.util.LinkedList;
import java.util.List;

public class StatusInformation {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        API api = new API(configuration);
        List<String> messages = new LinkedList<>();
        Senders senders = new Senders(configuration);

        messages.add(api.getGoodMorningMessage());

        logger.debug("Building message list to one message string.");
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : messages) {
            stringBuilder.append(message);
            stringBuilder.append("\n");
        }
        String message = stringBuilder.toString();

        logger.debug("Sending message");
        senders.sendToAll(message);
    }
}


