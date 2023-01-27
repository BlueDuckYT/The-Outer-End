package blueduck.outerend;

import blueduck.outerend.blocks.CrystalBudBlock;
import blueduck.outerend.client.ClientSetup;
import blueduck.outerend.common.CommonSetup;
import blueduck.outerend.config.ConfigHelper;
import blueduck.outerend.config.OuterEndConfig;
import blueduck.outerend.features.ConfiguredStructureFeatures;
import blueduck.outerend.items.OuterEndSpawnEgg;
import blueduck.outerend.registry.*;
import blueduck.outerend.server.BlockEventListener;
import blueduck.outerend.server.EntityEventListener;
import blueduck.outerend.server.ServerStartup;
import com.teamabnormals.blueprint.core.registry.BoatRegistry;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.sun.jna.Structure;
import net.minecraft.entity.EntityClassification;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

//import blueduck.outerend.client.DebugRenderer;

@Mod("outer_end")
public class OuterEndMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static String MODID = "outer_end";

    public static OuterEndConfig CONFIG;
    public static RegistryHelper HELPER = new RegistryHelper("outer_end");

    public static ArrayList<UUID> DEVS = new ArrayList<UUID>();


    public OuterEndMod() {

        CONFIG = ConfigHelper.register(ModConfig.Type.COMMON, OuterEndConfig::new);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonSetup::onCommonSetup);

        MinecraftForge.EVENT_BUS.addListener(ServerStartup::onServerStarting);
        MinecraftForge.EVENT_BUS.addListener(EntityEventListener::onBonemeal);
        MinecraftForge.EVENT_BUS.addListener(BlockEventListener::onFluidChangeBlock);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, this::biomeModification);

        
        BiomeRegistry.init();
        BlockRegistry.init();
        EntityRegistry.init();
        ItemRegistry.init();
        StructureRegistry.init();
        SoundRegistry.init();
        
        
        if (FMLEnvironment.dist.isClient()) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onSetup);
            
            DEVS.add(UUID.fromString("380df991-f603-344c-a090-369bad2a924a"));
            DEVS.add(UUID.fromString("c88a2cce-333f-450d-be8b-374c216ad4b5"));
            DEVS.add(UUID.fromString("d2b21209-ffe6-47f0-b86a-b13d40eeebc2"));
            DEVS.add(UUID.fromString("5a359e55-da1d-4c81-83d3-f9b63b9e59c7"));
            DEVS.add(UUID.fromString("0bd28a4b-2666-428b-8c04-2dff8a4c8fd9"));
            DEVS.add(UUID.fromString("631b7d0b-23c5-4341-bcc1-87fc02f8840a"));
            DEVS.add(UUID.fromString("264767a2-6a9b-4a35-9b3a-89c855164850"));
            DEVS.add(UUID.fromString("b0242a0f-e172-45f2-a79e-9fa14494bba9"));
            DEVS.add(UUID.fromString("3e7e37bd-95de-43c1-9ee4-b3b63dbdf66f"));
            DEVS.add(UUID.fromString("cf8bf0da-f86d-4ac0-b48a-3f6d940f6472"));
            DEVS.add(UUID.fromString("b62ad4bd-b2eb-47cb-b88b-564fc8ffb50f"));
            DEVS.add(UUID.fromString("3842d959-2871-4eb9-bcb9-aa0e35b36f2a"));
        }
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            StructureRegistry.setupStructures();
            ConfiguredStructureFeatures.registerConfiguredStructures();
            BlockRegistry.registerFlammables();
            ProcessorRegistry.registerProcessors();
        });

        BoatRegistry.registerBoat("outer_end:azure", ItemRegistry.AZURE_BOAT, BlockRegistry.AZURE_PLANKS);


        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;

        pot.addPlant(new ResourceLocation("outer_end:azure_bud"), BlockRegistry.POTTED_AZURE_BUD);
        pot.addPlant(new ResourceLocation("outer_end:ender_roots"), BlockRegistry.POTTED_ENDER_ROOTS);
        pot.addPlant(new ResourceLocation("outer_end:azure_sprouts"), BlockRegistry.POTTED_AZURE_SPROUTS);

        CrystalBudBlock.CRYSTAL_MAP.put(BlockRegistry.ROSE_CRYSTAL_BUD.get(), BlockRegistry.ROSE_CRYSTAL.get());
        CrystalBudBlock.CRYSTAL_MAP.put(BlockRegistry.MINT_CRYSTAL_BUD.get(), BlockRegistry.MINT_CRYSTAL.get());
        CrystalBudBlock.CRYSTAL_MAP.put(BlockRegistry.COBALT_CRYSTAL_BUD.get(), BlockRegistry.COBALT_CRYSTAL.get());
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
        if (event.getCategory().equals(Biome.Category.THEEND) && !event.getName().equals(new ResourceLocation("minecraft:the_end"))) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructureFeatures.CONFIGURED_END_TOWER);
            event.getGeneration().getStructures().add(() -> ConfiguredStructureFeatures.CONFIGURED_CATACOMBS);

            event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.PURPUR_GOLEM.get(), 3, 1, 1));

        }

        if (event.getName().equals(new ResourceLocation("minecraft:end_highlands"))) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.CHORUS_SQUID.get(), 8, 1, 1));
            event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.STALKER.get(), 8, 1, 1));

        }

        if (event.getName().equals(new ResourceLocation("outer_end:crystal_crag"))) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.STALKER.get(), 1, 1, 4));

        }
        
        if (event.getName().equals(new ResourceLocation("minecraft:small_end_islands"))) {
            event.getGeneration().getFeatures(Decoration.RAW_GENERATION).add(() -> {
            	return FeatureRegistry.CRAG_MOON_OUTSIDE_FEATURE;
            });
        }
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


            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.getDimensionKey().equals(World.OVERWORLD)){
                return;
            }

            if (serverWorld.getDimensionKey().getLocation().equals(new ResourceLocation("minecraft:the_end"))) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
               tempMap.put(StructureRegistry.END_TOWER.get(), DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.END_TOWER.get()));
                tempMap.put(StructureRegistry.CATACOMBS.get(), DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.CATACOMBS.get()));
               serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
}
       }
    }

    @Mod.EventBusSubscriber(modid = "outer_end", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEventBusSubscriber {
        @SubscribeEvent
        public void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
            OuterEndSpawnEgg.doDispenserSetup();
        }
        @SubscribeEvent
        public void onItemColorEvent(ColorHandlerEvent.Item event) {
           for (final SpawnEggItem egg : OuterEndSpawnEgg.OUTER_END_SPAWN_EGGS) {
                event.getItemColors().register((stack, i) -> egg.getColor(i), egg);
            }
        }
    }
    @Mod.EventBusSubscriber(modid = "outer_end")
    public static class LootEvents {

        @SubscribeEvent
        public static void onLootLoad(LootTableLoadEvent event) throws IllegalAccessException {

            ResourceLocation name = event.getName();

           if (name.equals(new ResourceLocation("minecraft", "chests/end_city_treasure"))) {
                LootPool pool = event.getTable().getPool("main");
                if (pool != null) {
                    addEntry(pool, getInjectEntry(new ResourceLocation("outer_end:chests/end_city_treasure"), 10, 0));
                }
            }


        }
    }

    private static LootEntry getInjectEntry(ResourceLocation location, int weight, int quality) {
        return TableLootEntry.builder(location).weight(weight).quality(quality).build();
    }



    private static void addEntry(LootPool pool, LootEntry entry) throws IllegalAccessException {
       List<LootEntry> lootEntries = (List<LootEntry>) ObfuscationReflectionHelper.findField(LootPool.class, "field_186453_a").get(pool);
       if (lootEntries.stream().anyMatch(e -> e == entry)) {
            throw new RuntimeException("Attempted to add a duplicate entry to pool: " + entry);
        }
        lootEntries.add(entry);
    }
}
