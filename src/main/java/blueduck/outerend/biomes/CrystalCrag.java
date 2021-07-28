package blueduck.outerend.biomes;

import blueduck.outerend.client.Color;
import blueduck.outerend.registry.EntityRegistry;
import blueduck.outerend.registry.FeatureRegistry;
import blueduck.outerend.registry.SoundRegistry;
import blueduck.outerend.registry.SurfaceRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;

public class CrystalCrag extends OuterEndBiome {

	static final Biome.Climate CLIMATE = new Biome.Climate(Biome.RainType.NONE, 0.8F, Biome.TemperatureModifier.NONE, 0F);

	static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer().withCreatureSpawnProbability(5);

	static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SurfaceRegistry.CRYSTAL_CRAG);

	// 3448555
	public CrystalCrag() {
		super(CLIMATE, Biome.Category.THEEND, -0.1F, .6F, (new BiomeAmbience.Builder()).withGrassColor(new Color(81, 91, 135).getRGB()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(3093087).withSkyColor(3093087).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).setMusic(SoundRegistry.createEndMusic(SoundRegistry.CRYSTAL_CRAG_MUSIC.get())).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.copy());
	}

	static {
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.RAW_GENERATION, FeatureRegistry.VIOLITE_DEAD_RAINBOW_FEATURE);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.RAW_GENERATION, FeatureRegistry.VIOLITE_BUMP_FEATURE);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.RAW_GENERATION, FeatureRegistry.VIOLITE_ROCK_BUMP_FLOWER_FEATURE);

		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureRegistry.CONFIGURED_ROSE_TANGLED_VIOLITE);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureRegistry.CONFIGURED_MINT_TANGLED_VIOLITE);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, FeatureRegistry.CONFIGURED_COBALT_TANGLED_VIOLITE);



		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.CRAG_ROOTS_DECORATOR);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.CRYSTAL_BUDS_DECORATOR);

		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureRegistry.CRYSTAL_SPIKE_FEATURE);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureRegistry.LARGE_CRYSTAL_SPIKE_FEATURE);

		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.RAW_GENERATION, FeatureRegistry.CRAG_MOON_FEATURE);

		SPAWN_SETTINGS.withSpawnCost(EntityType.ENDERMAN, 1, 40);
		SPAWN_SETTINGS.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 1, 3));

		SPAWN_SETTINGS.withSpawnCost(EntityRegistry.STALKER.get(), 1, 5);
		SPAWN_SETTINGS.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.STALKER.get(), 1, 2, 4));
	}

}
