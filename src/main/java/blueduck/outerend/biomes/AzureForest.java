package blueduck.outerend.biomes;

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
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class AzureForest extends OuterEndBiome {

	static final Biome.Climate CLIMATE = new Biome.Climate(Biome.RainType.NONE, 0.8F, Biome.TemperatureModifier.NONE, 0.4F);

	static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

	static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SurfaceRegistry.AZURE_FOREST);

	// 3448555
	public AzureForest() {
		super(CLIMATE, Biome.Category.THEEND, -0.1F, .15F, (new BiomeAmbience.Builder()).withGrassColor(3106678).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(10518688).withSkyColor(0).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).setMusic(SoundRegistry.createEndMusic(SoundRegistry.AZURE_MUSIC.get())).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.copy());
	}

	static {
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureRegistry.AZURE_TREE);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.AZURE_BERRY_VINE_DECORATOR);
		// GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
		// FeatureRegistry.END_GRASS_DECORATOR);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.END_FOLIAGE_DECORATOR);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.CHORUS_PLANT);
		GENERATION_SETTINGS.withStructure(StructureFeatures.END_CITY);

		SPAWN_SETTINGS.withCreatureSpawnProbability(5);
		SPAWN_SETTINGS.withSpawnCost(EntityType.ENDERMAN, 1, 40);
		SPAWN_SETTINGS.withSpawnCost(EntityRegistry.DRAGONFLY.get(), 1, 30);
		SPAWN_SETTINGS.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityRegistry.DRAGONFLY.get(), 1, 1, 3));
		SPAWN_SETTINGS.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 1, 3));
	}
}
