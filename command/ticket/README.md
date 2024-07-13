# README.md

## Project Update

We would like to share that the development for a specific feature has been indefinitely deferred, however, the feature
will remain active in the project.

This has been chosen primarily to serve as an educational tool for implementing command listeners and event listeners in
a command module. Though the augmentation of this feature may have been eagerly awaited, we have chosen to delay its
progression for now due to factors best apt to our project aims and roadmap.

## Demo Implementations

### Command Listeners

See TicketSlashCommandListener as an example of how to implement a CommandListener in a command module. Note the
Implementation of both SlashCommandListener and CommandModuleInitializer. The SlashCommandListener implementation
handles the command logic while CommandModuleInitializer is responsible for registering the command with the
SlashCommandRegistry.

See module-info to see how the listener class implementations are provided to the commands module to be loaded
in the SlashCommandRegistry. Also note the inclusion of the services directory in the resources folder. Evey
SlashCommandListener must be listed in the io.datcord.command.CommandModuleInitializer to be loaded as a service
by the SlashCommandRegistry.

## Unclear Service Declaration Purpose

The current functionality and necessity of the files present in the `services` directory remains somewhat unclear.
Relevant observations suggest that both `TicketBoardButtonInteractionEventListener` and
`TicketBoardEntitySelectInteractionEventListener` are loaded correctly, despite only the
`TicketBoardButtonInteractionEventListener` being declared in the `io.datcord.command.CommandModuleInitializer` file
located
within the `services` directory.
This functionality showcases its potential redundancy and may signal an opportunity to optimize project organization and
hierarchy.