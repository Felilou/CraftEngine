package net.pizzaboten.craftengine.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class SpacerWidget extends AbstractWidget {

    private int color;

    public SpacerWidget(int pX, int pY, int h, int w, int color) {
        super(pX, pY, w, h, Component.empty());
        this.color = color;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), color);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {

    }
}
