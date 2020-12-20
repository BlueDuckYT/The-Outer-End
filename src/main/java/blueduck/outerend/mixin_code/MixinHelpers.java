package blueduck.outerend.mixin_code;

import blueduck.outerend.registry.BiomeRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.SimplexNoiseGenerator;

import java.util.ArrayList;
import java.util.Random;

public class MixinHelpers {
	//Secondary generator
	public static PerlinNoiseGenerator generatorPerlin = new PerlinNoiseGenerator(new SharedSeedRandom(new Random().nextLong()), ImmutableList.of(0));
	public static SimplexNoiseGenerator generator = new SimplexNoiseGenerator(new Random());
	//Actual end generator
	public static SimplexNoiseGenerator generator1 = new SimplexNoiseGenerator(new Random());
	//Trinary generator
	public static SimplexNoiseGenerator generator2 = new SimplexNoiseGenerator(new Random());
	
	public static double get(float x, float z) {
		return
				((MixinHelpers.getRandomNoise(MixinHelpers.generator1, x, z)/2f)) +
						(MixinHelpers.getRandomNoise(null, x, z)/2f);
	}
	
	public static void resetGenerator(long seed) {
		ArrayList<Integer> numbers = new ArrayList<>();
		Random r = new Random(seed);
		for (int i=0;i<=new Random(seed).nextInt(4)+3;i++)
			numbers.add(r.nextInt(5));
		generatorPerlin = new PerlinNoiseGenerator(new SharedSeedRandom(seed),ImmutableList.copyOf(numbers));
		
		generator = new SimplexNoiseGenerator(new Random(seed));
		generator2 = new SimplexNoiseGenerator(new Random(new Random(seed).nextLong()));
	}
	
	public static Biome getBiome(float x, float z, Biome defaultBiome, Registry<Biome> lookupRegistry) {
		for (Biome b : BiomeRegistry.getBiomes()) {
			double noise = get(x + BiomeRegistry.getWeightRangeForBiome(b),z + BiomeRegistry.getWeightForBiome(b));
			if (
					noise <= BiomeRegistry.getWeightForBiome(b) + BiomeRegistry.getWeightRangeForBiome(b) &&
							noise >= BiomeRegistry.getWeightForBiome(b) - BiomeRegistry.getWeightRangeForBiome(b)
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
		float out = (input) / (float)(amt+1);
//		return isNegative?out:-out;
		return out;
	}
	
	public static float getRandomNoise(SimplexNoiseGenerator noiseGenerator, float x, float z) {
		if (noiseGenerator == null) {
			return (((float) generator.func_227464_a_(
					x/100f,z/100f,
					generator2.getValue(x/100f,z/100f)/1/*,
					generator2.getValue(x,z)*/
			))*200f);
		} else {
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
}
