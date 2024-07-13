# Event Module

This module serves as the core component in handling Discord events.

## Overview

The event module is designed to centralize event handling logic and provide a structured approach for integrating and managing events across the application.


## Getting Started

To create a new Discord event listener:
1. Create the packaging to mirror the packaging for the event in jda
2. Create the event listener class and extend ListenerAdapter
3. Override the method you are listening for onReadyEvent for example
4. Add the @Subscribe annotation to the method
5. Go to the EventDispatcher class and register the event

To create a ButtonInteractionEventListener (In another module):
1. Create the implementing class and implement ButtonInteractionEventListener and the CommandModuleInitializer interface
2. Add the @Subscribe annotation to the onButtonInteractionEvent method
3. Add  ButtonInteractionEventListenerRegistry.register(this); to the init method
4. Create a io.datcord.event.EventModuleInitializer file in the services directory in the main resources for the module
5. In the io.datcord.event.EventModuleInitializer file add the fully qualified name to the implementing class created in step 1
6. Ensure the module-info.java is configured in the new module
