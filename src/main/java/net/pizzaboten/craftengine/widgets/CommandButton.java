package net.pizzaboten.craftengine.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.pizzaboten.craftengine.CraftEngine;

public class CommandButton extends Button {

    String command;
    String description;
    String display;

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplay() {
        return display;
    }

    public CommandButton(String command, int x, int y, int width, int height, String display, String description) {
        super(Button.builder(Component.literal(display), button -> {
             CraftEngine.executeCommand(command);
        }).bounds(x, y, width, height).tooltip(Tooltip.create(Component.literal("Command: " + command + "\nDescription: "+description + "\nSHIFT + Click on button assign (MAX 4)"))));
        this.command = command;
        this.description = description;
        this.display = display;
    }

}
