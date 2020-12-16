package blueduck.outerend.mixin_code;

import blueduck.outerend.registry.BiomeRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.gen.SimplexNoiseGenerator;

import java.util.Random;

public class MixinHelpers {
	private static SimplexNoiseGenerator generator = new SimplexNoiseGenerator(new Random());
	
	public static void resetGenerator(long seed) {
		generator = new SimplexNoiseGenerator(new Random(seed));
	}
	
	public static double get(int x, int z) {
		return EndBiomeProvider.getRandomNoise(generator,x,z);
	}
	
	public static Biome getBiome(int x, int z, Biome defaultBiome, Registry<Biome> lookupRegistry) {
		for (Biome b : BiomeRegistry.getBiomes()) {
			if (
					get(x,z) <= BiomeRegistry.getWeightForBiome(b) + BiomeRegistry.getWeightRangeForBiome(b) &&
							get(x,z) >= BiomeRegistry.getWeightForBiome(b) - BiomeRegistry.getWeightRangeForBiome(b)
			) {
				return lookupRegistry.getOrDefault(b.getRegistryName());
			}
		}
		return defaultBiome;
	}
}
