package blueduck.outerend.mixin_code;

import net.minecraft.world.gen.SimplexNoiseGenerator;

import java.util.Random;

public class MixinHelpers {
	private static SimplexNoiseGenerator generator = new SimplexNoiseGenerator(new Random());
	
	public static void resetGenerator(long seed) {
		generator = new SimplexNoiseGenerator(new Random(seed));
	}
	
	public static double get(int x, int z) {
		return generator.getValue(x,z) * 30;
	}
}
