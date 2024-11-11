package net.pizzaboten.craftengine.Screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pizzaboten.craftengine.CommandButton;

public class CraftEngineMenu extends Screen
{
    private final Screen parentScreen;

    public CraftEngineMenu(Screen parentScreen)
    {
        super(Component.literal("Craft Engine"));
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init()
    {
        addReturnButton();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        guiGraphics.drawCenteredString(this.font, this.title.getString(), this.width / 2, 40, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen()
    {
        return true;
    }

    private void addReturnButton(){
        this.addRenderableWidget(Button.builder(Component.literal("Return"), button -> {
            Minecraft.getInstance().setScreen(parentScreen);
        }).bounds(this.width / 2 - 102, this.height / 4 + 147, 204, 20).build());
        this.addRenderableWidget(new CommandButton("kill @e[type=!player]", 0, 0, 200, 50, "lol"));
    }
}

