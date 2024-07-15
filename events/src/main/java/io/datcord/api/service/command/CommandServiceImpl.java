package io.datcord.api.service.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of the CommandService for making HTTP calls to fetch command data.
 * This class uses Java's HttpClient for executing HTTP requests. It fetches both global and guild-specific
 * command-related data. The responses of the HTTP request are currently only logged and no data is actually returned.
 * In the guild-specific implementation, it makes a request to a 'guild' endpoint providing the guildId as a request parameter.
 * In the global implementation, it makes a request to a 'global' endpoint with no additional parameters.
 *
 * TODO: Refactor the ROOT_API to properties. The base API URL should not be hardcoded.
 */
public class CommandServiceImpl implements CommandService {

    private static final Logger logger = LoggerFactory.getLogger(CommandServiceImpl.class);
    private static final String ROOT_API = "http://localhost:8080/api/v1/commands";//TODO: Refactor this to properties

    @Override
    public String requestGuildCommandData(long guildId) {
        String url = ROOT_API + "/guild?guildId=" + guildId;
        return executeCommandRequest(url);
    }

    @Override
    public String requestGlobalCommandData() {
        String url = ROOT_API + "/global";
       return executeCommandRequest(url);


    }

    private String executeCommandRequest(String url) {
        logger.info("Requesting command data to {}", url);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            logger.info("Request sent to {}", url);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Response received {}", response.body());
            return response.body();
        } catch (InterruptedException | IOException e) {
            logger.warn("No response: {}", e.getMessage());
        }
        return null;
    }
}
