package net.pizzaboten.craftengine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(CraftEngine.MOD_ID)
public class CraftEngine
{
    public static final String MOD_ID = "craftengine";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CraftEngine()
    {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLClientSetupEvent event)
    {
        LOGGER.info("Common setup complete!");
        MinecraftForge.EVENT_BUS.register(new PauseScreenInjector());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("Server starting!");
    }

    public class PauseScreenInjector
    {
        @SubscribeEvent
        public void onGuiInit(net.minecraftforge.client.event.ScreenEvent.Init.Post event)
        {
            if (event.getScreen() instanceof PauseScreen)
            {
                PauseScreen screen = (PauseScreen) event.getScreen();
                event.addListener(Button.builder(Component.literal("Craft Engine"), button -> {
                    Minecraft.getInstance().setScreen(new CraftEngineMenu(screen));
                }).bounds(screen.width / 2 - 100, screen.height / 4 + 150, 200, 20).build());
            }
        }
    }

    public class CraftEngineMenu extends Screen
    {
        private final Screen parentScreen;

        protected CraftEngineMenu(Screen parentScreen)
        {
            super(Component.literal("Craft Engine Menu"));
            this.parentScreen = parentScreen;
        }

        @Override
        protected void init()
        {
            this.addRenderableWidget(Button.builder(Component.literal("Return"), button -> {
                Minecraft.getInstance().setScreen(parentScreen);
            }).bounds(this.width / 2 - 98, this.height / 4 + 120, 202, 20).build());
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
            return false;
        }
    }

}
