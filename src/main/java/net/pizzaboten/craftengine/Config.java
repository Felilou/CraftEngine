package net.pizzaboten.craftengine;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CraftEngine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
   @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {}
}
