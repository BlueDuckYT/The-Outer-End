package blueduck.outerend.client;

import blueduck.outerend.client.entity.renderer.DragonflyEntityRenderer;
import blueduck.outerend.registry.BlockRegistry;
import blueduck.outerend.registry.EntityRegistry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
	public static void onSetup(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_SAPLING.get(), RenderType.getCutout());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.DRAGONFLY.get(), DragonflyEntityRenderer::new);
	}
	
	public static void renderDebugEntityPathfinding(RenderWorldLastEvent event) {
//		MatrixStack stack = new MatrixStack();
//		stack.push();
//		stack.translate(
//				-Minecraft.getInstance().getRenderManager().info.getProjectedView().x,
//				-Minecraft.getInstance().getRenderManager().info.getProjectedView().y,
//				-Minecraft.getInstance().getRenderManager().info.getProjectedView().z
//		);
		RenderSystem.pushMatrix();
		RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getPitch(),1,0,0);
		RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getYaw()+180,0,1,0);
		Minecraft.getInstance().debugRenderer.pathfinding.render(
				new MatrixStack(),
				Minecraft.getInstance().getRenderTypeBuffers().getBufferSource(),
				Minecraft.getInstance().getRenderManager().info.getProjectedView().x,
				Minecraft.getInstance().getRenderManager().info.getProjectedView().y,
				Minecraft.getInstance().getRenderManager().info.getProjectedView().z
		);
		RenderSystem.popMatrix();
	}
}
