package net.pizzaboten.craftengine.Screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pizzaboten.craftengine.CommandButton;
import net.pizzaboten.craftengine.CraftEngine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

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

        JSONObject json = CraftEngine.COMMANDS;
        JSONObject commands = json.getJSONObject("Commands");
        Iterator<String> keys = commands.keys();
        int yOff = 10;
        while (keys.hasNext()) {
            String category = keys.next();
            System.out.println("Category: " + category);
            JSONArray commandArray = commands.getJSONArray(category);
            for (int i = 0; i < commandArray.length(); i++) {
                JSONObject command = commandArray.getJSONObject(i);
                String commandString = command.getString("Command");
                String displayString = command.getString("Display");
                String tooltipString = command.getString("Info");

                System.out.println("  Display: " + displayString);
                System.out.println("  Command: " + commandString);
                System.out.println("  Info: " + tooltipString);
                System.out.println();
                this.addRenderableWidget(new CommandButton(commandString, this.width / 2 - 80, yOff, 160, 20, displayString));
                yOff += 30;
            }
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        return super.mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY);
    }
}

