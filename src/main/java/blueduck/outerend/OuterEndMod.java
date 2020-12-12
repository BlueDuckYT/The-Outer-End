package blueduck.outerend;

import blueduck.outerend.registry.BiomeRegistry;
import blueduck.outerend.server.ServerStartup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("outer_end")
public class OuterEndMod
{
    private static final Logger LOGGER = LogManager.getLogger();

    public OuterEndMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    
        FMLJavaModLoadingContext.get().getModEventBus().register(BiomeRegistry.class);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ServerStartup::onServerStarting);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }
}
