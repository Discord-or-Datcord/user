package io.datcord.mapper.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

/**
 * A base abstract class for mapping JSON data to objects of type T.
 * Subclasses must implement the {@code parseObject} method to specify how each JSON node is parsed into an object of type T.
 *
 * @param <T> the type of object to map JSON data to
 */
public abstract class JsonMapper<T> {
    /**
     * Logger for the class
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonMapper.class);


    /**
     * Converts a JSON string into a collection of objects of type T.
     *
     * @param jsonBody the JSON string to convert
     * @return a collection of parsed objects
     */
    public T fromJson(String jsonBody) {
        JsonNode rootNode = mapRoot(jsonBody);
        logger.debug("Root node: {}", rootNode);
//
//        if (rootNode.isContainerNode()) {
//            Collection<T> outputList = new ArrayList<>();
//            for (JsonNode node : rootNode) {
//                logger.debug("Parsing node {}", node);
//                T parsedObject = parseObject(node);
//                logger.debug("Parsed object {}", parsedObject);
//                outputList.add(parsedObject);
//            }
//            return outputList;
//        }
        return parseObject(rootNode);
    }


    public Collection<T> fromJsonAsCollection(String jsonBody) {
        JsonNode rootNode = mapRoot(jsonBody);
        Collection<T> outputList = new ArrayList<>();
        if (rootNode.isContainerNode()) {
            for (JsonNode node : rootNode) {
                logger.debug("Parsing node {}", node);
                T parsedObject = parseObject(node);
                logger.debug("Parsed object {}", parsedObject);
                outputList.add(parsedObject);
            }

        }
        return outputList;
    }

    /**
     * Parses a JSON node into an object of type T.
     *
     * @param node the JSON node to parse
     * @return an object of type T parsed from the JSON node
     */
    protected abstract T parseObject(JsonNode node);

    /**
     * Parses a field from a JSON node.
     *
     * @param field        the field name
     * @param node         the JSON node
     * @return the parsed field as a {@link JsonNode JsonNode}
     */
    protected JsonNode parseField(String field,JsonNode node) {
        if (node.has(field)) {
            return node.get(field);
        }
        return mapper.nullNode();
    }

    /**
     * Parses a string field from a JSON node.
     *
     * @param field        the field name
     * @param node         the JSON node
     * @param defaultValue the default value if field is missing
     * @return the parsed string field
     */
    protected String parseStringField(String field, JsonNode node,String defaultValue) {
        logger.debug("field {}, node {}, default {}",field,node.toString(),defaultValue);
        return parseField(field,node).asText(defaultValue);
    }

    /**
     * Parses a boolean field from a JSON node.
     *
     * @param field        the field name
     * @param node         the JSON node
     * @param defaultValue the default value if field is missing
     * @return the parsed boolean field
     */
    protected boolean parseBooleanField(String field, JsonNode node, boolean defaultValue ) {
        return parseField(field,node).asBoolean(defaultValue);
    }

    /**
     * Parses a double field from a JSON node.
     *
     * @param field        the field name
     * @param node         the JSON node
     * @param defaultValue the default value if field is missing
     * @return the parsed double field
     */
    protected double parseDoubleField(String field, JsonNode node,double defaultValue) {
        return parseField(field,node).asDouble(defaultValue);
    }

    /**
     * Parses an integer field from a JSON node.
     *
     * @param field        the field name
     * @param node         the JSON node
     * @param defaultValue the default value if field is missing
     * @return the parsed integer field
     */
    protected int parseIntegerField(String field, JsonNode node,int defaultValue) {
        return parseField(field,node).asInt(defaultValue);
    }

    /**
     * Maps the root JSON node from a JSON string.
     *
     * @param json the JSON string to map
     * @return the root JSON node
     * @throws NullPointerException if the root node is not a container node (object or array)
     * @throws RuntimeException if there was an error mapping the JSON string to JsonNode
     */
    private JsonNode mapRoot(String json) {
        try {
            logger.debug("json: {}", json);
            JsonNode rootNode =  mapper.readTree(json);

            if (rootNode.isObject()) {
                rootNode.fields().forEachRemaining(entry -> logger.debug("Key: {}, Value: {}", entry.getKey(), entry.getValue()));
                return rootNode;
            } else if (rootNode.isArray()) {
                IntStream.range(0, rootNode.size()).mapToObj(rootNode::get).forEach(node -> logger.debug("Value: {}", node));
                return rootNode;
            } else {//root node should always be a container node. A container node is an object or array
                logger.debug("Root node is a single value node, Value: {}", rootNode);
                throw new NullPointerException("Root node is not a container node");
            }


        } catch (Exception e) {
            logger.warn("Failed to map to JsonNode: {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private final ObjectMapper mapper = new ObjectMapper();
}
