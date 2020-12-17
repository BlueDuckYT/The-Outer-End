package blueduck.outerend.biomes;

import blueduck.outerend.features.FeatureRegistry;
import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class AzureForest extends OuterEndBiome {
    
    static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, "outer_end:azure_forest", new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(BlockRegistry.AZURE_GRASS.get().getDefaultState(), Blocks.END_STONE.getDefaultState(), BlockRegistry.AZURE_GRASS.get().getDefaultState())));
    static final Biome.Climate CLIMATE = new Biome.Climate(Biome.RainType.NONE, 0.8F, Biome.TemperatureModifier.NONE, 0.4F);
    
    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    
    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SURFACE_BUILDER);
    
    //3448555
    public AzureForest() {
        super(CLIMATE, Biome.Category.THEEND, -0.1F, .15F, (new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(10518688).withSkyColor(0).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).setMusic(BackgroundMusicTracks.END_MUSIC).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.copy());
    }
    
    static {
//        GENERATION_SETTINGS.withSurfaceBuilder()
        
        DefaultBiomeFeatures.withOverworldOres(GENERATION_SETTINGS);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.AZURE_TREE);
    }
}
