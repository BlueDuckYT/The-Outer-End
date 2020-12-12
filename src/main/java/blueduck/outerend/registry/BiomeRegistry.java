package blueduck.outerend.registry;

import blueduck.outerend.biomes.BiomeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

public class BiomeRegistry {
	private static final HashMap<ResourceLocation, Biome> outer_end_biomes = new HashMap<>();
	private static final HashMap<Biome, Float> outer_end_biomes_weights = new HashMap<>();
	
	@SubscribeEvent
	public static void registerEvent(RegistryEvent.Register<Biome> event) {
		register(new BiomeWrapper(new ResourceLocation("outer_ends:test_biome")).withCategory(Biome.Category.THEEND).build(),new ResourceLocation("outer_ends:test_biome"),10f,event);
	}
	
	public static Biome[] getBiomes() {
		return outer_end_biomes.values().toArray(new Biome[0]);
	}
	
	public static float getWeightForBiome(Biome biome) {
		return outer_end_biomes_weights.get(biome);
	}
	
	private static void register(Biome biome, ResourceLocation registryName, float weight, RegistryEvent.Register<Biome> event) {
		event.getRegistry().register(biome);
		outer_end_biomes.put(registryName, ForgeRegistries.BIOMES.getValue(registryName));
		outer_end_biomes_weights.put(getBiomes()[outer_end_biomes_weights.size()],weight);
	}
}