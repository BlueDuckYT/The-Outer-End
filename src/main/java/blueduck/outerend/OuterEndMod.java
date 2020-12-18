package blueduck.outerend;

import blueduck.outerend.client.ClientSetup;
import blueduck.outerend.common.CommonSetup;
import blueduck.outerend.features.ConfiguredStructureFeatures;
import blueduck.outerend.registry.BiomeRegistry;
import blueduck.outerend.registry.BlockRegistry;
import blueduck.outerend.registry.EntityRegistry;
import blueduck.outerend.registry.ItemRegistry;
import blueduck.outerend.registry.StructureRegistry;
import blueduck.outerend.server.ServerStartup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod("outer_end")
public class OuterEndMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static String MODID = "outer_end";
    public OuterEndMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    
        //FMLJavaModLoadingContext.get().getModEventBus().register(BiomeRegistry.class);
        MinecraftForge.EVENT_BUS.addListener(ServerStartup::onServerStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonSetup::onCommonSetup);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, this::biomeModification);

        BiomeRegistry.init();
        BlockRegistry.init();
        EntityRegistry.init();
        ItemRegistry.init();
        StructureRegistry.init();

        if (FMLEnvironment.dist.isClient()) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onSetup);
            if (!FMLEnvironment.production) {
                MinecraftForge.EVENT_BUS.addListener(ClientSetup::renderDebugEntityPathfinding);
            }
        }
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            StructureRegistry.setupStructures();
            ConfiguredStructureFeatures.registerConfiguredStructures();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        // Add our structure to all biomes including other modded biomes.
        // You can skip or add only to certain biomes based on stuff like biome category,
        // temperature, scale, precipitation, mod id, etc. All kinds of options!
        //
        // You can even use the BiomeDictionary as well! To use BiomeDictionary, do
        // RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
        // registrykey. Then that can be fed into the dictionary to get the biome's types.
        event.getGeneration().getStructures().add(() -> ConfiguredStructureFeatures.CONFIGURED_END_TOWER);
    }

    /**
     * Will go into the world's chunkgenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure.
     * (Don't forget to attempt to remove your structure too from
     *  the map if you are blacklisting that dimension! It might have
     *  your structure in it already.)
     *
     * Basically use this to make absolutely sure the chunkgenerator
     * can or cannot spawn your structure.
     */
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            // Prevent spawning our structure in Vanilla's superflat world as
            // people seem to want their superflat worlds free of modded structures.
            // Also that vanilla superflat is really tricky and buggy to work with in my experience.
            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.getDimensionKey().equals(World.OVERWORLD)){
                return;
            }

            if (serverWorld.getDimensionKey().equals(new ResourceLocation("minecraft:end"))) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
                tempMap.put(StructureRegistry.END_TOWER.get(), DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.END_TOWER.get()));
                serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
            }
        }
    }
}
