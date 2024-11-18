package net.pizzaboten.craftengine.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.FittingMultiLineTextWidget;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.network.chat.Component;

public class SpacerWidget extends FittingMultiLineTextWidget {
    public SpacerWidget(int pX, int pY, int h, int w) {
        super(pX, pY, w, h, Component.empty(), Minecraft.getInstance().font);
    }
}
