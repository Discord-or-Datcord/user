# The Feature Modules Directory

Hello! This directory is where the feature modules will go.

## What is a Feature?

In the context of our project, a feature is described as a reaction to any event that isn't a
SlashCommandInteractionEvent.

So it's essentially how our system responds to various event triggers, as long as the trigger is not a
SlashCommandInteractionEvent.

We encapsulate these reactions in what we call 'feature modules' and this directory is where we store them.

Please ensure to correctly place and manage your feature modules in this directory.

## Creating a Feature

In order to create a feature, it is necessary to first implement the event listener of the respective event your feature
is reacting to.

