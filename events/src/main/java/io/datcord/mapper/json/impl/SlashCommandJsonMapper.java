package io.datcord.mapper.json.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.datcord.mapper.json.JsonMapper;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is {@code SlashCommandJsonMapper} class, which extends the {@code JsonMapper<CommandData>} abstract class.
 *
 * It is responsible for mapping JSON representation of Slash Command to {@code CommandData} object,
 * which further can be used to create Slash Commands in Bot for Discord.
 *
 * In particular, through the overridden {@code parseObject} method, it reads key elements
 * such as command name, description, options, etc. from the inputted {@code JsonNode} object,
 * then instantiates and populates respective fields in a new {@code SlashCommandData} object.
 *
 * This class also handles the parsing of Slash Command options with their respective types, descriptions,
 * requirements, autocomplete, min and max values. In addition, if an option has present choices, this class
 * adds these choices to the {@code OptionData}.
 */
public class SlashCommandJsonMapper extends JsonMapper<CommandData> {

    private static final Logger logger = LoggerFactory.getLogger(SlashCommandJsonMapper.class);


    @Override
    protected CommandData parseObject(JsonNode node)  {
        JsonNode commandJsonNode = node.get("commandJson");
        JsonNode commandNode = null;
        try {
            commandNode = mapper.readTree(commandJsonNode.asText());
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse command json", e);
            throw new RuntimeException(e);
        }
        logger.debug("Parsing command data {}", commandNode.toString());
        logger.debug("Command name {}", commandNode.get("name"));
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

    private final ObjectMapper mapper = new ObjectMapper();
}
