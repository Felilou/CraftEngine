# CraftEngine Documentation

## Overview
CraftEngine is a Minecraft mod built for version 1.21.x using the Forge Mod API.

## Technical Requirements
- Minecraft 1.21.x
- Forge Mod API 1.21.x
- Java (with UTF-8 encoding)
- Gradle build system

## Project Structure
```/src/main/resources/``` - Primary resources directory
```/src/generated/resources/``` - Generated resources directory
```/mcmodsrepo/``` - Local maven repository for mod artifacts
```/run/``` - Development runtime directory
```/run-data/``` - Data generation runtime directory

## Build Configuration
- Uses Gradle with ShadowJar plugin for dependency packaging
- Includes org.json dependency (version 20210307)
- Supports Maven publishing
- Uses Forge's mapping system (configurable between official/parchment channels)

## Development Features
- IDE Integration support (Eclipse/IntelliJ)
- Resource processing with property replacement
- Data generation capabilities
- Debug logging configuration
- Access transformer support
- Custom run configurations for development

## Building the Project
1. Run the shadowJar task:
```bash
./gradlew shadowJar
