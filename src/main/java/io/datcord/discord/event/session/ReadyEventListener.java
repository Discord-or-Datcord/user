package io.datcord.discord.event.session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Listener class that handles the bot {@link ReadyEvent} and updates global command and guild commands.
 *
 * TODO: Refactor command loading and REST methods to another class
 */
public class ReadyEventListener extends ListenerAdapter {

    /**
     * Logger instance for logging information.
     */
    private static final Logger logger = LoggerFactory.getLogger(ReadyEventListener.class);

    /**
     * Called when the bot is ready.
     *
     * @param event the ReadyEvent
     */
    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();

        /**
         * Update global commands.
         */
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(

        );
        commands.queue();
        logger.debug("Loaded global commands");

        /**
         * Update commands for each guild.
         */
        jda.getGuilds().forEach(guild -> {
            logger.debug("Loading commands for guild {}", guild.getName());
            guild.updateCommands().addCommands(readCommandDataForGuild(guild.getIdLong())).queue();
        });

        logger.debug("All commands loaded");
    }

    /**
     * Reads command data for a given guild from a remote API.
     *
     * TODO: Refactor this to a new class
     *
     * @param guildId the ID of the guild
     * @return a collection of CommandData for the guild
     */
    private Collection<CommandData> readCommandDataForGuild(long guildId) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/commands?guildId=" + guildId))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode rootNode = mapper.readTree(response.body());
            Collection<CommandData> commandDataList = new ArrayList<>();

            logger.debug("Response {} ", response.body());
            logger.info("root node is container node {}", rootNode.isContainerNode());
            if (rootNode.isContainerNode()) {
                for (JsonNode node : rootNode) {
                    logger.debug("Parsing Node {}", node.toString());

                    String commandJson = node.get("commandJson").asText();
                    Integer id = node.get("id").asInt();

                    logger.debug("Parsed command id {}", id);
                    logger.debug("Parsing command json {}", commandJson);

                    SlashCommandData slashCommandData = parseCommandData(commandJson);
                    commandDataList.add(slashCommandData);
                }
            }

            logger.debug("Read {} commands", commandDataList.size());

            return commandDataList;
        } catch (Exception e) {
            logger.warn("No value returned from GET");
            return Collections.emptyList();
        }
    }

    /**
     * Parses command data from a JSON string.
     *
     * TODO: Refactor this to a new class
     *
     * May need to revisit choice loading
     *
     * Missing support for fields {@link OptionData} minLength and maxLength
     *
     * @param commandJson the JSON string representing the command data
     * @return a SlashCommandData object
     * @throws Exception if parsing fails
     */
    private SlashCommandData parseCommandData(String commandJson) throws Exception {
        JsonNode commandNode = mapper.readTree(commandJson);
        String name = commandNode.get("name").asText();
        String description = commandNode.get("description").asText();

        SlashCommandData slashCommandData = Commands.slash(name, description);

        // If there are options in the command JSON, you can parse and add them here
        if (commandNode.has("options")) {
            logger.debug("Command has options {}",commandNode.get("options"));
            ArrayNode optionsNode = (ArrayNode) commandNode.get("options");

            for (JsonNode optionNode : optionsNode) {
                logger.debug("Parsing option node {}",optionNode.toString());
                String optionName = this.parseStringField("name",optionNode,"Missing name");
                logger.debug("Option name {}",optionName);
                String optionDescription = this.parseStringField("description",optionNode,"Missing desc.");
                logger.debug("Option desc. {}",optionDescription);
                OptionType optionType = OptionType.fromKey(this.parseIntegerField("type",optionNode,-1));
                logger.debug("Option Type {}",optionType);
                boolean required = this.parseBooleanField("required",optionNode,false);
                logger.debug("Option required {}",required);
                boolean autocomplete = this.parseBooleanField("autocomplete",optionNode,false);
                logger.debug("Option autocomplete {}",autocomplete);
                double minValue = this.parseDoubleField("minValue",optionNode,0D);
                logger.debug("Min. Value {}",minValue);
                double maxValue = this.parseDoubleField("maxValue",optionNode,0D);
                logger.debug("Max. Value {}",maxValue);

                // Create OptionData object
                OptionData optionData = new OptionData(optionType, optionName, optionDescription);

                optionData.setRequired(required);
                optionData.setAutoComplete(autocomplete);

                //TODO: Figure out why these break command loading
                //Interrupts the command loading process and no commands are loaded
//                optionData.setMinValue(minValue);
//                optionData.setMaxValue(maxValue);

                logger.debug("OptionData {}",optionData);

                // Add choices if present
                if (optionNode.has("choices")) {
                    logger.debug("Option has choices");
                    ArrayNode choicesNode = (ArrayNode) optionNode.get("choices");
                    logger.debug("Choices {}",choicesNode.toString());
                    for (JsonNode choiceNode : choicesNode) {
                        String choiceName = choiceNode.get("name").asText();
                        String choiceValue = choiceNode.get("value").asText(); // Assuming value is a string

                        optionData.addChoice(choiceName, choiceValue);
                    }
                }
                logger.debug("Adding command option data");
                slashCommandData.addOptions(optionData);
            }
        }
        logger.debug("Parsed command data {}",slashCommandData.toString());
        return slashCommandData;
    }

    /**
     * Parses a field from a JSON node.
     *
     * TODO: Refactor to new class
     *
     * @param field        the field name
     * @param node         the JSON node
     * @return the parsed field as a {@link com.fasterxml.jackson.databind.JsonNode JsonNode}
     */
    private JsonNode parseField(String field,JsonNode node) {
        if (node.has(field)) {
            return node.get(field);
        }
        return mapper.nullNode();
    }

    /**
     * Parses a string field from a JSON node.
     *
     * TODO: Refactor to new class
     *
     * @param field        the field name
     * @param node         the JSON node
     * @param defaultValue the default value if field is missing
     * @return the parsed string field
     */
    private String parseStringField(String field, JsonNode node,String defaultValue) {
        logger.debug("field {}, node {}, default {}",field,node.toString(),defaultValue);
        return parseField(field,node).asText(defaultValue);
    }

    /**
     * Parses a boolean field from a JSON node.
     *
     * TODO: Refactor to  new class
     *
     * @param field        the field name
     * @param node         the JSON node
     * @param defaultValue the default value if field is missing
     * @return the parsed boolean field
     */
    private boolean parseBooleanField(String field, JsonNode node, boolean defaultValue ) {
        return parseField(field,node).asBoolean(defaultValue);
    }

    /**
     * Parses a double field from a JSON node.
     *
     * TODO: Refactor to new class
     *
     * @param field        the field name
     * @param node         the JSON node
     * @param defaultValue the default value if field is missing
     * @return the parsed double field
     */
    private double parseDoubleField(String field, JsonNode node,double defaultValue) {
        return parseField(field,node).asDouble(defaultValue);
    }

    /**
     * Parses an integer field from a JSON node.
     *
     * TODO: Refactor to new class
     *
     * @param field        the field name
     * @param node         the JSON node
     * @param defaultValue the default value if field is missing
     * @return the parsed integer field
     */
    private int parseIntegerField(String field, JsonNode node,int defaultValue) {
        return parseField(field,node).asInt(defaultValue);
    }

    /**
     * ObjectMapper instance for parsing JSON data.
     *
     * TODO: Refactor to new class
     */
    private final ObjectMapper mapper = new ObjectMapper();
}

