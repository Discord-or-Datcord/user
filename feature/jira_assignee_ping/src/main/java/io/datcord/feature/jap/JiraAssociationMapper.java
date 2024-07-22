package io.datcord.feature.jap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.datcord.mapper.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraAssociationMapper extends JsonMapper<JiraAssociationDto>{

    private static final Logger logger = LoggerFactory.getLogger(JiraAssociationMapper.class);

    @Override
    protected JiraAssociationDto parseObject(JsonNode node) {
        try {
            logger.info("Mapping {}",node);
            return mapper.readValue(node.toString(),JiraAssociationDto.class);
        } catch (JsonProcessingException e) {
            logger.error("Failed to map Jira association", e);
            throw new RuntimeException(e);
        }
    }

    private final ObjectMapper mapper = new ObjectMapper();
}
