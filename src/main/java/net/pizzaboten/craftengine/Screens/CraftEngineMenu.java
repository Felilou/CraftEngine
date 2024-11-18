package net.pizzaboten.craftengine.Screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pizzaboten.craftengine.widgets.*;
import net.pizzaboten.craftengine.CraftEngine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CraftEngineMenu extends Screen {

    //Breakpoints x =

    private final Screen parentScreen;
    private scrollableWidget scrollableSection;
    private final List<AbstractWidget> CommandListWidgets = new ArrayList<>();
    private boolean isPause = false;


    public CraftEngineMenu(Screen parentScreen) {
        super(Component.literal("Craft Engine"));
        this.parentScreen = parentScreen;
        fillButtonsList();
    }

    @Override
    protected void init() {
        initTopContainer();
        initInfoString();
        initScrollSection();
    }

    private void initInfoString() {
        StringBuilder builder = new StringBuilder();
        assert Minecraft.getInstance().player != null;
        builder.append(Minecraft.getInstance().player.getName().getString());
        builder.append(" ");
        builder.append("Permission Level: ");
        builder.append(Minecraft.getInstance().player.getPermissionLevel());

        MultiLineTextWidget textWidget = new MultiLineTextWidget(5, 85, Component.literal(builder.toString()), Minecraft.getInstance().font);

        addRenderableWidget(textWidget);
    }

    private void initScrollSection() {

        AbstractWidget bg = new AbstractWidget(5, 95, this.width-10, 1, Component.literal("Craft Engine")) {

            @Override
            protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                pGuiGraphics.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xFFFFFFFF);
            }

            @Override
            protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

            }

        };

        addRenderableWidget(bg);


        scrollableSection = new scrollableWidget(Minecraft.getInstance(), this.width - 150,this.height-100, 100, 145, CommandListWidgets);
        addRenderableWidget(scrollableSection);

        for(int i=0; i<4; i++){
            Button b = new CommandButton("w", 5, 100 + 25 * i,  145 - 10, 20, "waaaas");
            addRenderableWidget(b);
        }

    }

    private void initTopContainer() {

        this.addRenderableWidget(Button.builder(Component.literal("X"), button -> {
            Minecraft.getInstance().setScreen(parentScreen);
        }).bounds( 5, 5, 20, 20).build());

        MultiLineTextWidget title = new MultiLineTextWidget(0, 10, Component.literal("Craft Engine"), Minecraft.getInstance().font);
        title.setX(this.width / 2 - title.getWidth() / 2);

        Checkbox checkbox = Checkbox.builder(Component.literal("Pause"), Minecraft.getInstance().font).pos(6, 25+2).onValueChange( (valChange, is) -> {
            isPause = is;
        }).selected(isPause).build();

        //height =  20 + 5 = 25
        this.addRenderableWidget(title);
        this.addRenderableWidget(checkbox);

    }



    private void fillButtonsList() {
        CommandListWidgets.removeAll(CommandListWidgets);

        JSONObject commands = CraftEngine.COMMANDS.getJSONObject("Commands");
        boolean first = true;
        for (String category : commands.keySet()) {
            if (!first) {
                CommandListWidgets.add(new SpacerWidget(0, 0, 10, 0));
            }
            CommandListWidgets.add(new TextWidget(0, 0, 0, Component.literal(category), false));
            first = false;
            JSONArray commandList = commands.getJSONArray(category);

            for (int i = 0; i < commandList.length(); i++) {

                JSONObject command = commandList.getJSONObject(i);

                CommandListWidgets.add(new CommandButton(command.getString("Command"), 0, 0, 0, 20, command.getString("Display")));
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
    }

    @Override
    public boolean isPauseScreen() {
        return isPause;
    }
}
