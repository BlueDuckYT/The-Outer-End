package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class SurfaceRegistry {

	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> CRYSTAL_CRAG = newConfiguredSurfaceBuilder("crystal_crag_surface", SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(BlockRegistry.VIOLITE.get().getDefaultState(), BlockRegistry.VIOLITE.get().getDefaultState(), BlockRegistry.VIOLITE.get().getDefaultState())));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> AZURE_FOREST = newConfiguredSurfaceBuilder("azure_forest_surface", SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(BlockRegistry.AZURE_GRASS.get().getDefaultState(), Blocks.END_STONE.getDefaultState(), BlockRegistry.AZURE_GRASS.get().getDefaultState())));

	private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> newConfiguredSurfaceBuilder(String registryName, ConfiguredSurfaceBuilder<SC> configuredFeature) {
		Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(OuterEndMod.MODID, registryName), configuredFeature);
		return configuredFeature;
	}

	private static <SC extends ISurfaceBuilderConfig> SurfaceBuilder<SC> newSurfaceBuilder(String registryName, SurfaceBuilder<SC> configuredFeature) {
		configuredFeature.setRegistryName(new ResourceLocation(OuterEndMod.MODID, registryName));
		ForgeRegistries.SURFACE_BUILDERS.register(configuredFeature);
		return configuredFeature;
	}
}
