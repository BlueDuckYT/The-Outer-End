package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.features.AzureBerryVineFeature;
import blueduck.outerend.features.AzureTreeFeature;
import blueduck.outerend.features.CragBudDecorator;
import blueduck.outerend.features.CragMoonFeature;
import blueduck.outerend.features.CragPlantDecorator;
import blueduck.outerend.features.CrystalSpikeFeature;
import blueduck.outerend.features.EndFoliageDecorator;
import blueduck.outerend.features.EndGrassDecorator;
import blueduck.outerend.features.LargeCrystalSpikeFeature;
import blueduck.outerend.features.crystalcragsurface.VioliteBumpFeature;
import blueduck.outerend.features.crystalcragsurface.VioliteDeadRainbowFeature;
import blueduck.outerend.features.crystalcragsurface.VioliteRockBumpFlowerFeature;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureRegistry {
	
    public static final ConfiguredFeature<NoFeatureConfig, AzureTreeFeature> AZURE_TREE =
            (ConfiguredFeature<NoFeatureConfig, AzureTreeFeature>) newConfiguredFeature(
                    "azure_tree",
                    newFeature(
                            "azure_tree",
                            new AzureTreeFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 1, 2)))
            );
    
    public static final ConfiguredFeature<NoFeatureConfig, EndGrassDecorator> END_GRASS_DECORATOR =
            (ConfiguredFeature<NoFeatureConfig, EndGrassDecorator>) newConfiguredFeature(
                    "end_grass_decorator",
                    newFeature(
                            "end_grass_decorator",
                            new EndGrassDecorator(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 1, 6)))
            );
    
    public static final ConfiguredFeature<NoFeatureConfig, EndFoliageDecorator> END_FOLIAGE_DECORATOR =
            (ConfiguredFeature<NoFeatureConfig, EndFoliageDecorator>) newConfiguredFeature(
                    "end_foliage_decorator",
                    newFeature(
                            "end_foliage_decorator",
                            new EndFoliageDecorator(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(3, 0.2F, 5)))
            );
    
    public static final ConfiguredFeature<NoFeatureConfig, AzureBerryVineFeature> AZURE_BERRY_VINE_DECORATOR =
            (ConfiguredFeature<NoFeatureConfig, AzureBerryVineFeature>) newConfiguredFeature(
                    "azure_berry_vine_decorator",
                    newFeature(
                            "azure_berry_vine_decorator",
                            new AzureBerryVineFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.85F, 2)))
            );
    
    public static final ConfiguredFeature<NoFeatureConfig, CragPlantDecorator> CRAG_ROOTS_DECORATOR =
            (ConfiguredFeature<NoFeatureConfig, CragPlantDecorator>) newConfiguredFeature(
                    "crag_roots_decorator",
                    newFeature(
                            "crag_roots_decorator",
                            new CragPlantDecorator(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 1, 8)))
            );
    
    public static final ConfiguredFeature<NoFeatureConfig, CragBudDecorator> CRYSTAL_BUDS_DECORATOR =
            (ConfiguredFeature<NoFeatureConfig, CragBudDecorator>) newConfiguredFeature(
                    "crag_buds_decorator",
                    newFeature(
                            "crag_buds_decorator",
                            new CragBudDecorator(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 1, 3)))
            );
    
    public static final ConfiguredFeature<NoFeatureConfig, CrystalSpikeFeature> CRYSTAL_SPIKE_FEATURE =
            (ConfiguredFeature<NoFeatureConfig, CrystalSpikeFeature>) newConfiguredFeature(
                    "crystal_spike_feature",
                    newFeature(
                            "crystal_spike_feature",
                            new CrystalSpikeFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.2F, 2)))
            );

    public static final ConfiguredFeature<NoFeatureConfig, LargeCrystalSpikeFeature> LARGE_CRYSTAL_SPIKE_FEATURE =
            (ConfiguredFeature<NoFeatureConfig, LargeCrystalSpikeFeature>) newConfiguredFeature(
                    "large_crystal_spike_feature",
                    newFeature(
                            "large_crystal_spike_feature",
                            new LargeCrystalSpikeFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.2F, 2)))
            );

    public static final ConfiguredFeature<NoFeatureConfig, VioliteBumpFeature> VIOLITE_BUMP_FEATURE =
            (ConfiguredFeature<NoFeatureConfig, VioliteBumpFeature>) newConfiguredFeature(
                    "violite_bump_feature",
                    newFeature(
                            "violite_bump_feature",
                            new VioliteBumpFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 1, 10)))
            );

    public static final ConfiguredFeature<NoFeatureConfig, VioliteRockBumpFlowerFeature> VIOLITE_ROCK_BUMP_FLOWER_FEATURE =
            (ConfiguredFeature<NoFeatureConfig, VioliteRockBumpFlowerFeature>) newConfiguredFeature(
                    "violite_rock_bump_flower_feature",
                    newFeature(
                            "violite_rock_bump_flower_feature",
                            new VioliteRockBumpFlowerFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)))
            );

    public static final ConfiguredFeature<NoFeatureConfig, VioliteDeadRainbowFeature> VIOLITE_DEAD_RAINBOW_FEATURE =
            (ConfiguredFeature<NoFeatureConfig, VioliteDeadRainbowFeature>) newConfiguredFeature(
                    "violite_dead_rainbow_feature",
                    newFeature(
                            "violite_dead_rainbow_feature",
                            new VioliteDeadRainbowFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1)))
            );
    
    public static final ConfiguredFeature<NoFeatureConfig, CragMoonFeature> CRAG_MOON_FEATURE =
            (ConfiguredFeature<NoFeatureConfig, CragMoonFeature>) newConfiguredFeature(
                    "crag_moon_feature",
                    newFeature(
                            "crag_moon_feature",
                            new CragMoonFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.03F, 1)))
            );

    public static final ConfiguredFeature<NoFeatureConfig, CragMoonFeature> CRAG_MOON_OUTSIDE_FEATURE =
            (ConfiguredFeature<NoFeatureConfig, CragMoonFeature>) newConfiguredFeature(
                    "crag_moon_outside_feature",
                    newFeature(
                            "crag_moon_outside_feature",
                            new CragMoonFeature(NoFeatureConfig.field_236558_a_)
                    ).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01F, 1)))
            );

    public static final ConfiguredFeature<?, ?> CONFIGURED_ROSE_TANGLED_VIOLITE = Feature.ORE.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(BlockRegistry.VIOLITE.get()), BlockRegistry.ROSE_TANGLED_VIOLITE.get().getDefaultState(), 9)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 130)))
            .square()
            .range(250);

    public static final ConfiguredFeature<?, ?> CONFIGURED_MINT_TANGLED_VIOLITE = Feature.ORE.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(BlockRegistry.VIOLITE.get()), BlockRegistry.MINT_TANGLED_VIOLITE.get().getDefaultState(), 9)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 130)))
            .square()
            .range(250);

    public static final ConfiguredFeature<?, ?> CONFIGURED_COBALT_TANGLED_VIOLITE = Feature.ORE.withConfiguration(new OreFeatureConfig(new BlockMatchRuleTest(BlockRegistry.VIOLITE.get()), BlockRegistry.COBALT_TANGLED_VIOLITE.get().getDefaultState(), 9)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 130)))
            .square()
            .range(250);


    
    private static <FC extends IFeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, F> newConfiguredFeature(String registryName, ConfiguredFeature<FC, F> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(OuterEndMod.MODID, registryName), configuredFeature);
        return configuredFeature;
    }

    private static <FC extends IFeatureConfig> Feature<FC> newFeature(String registryName, Feature<FC> configuredFeature) {
        //thank you noeppi_noeppi
        configuredFeature.setRegistryName(new ResourceLocation(OuterEndMod.MODID, registryName));
        ForgeRegistries.FEATURES.register(configuredFeature);
        return configuredFeature;
    }
}
