package net.pizzaboten.craftengine.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.network.chat.Component;

public class TextWidget extends MultiLineTextWidget {

    public TextWidget(int pX, int pY, int width, Component pMessage) {
        super(pX, pY, pMessage, Minecraft.getInstance().font);
        setWidth(width);
    }

}
