package net.pizzaboten.craftengine.Screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pizzaboten.craftengine.CommandButton;
import net.pizzaboten.craftengine.CraftEngine;
import net.pizzaboten.craftengine.widgets.ScrollableRenderable;
import org.json.JSONArray;
import org.json.JSONObject;
import net.minecraft.client.gui.components.FittingMultiLineTextWidget;
import static net.pizzaboten.craftengine.CraftEngine.LOGGER;

import java.util.ArrayList;
import java.util.List;

public class CraftEngineMenu extends Screen {
    private final Screen parentScreen;
    private ScrollableRenderable scrollableSection;
    private final List<AbstractWidget> buttons = new ArrayList<>();

    public CraftEngineMenu(Screen parentScreen) {
        super(Component.literal("Craft Engine"));
        this.parentScreen = parentScreen;

    }

    @Override
    protected void init() {
        addReturnButton();
        fillButtonsList();
        scrollableSection = new ScrollableRenderable(Minecraft.getInstance(), this.width - 40,this.height-40, 10, this.width / 2 - ((this.width - 40) / 2) , buttons);
        this.addRenderableWidget(scrollableSection);
    }

    private void fillButtonsList() {
        buttons.removeAll(buttons);
        JSONObject commands = CraftEngine.COMMANDS.getJSONObject("Commands");
        for (String category : commands.keySet()) {
            buttons.add(new FittingMultiLineTextWidget(0, 0, this.width - 40 - 10, 20, Component.literal(category), Minecraft.getInstance().font));
            JSONArray commandList = commands.getJSONArray(category);
            for (int i = 0; i < commandList.length(); i++) {
                JSONObject command = commandList.getJSONObject(i);
                buttons.add(new CommandButton(
                        command.getString("Command"),
                        0,
                        0,
                        this.width - 40 - 10,
                        20,
                        command.getString("Display")
                ));
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
        }).bounds(this.width / 2 - 102, this.height-40 + 10 + 5, 204, 20).build());
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
