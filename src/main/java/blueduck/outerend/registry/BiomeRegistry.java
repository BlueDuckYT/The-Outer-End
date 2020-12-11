package blueduck.outerend.registry;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BiomeRegistry {
	@SubscribeEvent
	public static void registerEvent(RegistryEvent.Register<Biome> event) {
//		event.getRegistry().register();
	}
}