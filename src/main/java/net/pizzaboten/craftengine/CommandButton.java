package net.pizzaboten.craftengine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class CommandButton extends Button {

    protected CommandButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, CreateNarration pCreateNarration) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pCreateNarration);
    }

    protected CommandButton(Builder builder) {
        super(builder);
    }

    public CommandButton(String command, int x, int y, int width, int height, String display) {
        super(Button.builder(Component.literal(display), button -> {
            CraftEngine.executeCommand(command);
        }).bounds(x, y, width, height));

    }
}
