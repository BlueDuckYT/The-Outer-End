package blueduck.outerend.biomes;

import blueduck.outerend.client.Color;
import blueduck.outerend.registry.BlockRegistry;
import blueduck.outerend.registry.EntityRegistry;
import blueduck.outerend.registry.FeatureRegistry;
import blueduck.outerend.registry.SoundRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class CrystalCrag extends OuterEndBiome {

    static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, "outer_end:crystal_crag", new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(BlockRegistry.VIOLITE.get().getDefaultState(), BlockRegistry.VIOLITE.get().getDefaultState(), BlockRegistry.VIOLITE.get().getDefaultState())));
    static final Biome.Climate CLIMATE = new Biome.Climate(Biome.RainType.NONE, 0.8F, Biome.TemperatureModifier.NONE, 0.4F);

    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SURFACE_BUILDER);

    //3448555
    public CrystalCrag() {
        super(CLIMATE, Biome.Category.THEEND, -0.1F, .15F, (new BiomeAmbience.Builder()).withGrassColor(new Color(81,91,135).getRGB()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(10518688).withSkyColor(0).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).setMusic(new BackgroundMusicSelector(SoundRegistry.AZURE_MUSIC.get(), 200, 2000, false)).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.copy());
    }
    
    static {
//        GENERATION_SETTINGS.withSurfaceBuilder()

        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.CRYSTAL_SPIKE_FEATURE);

        SPAWN_SETTINGS.withCreatureSpawnProbability(5);
        SPAWN_SETTINGS.withSpawnCost(EntityType.ENDERMAN,1,40);
        SPAWN_SETTINGS.withSpawnCost(EntityRegistry.DRAGONFLY.get(),1,30);
        SPAWN_SETTINGS.withSpawner(EntityClassification.CREATURE,new MobSpawnInfo.Spawners(EntityRegistry.DRAGONFLY.get(),1,1,3));
        SPAWN_SETTINGS.withSpawner(EntityClassification.MONSTER,new MobSpawnInfo.Spawners(EntityType.ENDERMAN,1,1,3));
    }
}
