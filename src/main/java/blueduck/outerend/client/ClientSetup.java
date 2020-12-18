package blueduck.outerend.client;

import blueduck.outerend.client.entity.renderer.DragonflyEntityRenderer;
import blueduck.outerend.registry.BlockRegistry;
import blueduck.outerend.registry.EntityRegistry;
import blueduck.outerend.registry.ItemRegistry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ClientSetup {
	public static void onSetup(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_DOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_TRAPDOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.ENDER_ROOTS.get(), RenderType.getCutout());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.DRAGONFLY.get(), DragonflyEntityRenderer::new);
	}
	
	public static void renderDebugEntityPathfinding(RenderWorldLastEvent event) {
		if (!FMLEnvironment.production) {
			if (Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND).getItem().equals(ItemRegistry.DEBUG_TOOL.get())) {
				RenderSystem.pushMatrix();
				RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getPitch(), 1, 0, 0);
				RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getYaw() + 180, 0, 1, 0);
				try {
					Minecraft.getInstance().debugRenderer.pathfinding.render(
							new MatrixStack(),
							Minecraft.getInstance().getRenderTypeBuffers().getBufferSource(),
							Minecraft.getInstance().getRenderManager().info.getProjectedView().x,
							Minecraft.getInstance().getRenderManager().info.getProjectedView().y,
							Minecraft.getInstance().getRenderManager().info.getProjectedView().z
					);
				} catch (Throwable ignored) {
				}
				RenderSystem.popMatrix();
			}
		}
	}
}
