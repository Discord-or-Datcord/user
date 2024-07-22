package io.datcord.command.jira;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The ResponseBuilder class provides methods to build a JSON response object with mapped string or long fields.
 *
 * TODO: Refactor this into a class in the app module to be reused
 */
public class ResponseBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ResponseBuilder.class);

    /**
     * Maps a string field to a given fieldName and value in the JSON response object.
     *
     * @param fieldName The name of the field to be mapped.
     * @param value The value of the field to be mapped.
     * @return The updated JSON response object with the mapped string field.
     */
    public static ObjectNode mapStringField(String fieldName, String value) {
        return objectNode.put(fieldName,value);
    }

    /**
     * Maps a long field to a given fieldName and value in the JSON response object.
     *
     * @param fieldName The name of the field to be mapped.
     * @param value The value of the field to be mapped.
     * @return The updated JSON response object with the mapped long field.
     */
    public static ObjectNode mapLongField(String fieldName, Long value) {
        return objectNode.put(fieldName,value);
    }

    /**
     * Converts the JSON response object to a string representation.
     *
     * @return The JSON response object as a string.
     */
    public static String asJsonString() {
        return objectNode.toString();
    }

    /**
     * Clears all the mapped fields in the JSON response object.
     */
    public static void clear() {
        objectNode.removeAll();
    }

    public String jiraAssociationGET(String assigneeName, long guildId) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/v1/commands/jira/association"
                            + "?assigneeName=" + assigneeName
                            + "&guildId=" + guildId))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Response received {}", response.body());
            return response.body();
        } catch (InterruptedException | IOException e) {
            logger.warn("No response: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Makes a POST request to Jira to create an association.
     *
     */
    public static int jiraAssociationPOST() {
        int status = 0;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/v1/commands/jira/create"))
                    .header("Content-Type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(asJsonString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Response received {}", response.body());
            status = response.statusCode();
        } catch (InterruptedException | IOException e) {
            logger.warn("No response: {}", e.getMessage());
        }
        return status;
    }

    private static final ObjectNode objectNode = new ObjectMapper().createObjectNode();
}
