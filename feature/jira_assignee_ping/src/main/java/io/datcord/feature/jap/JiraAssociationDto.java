package io.datcord.feature.jap;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JiraAssociationDto {

    private String assigneeName;
    private long userId;
    private long guildId;

}
