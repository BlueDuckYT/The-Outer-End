package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.biomes.AzureForest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

public class BiomeRegistry {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, OuterEndMod.MODID);

	public static final RegistryObject<Biome> AZURE_FOREST = BIOMES.register("azure_forest", () -> new AzureForest().getBiome());
	
	private static final HashMap<Biome, Float> OUTER_END_BIOMES_WEIGHT_RANGES = new HashMap<>();
	
	private static final HashMap<ResourceLocation, Biome> OUTER_END_BIOMES = new HashMap<>();
	private static final HashMap<Biome, Float> OUTER_END_BIOME_WEIGHTS = new HashMap<>();

	public static void init() {
		BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid = "outer_end")
	public static class RegistryEvents {
		@SubscribeEvent
		public static void registerEvent(RegistryEvent.Register<Biome> event) {
			register(AZURE_FOREST.get(), AZURE_FOREST.getId(), 60f, 60f, event);
		}
	}
	
	public static Biome[] getBiomes() {
		return OUTER_END_BIOMES.values().toArray(new Biome[0]);
	}
	
	public static float getWeightForBiome(Biome biome) {
		return OUTER_END_BIOME_WEIGHTS.get(biome);
	}
	
	private static void register(Biome biome, ResourceLocation registryName, float weight, float weightRange, RegistryEvent.Register<Biome> event) {
//		event.getRegistry().register(biome);
		OUTER_END_BIOMES.put(registryName, ForgeRegistries.BIOMES.getValue(registryName));
		OUTER_END_BIOME_WEIGHTS.put(getBiomes()[OUTER_END_BIOME_WEIGHTS.size()],weight);
		OUTER_END_BIOMES_WEIGHT_RANGES.put(getBiomes()[OUTER_END_BIOMES_WEIGHT_RANGES.size()],weightRange);
	}
	
	public static float getWeightRangeForBiome(Biome biome) {
		return OUTER_END_BIOMES_WEIGHT_RANGES.get(biome);
	}
}