package blueduck.outerend.features;

import blueduck.outerend.OuterEndMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureRegistry {
	public static final ConfiguredFeature<NoFeatureConfig,AzureTreeFeature> AZURE_TREE =
			(ConfiguredFeature<NoFeatureConfig, AzureTreeFeature>) newConfiguredFeature(
					"azure_tree",
					newFeature(
							"azure_tree",
							new AzureTreeFeature(NoFeatureConfig.field_236558_a_)
					).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
							.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
							.withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 1, 2)))
			);
	
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
