package io.datcord.api.service.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Collection;

public interface CommandService {

    String requestGuildCommandData(long guildId);

    String requestGlobalCommandData();
}
