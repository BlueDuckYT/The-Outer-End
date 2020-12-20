package blueduck.outerend.client;

import blueduck.outerend.client.entity.renderer.DragonflyEntityRenderer;
import blueduck.outerend.client.entity.renderer.HimmeliteRenderer;
import blueduck.outerend.registry.BlockRegistry;
import blueduck.outerend.registry.EntityRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
	public static void onSetup(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_DOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_TRAPDOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.ENDER_ROOTS.get(), RenderType.getCutout());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.DRAGONFLY.get(), DragonflyEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HIMMELITE.get(), HimmeliteRenderer::new);
	}
}
