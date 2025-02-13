package net.pizzaboten.craftengine.Injectors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.pizzaboten.craftengine.Screens.CraftEngineMenu;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CraftEngineScreenInjector
{
    @SubscribeEvent
    public void onGuiInit(net.minecraftforge.client.event.ScreenEvent.Init.Post event)
    {
        if (event.getScreen() instanceof PauseScreen screen)
        {
            event.addListener(Button.builder(Component.literal("Commands"), button -> {
                try {
                    Minecraft.getInstance().setScreen(new CraftEngineMenu(screen));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).bounds(screen.width / 2 - 102, screen.height / 4 + 146, 204, 20).build());
        }
    }
}
