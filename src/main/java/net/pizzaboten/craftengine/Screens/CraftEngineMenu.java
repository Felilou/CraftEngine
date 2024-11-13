package net.pizzaboten.craftengine.Screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pizzaboten.craftengine.CraftEngine;
import net.pizzaboten.craftengine.widgets.ScrollableSectionWidget;
import net.pizzaboten.craftengine.CommandButton;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CraftEngineMenu extends Screen {
    private final Screen parentScreen;
    private ScrollableSectionWidget scrollableSection;
    private final List<CommandButton> commandButtons = new ArrayList<>();

    public CraftEngineMenu(Screen parentScreen) {
        super(Component.literal("Craft Engine"));
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() {
        addReturnButton();

        // Erstelle den scrollbaren Bereich
        int scrollX = this.width / 2 - 80;
        int scrollY = 60;
        int scrollWidth = 160;
        int scrollHeight = this.height - 100;

        scrollableSection = new ScrollableSectionWidget(scrollX, scrollY, scrollWidth, scrollHeight, Component.literal("Commands"));
        this.addRenderableWidget(scrollableSection);

        // Füge die Command-Buttons zur Liste hinzu, ohne sie direkt als Widgets hinzuzufügen
        addCommandButtons();
    }

    private void addCommandButtons() {
        JSONObject json = CraftEngine.COMMANDS;
        JSONObject commands = json.getJSONObject("Commands");
        Iterator<String> keys = commands.keys();

        int yOff = 10;
        while (keys.hasNext()) {
            String category = keys.next();
            JSONArray commandArray = commands.getJSONArray(category);
            for (int i = 0; i < commandArray.length(); i++) {
                JSONObject command = commandArray.getJSONObject(i);
                String commandString = command.getString("Command");
                String displayString = command.getString("Display");

                // Erstelle einen CommandButton und füge ihn zur Liste hinzu
                CommandButton commandButton = new CommandButton(commandString, scrollableSection.getX() + 5, yOff, 150, 20, displayString);
                commandButtons.add(commandButton);

                yOff += 30; // Abstand zwischen den Buttons
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.drawCenteredString(this.font, this.title.getString(), this.width / 2, 40, 0xFFFFFF);

        // Rendere den Scrollbereich und passe die Positionen der Buttons an
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderScrollableSection(guiGraphics);
    }

    private void renderScrollableSection(GuiGraphics guiGraphics) {
        int scrollOffset = (int) scrollableSection.scrollAmount();

        for (CommandButton button : commandButtons) {
            // Berechne die verschobene Y-Position basierend auf dem Scroll-Offset
            int adjustedY = button.getY() - scrollOffset;

            // Stelle sicher, dass der Button innerhalb des sichtbaren Bereichs des Scrollbereichs liegt
            if (adjustedY >= scrollableSection.getY() && adjustedY <= scrollableSection.getY() + scrollableSection.getHeight()) {
                // Setze die neue Position und rendere den Button
                button.setY(adjustedY);
                button.render(guiGraphics, 0, 0, 0);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollableSection != null) {
            return scrollableSection.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private void addReturnButton() {
        this.addRenderableWidget(Button.builder(Component.literal("Return"), button -> {
            Minecraft.getInstance().setScreen(parentScreen);
        }).bounds(this.width / 2 - 102, this.height / 4 + 147, 204, 20).build());
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
