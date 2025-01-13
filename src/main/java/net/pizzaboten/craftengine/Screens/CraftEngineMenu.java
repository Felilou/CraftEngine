package net.pizzaboten.craftengine.Screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pizzaboten.craftengine.widgets.*;
import net.pizzaboten.craftengine.CraftEngine;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CraftEngineMenu extends Screen{

    private final Screen parentScreen;
    private scrollableWidget scrollableSection;
    private final List<AbstractWidget> CommandListWidgets = new ArrayList<>();
    private boolean isPause = false;
    private EditBox editBox;
    JSONObject commands = CraftEngine.COMMANDS;
    private final List<CommandButton> varCommands = new ArrayList<>();

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

    @Override
    public void render (@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        scrollableSection.filter = editBox.getValue();
        if(hasShiftDown()){
            for(AbstractWidget widget : CommandListWidgets){
                if(widget instanceof CommandButton commandButton&&widget.isHovered()){
                    saveCommand(commandButton);
                }
            }

            System.out.println(varCommands.size());
            for(int i=0;i<varCommands.size();i++){
                if(!varCommands.isEmpty()&&varCommands.get(i).isMouseOver(mouseX, mouseY)){
                    removeWidget(varCommands.get(i));
                    varCommands.removeAll(List.of(varCommands.get(i)));
                }
            }
        }
    }

    private void initInfoString() {

        StringBuilder builder = new StringBuilder();
        assert Minecraft.getInstance().player != null;
        builder.append(Minecraft.getInstance().player.getName().getString());
        builder.append(" ");
        builder.append("Permission Level: ");
        builder.append(Minecraft.getInstance().player.getPermissionLevel());

        TextWidget textWidget = new TextWidget(5, 85, this.width - 170, Component.literal(builder.toString()));
        SpacerWidget spacerWidget = new SpacerWidget(5, 95, 1, this.width-10, 0xFFFFFFFF);

        addRenderableWidget(textWidget);
        addRenderableWidget(spacerWidget);
    }

    private void initScrollSection() {

        scrollableSection = new scrollableWidget(Minecraft.getInstance(), this.width - 150,this.height-100 - 8 - 9, 100, 145, CommandListWidgets);

        if (scrollableSection.contentHeight() < scrollableSection.getHeight()){
            scrollableSection = new scrollableWidget(Minecraft.getInstance(), this.width - 150, scrollableSection.contentHeight() + 4, 100, 145, CommandListWidgets);
        }

        SpacerWidget spacerWidget = new SpacerWidget(5, scrollableSection.getHeight() + scrollableSection.getTop() + 5, 1, this.width-10, 0xFFFFFFFF);
        TextWidget textWidget = new TextWidget(5, scrollableSection.getHeight() + scrollableSection.getTop() + 5 + 1 + 2, this.width - 10, Component.literal("v"+commands.getJSONObject("Info").getDouble("Version")));

        addRenderableWidget(textWidget);
        addRenderableWidget(scrollableSection);
        addRenderableWidget(spacerWidget);

    }

    public void saveCommand(CommandButton b){

        CommandButton commandButton = new CommandButton(b.getCommand(), 5, 100,  145 - 10, 20, b.getDisplay(), b.getDescription());

        if(varCommands.stream().anyMatch(btn -> btn.getCommand().equals(commandButton.getCommand())) ||varCommands.size()>3){
            return;
        }

        int[] vals = new int[]{100+25*3, 100+25*2, 100+25, 100};
        varCommands.add(commandButton);
        addRenderableWidget(commandButton);
        for(CommandButton button : varCommands){
            for(int i=0; i<vals.length;i++){
                if(button.getY()==vals[i]){
                    vals[i] = 0;
                }
            }
        }
        for(int i = 0; i < vals.length ; i++) {
            if(vals[i]!=0){
                commandButton.setY(vals[i]);
            }
        }
    }

    private void initTopContainer() {

        this.addRenderableWidget(Button.builder(Component.literal("←"), button -> Minecraft.getInstance().setScreen(parentScreen)).bounds( 5, 5, 17, 18).build());
        MultiLineTextWidget title = new MultiLineTextWidget(0, 10, Component.literal("Craft Engine"), Minecraft.getInstance().font);
        Checkbox checkbox = Checkbox.builder(Component.literal("Pause"), Minecraft.getInstance().font).pos(5, 25+2).onValueChange( (valChange, is) -> isPause = is).selected(isPause).build();
        editBox = new EditBox(Minecraft.getInstance().font,  145,78, this.width - 150, 15, Component.literal("Search..."));
        title.setX(this.width / 2 - title.getWidth() / 2);

        this.addRenderableWidget(editBox);
        this.addRenderableWidget(title);
        this.addRenderableWidget(checkbox);
    }

    private void fillButtonsList() {

        JSONObject commands = CraftEngine.COMMANDS.getJSONObject("Commands");
        boolean first = true;

        for (String category : commands.keySet()) {

            if (!first) {
                CommandListWidgets.add(new SpacerWidget(0, 0, 10, 0, 0x00000000));
            }

            CommandListWidgets.add(new TextWidget(0, 0, 0, Component.literal(category)));
            first = false;
            JSONArray commandList = commands.getJSONArray(category);

            for (int i = 0; i < commandList.length(); i++) {
                JSONObject command = commandList.getJSONObject(i);
                CommandListWidgets.add(new CommandButton(command.getString("Command"), 0, 0, 0, 20, command.getString("Display"), command.getString("Info")));
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollableSection != null && scrollableSection.isMouseOver(mouseX, mouseY)) {
            return scrollableSection.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean isPauseScreen() {
        return isPause;
    }
}
