package blueduck.outerend.registry;

import blueduck.outerend.biomes.BiomeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BiomeRegistry {
	@SubscribeEvent
	public static void registerEvent(RegistryEvent.Register<Biome> event) {
		event.getRegistry().register(
				new BiomeWrapper(
						new ResourceLocation("outer_end:test_biome")
				).withCategory(Biome.Category.THEEND).build()
		);
	}
}