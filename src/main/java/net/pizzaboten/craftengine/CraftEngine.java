package net.pizzaboten.craftengine;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.pizzaboten.craftengine.Injectors.CraftEngineScreenInjector;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import org.json.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Mod(CraftEngine.MOD_ID)
public class CraftEngine {
    public static final String MOD_ID = "craftengine";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final JSONObject COMMANDS = LoadCommands();

    public CraftEngine() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }


        private static JSONObject LoadCommands() {
            JSONObject json = new JSONObject("{error: 'Error loading commands'}");
            try {
                InputStream is = CraftEngine.class.getClassLoader().getResourceAsStream("Commands.json");

                if (is != null) {
                    String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8);
                    json = new JSONObject(jsonTxt);
                } else {
                    throw new RuntimeException("File not found in resources folder: Commands.json");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }



    private void commonSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Common setup complete!");
        MinecraftForge.EVENT_BUS.register(new CraftEngineScreenInjector());
    }

    public static void executeCommand(String command) {
        MinecraftServer serverPlayer = ServerLifecycleHooks.getCurrentServer();
        LOGGER.info(serverPlayer.name());
        CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withPermission(4);

        CommandDispatcher<CommandSourceStack> commanddispatcher = serverPlayer.getCommands().getDispatcher();
        ParseResults<CommandSourceStack> results = commanddispatcher.parse(command, commandSourceStack);
        serverPlayer.getCommands().performCommand(results, command);
    }


}
