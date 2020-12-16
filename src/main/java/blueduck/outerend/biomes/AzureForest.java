package blueduck.outerend.biomes;

import blueduck.outerend.features.FeatureRegistry;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public class AzureForest extends OuterEndBiome {

    //static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, "jellyfishing:jellyfish_fields", new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(JellyfishingBlocks.ALGAE_GRASS.get().getDefaultState(), JellyfishingBlocks.CORALSTONE.get().getDefaultState(), JellyfishingBlocks.ALGAE_GRASS.get().getDefaultState())));
    static final Biome.Climate CLIMATE = new Biome.Climate(Biome.RainType.RAIN, 0.8F, Biome.TemperatureModifier.NONE, 0.4F);

    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e );
    //3448555
    public AzureForest() {
        super(CLIMATE, Biome.Category.THEEND, -0.1F, .15F, (new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(10518688).withSkyColor(0).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).setMusic(BackgroundMusicTracks.END_MUSIC).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.copy());
    }
    static {
//        GENERATION_SETTINGS.withSurfaceBuilder()
    
        DefaultBiomeFeatures.withOverworldOres(GENERATION_SETTINGS);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION,FeatureRegistry.AZURE_TREE);




          }
}
