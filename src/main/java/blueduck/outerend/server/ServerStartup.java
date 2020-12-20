package blueduck.outerend.server;

import blueduck.outerend.mixin_code.MixinHelpers;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ServerStartup {
	public static void onServerStarting(FMLServerStartingEvent event) {
		MixinHelpers.resetGenerator(
				event.getServer().getWorld(World.THE_END).getSeed()
		);
	}
}
