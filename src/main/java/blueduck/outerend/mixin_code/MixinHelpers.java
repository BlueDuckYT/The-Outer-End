package blueduck.outerend.mixin_code;

import blueduck.outerend.registry.BiomeRegistry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.SimplexNoiseGenerator;

import java.util.Random;

public class MixinHelpers {
	//Secondary generator
	public static SimplexNoiseGenerator generator = new SimplexNoiseGenerator(new Random());
	//Actual end generator
	public static SimplexNoiseGenerator generator1 = new SimplexNoiseGenerator(new Random());
	
	public static double get(float x, float z) {
		return getRandomNoise(generator,x,z);
	}
	
	public static void resetGenerator(long seed) {
		generator = new SimplexNoiseGenerator(new Random(seed));
	}
	
	public static Biome getBiome(float x, float z, Biome defaultBiome, Registry<Biome> lookupRegistry) {
		for (Biome b : BiomeRegistry.getBiomes()) {
			if (
					get(x,z) <= BiomeRegistry.getWeightForBiome(b) + BiomeRegistry.getWeightRangeForBiome(b) &&
							get(x,z) >= BiomeRegistry.getWeightForBiome(b) - BiomeRegistry.getWeightRangeForBiome(b)
			) {
				if (lookupRegistry == null) {
					return b;
				} else {
					return lookupRegistry.getOrDefault(b.getRegistryName());
				}
			}
		}
		return defaultBiome;
	}
	
	public static float floatBitShift(int input, int amt) {
		boolean isNegative = amt < 0;
		float out = Math.abs(input) / (float)(amt+1);
		return isNegative?out:-out;
	}
	
	public static float getRandomNoise(SimplexNoiseGenerator noiseGenerator, float x, float z) {
		float i = x / 2;
		float j = z / 2;
		float k = x % 2;
		float l = z % 2;
		float f = 100.0F - MathHelper.sqrt((float)(x * x + z * z)) * 8.0F;
		f = MathHelper.clamp(f, -100.0F, 80.0F);
		
		for(int i1 = -12; i1 <= 12; ++i1) {
			for(int j1 = -12; j1 <= 12; ++j1) {
				long k1 = (long)(i + i1);
				long l1 = (long)(j + j1);
				if (k1 * k1 + l1 * l1 > 4096L && noiseGenerator.getValue((double)k1, (double)l1) < (double)-0.9F) {
					float f1 = (MathHelper.abs((float)k1) * 3439.0F + MathHelper.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
					float f2 = (float)(k - i1 * 2);
					float f3 = (float)(l - j1 * 2);
					float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
					f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
					f = Math.max(f, f4);
				}
			}
		}
		
		return f;
	}
}
