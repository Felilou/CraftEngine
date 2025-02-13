package net.pizzaboten.craftengine.Screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pizzaboten.craftengine.FileHandler;
import net.pizzaboten.craftengine.widgets.*;
import net.pizzaboten.craftengine.CraftEngine;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftEngineMenu extends Screen{

    private final Screen parentScreen;
    private scrollableWidget scrollableSection;
    private final List<AbstractWidget> CommandListWidgets = new ArrayList<>();
    private boolean isPause = false;
    private EditBox editBox;
    JSONObject commands = CraftEngine.COMMANDS;
    private final List<CommandButton> varCommands = new ArrayList<>();
    private final FileHandler fileHandler = new FileHandler();

    public CraftEngineMenu(Screen parentScreen) throws IOException {
        super(Component.literal("Craft Engine"));
        this.parentScreen = parentScreen;
        fillButtonsList();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(hasShiftDown()){
            for(AbstractWidget widget : CommandListWidgets){
                if(widget instanceof CommandButton commandButton&&widget.isHovered()){
                    saveCommand(commandButton);
                }
            }
            for(int i=0;i<varCommands.size();i++){
                if(varCommands.get(i).isMouseOver(mouseX, mouseY)){
                    fileHandler.deleteCommand(varCommands.get(i).getCommand());
                    varCommands.removeAll(List.of(varCommands.get(i)));
                }
            }
            System.out.println(Arrays.toString(fileHandler.getCommands()));
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void init() {
        initTopContainer();
        initScrollSection();
    }

    @Override
    public void render (@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        scrollableSection.filter = editBox.getValue();
        varCommands.forEach(commandButton -> commandButton.render(guiGraphics, mouseX, mouseY, partialTicks));
        guiGraphics.drawString(Minecraft.getInstance().font, "Quick Access", 5, 102, 0xFFFFFFFF, false);
        guiGraphics.fill(0, 0, 0, 0, 0);
    }

    private void initScrollSection() {

        scrollableSection = new scrollableWidget(Minecraft.getInstance(), this.width - 150,this.height-100, 100, 145, CommandListWidgets);

        if (scrollableSection.contentHeight() < scrollableSection.getHeight()){
            scrollableSection = new scrollableWidget(Minecraft.getInstance(), this.width - 150, scrollableSection.contentHeight() + 4, 100, 145, CommandListWidgets);
        }

        addRenderableWidget(scrollableSection);

    }

    public void saveCommand(CommandButton b){

        CommandButton commandButton = new CommandButton(b.getCommand(), 5, 100,  145 - 10, 20, b.getDisplay(), b.getDescription());

        if(varCommands.stream().anyMatch(btn -> btn.getCommand().equals(commandButton.getCommand()))||varCommands.size()>4){
            return;
        }

        int[] vals = new int[]{90+25*5, 90+25*4, 90+25*3, 90+25*2, 90+25};
        varCommands.add(commandButton);
        for(int val : vals){
            boolean used = false;
            for(CommandButton btn : varCommands){
                if(btn.getY() == val+3){
                    used = true;
                    break;
                }
            }
            if(!used){
                commandButton.setY(val+3);
                break;
            }
        }
        fileHandler.saveCommand(commandButton.getCommand());
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

        String[] commandss = fileHandler.getCommands();
        for(String command : commandss){
            for(AbstractWidget button : CommandListWidgets) {
                if(button instanceof CommandButton && ((CommandButton) button).getCommand().equals(command)){
                    saveCommand((CommandButton) button);
                }
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