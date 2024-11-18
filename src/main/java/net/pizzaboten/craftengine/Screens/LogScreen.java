package net.pizzaboten.craftengine.Screens;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class LogScreen extends Screen {

    protected LogScreen() {
        super(Component.literal("Logs"));
    }

    @Override
    protected void init() {
        loadLogs();
        initLogsInScrollArea();
    }

    private void loadLogs() {

    }

    private void initLogsInScrollArea() {




    }
}
