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
			
//			RenderSystem.pushMatrix();
//			RenderSystem.enableBlend();
//			RenderSystem.defaultBlendFunc();
//			RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
//			RenderSystem.disableTexture();
//			RenderSystem.lineWidth(6.0F);
//			{
//				RenderSystem.pushMatrix();
//				RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getPitch(), 1, 0, 0);
//				RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getYaw() + 180, 0, 1, 0);
//
////				ServerWorld world = Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.world.getDimensionKey());
//				BlockPos pos = new ChunkPos(Minecraft.getInstance().player.getPosition()).asBlockPos();
////				long seed = world.getSeed();
////				MixinHelpers.generator = new SimplexNoiseGenerator(new Random(world.getSeed()));
//
//				int size = 16;
//				for (int xOff = -size; xOff<=size;xOff++) {
//					for (int zOff = -size; zOff <= size; zOff++) {
//						RenderSystem.pushMatrix();
//						int x = pos.getX()+(xOff);
//						x = x >> 2;
//						int z = pos.getZ()+(zOff);
//						z = z >> 2;
//						double y = MixinHelpers.get(x,z);
//						AxisAlignedBB boundingBox = new AxisAlignedBB(pos.getX() + xOff, 128, pos.getZ() +zOff, pos.getX() + 1 + xOff, 128 + 1f / 16, pos.getZ() + 1 + zOff);
//						RenderSystem.translated(
//								-Minecraft.getInstance().getRenderManager().info.getProjectedView().x,
//								-Minecraft.getInstance().getRenderManager().info.getProjectedView().y,
//								-Minecraft.getInstance().getRenderManager().info.getProjectedView().z
//						);
//						Biome b = MixinHelpers.getBiome(x,z,null, null);
//						if (b != null) {
//							Color c = new Color(b.getGrassColor(x,z));
//							DebugRenderer.renderBox(boundingBox, c.getRed()/255f, c.getGreen()/255f, c.getBlue()/255f, 1f);
//						} else {
//							float f = (float) (Math.max(0,(y+100))/180f);
//							DebugRenderer.renderBox(boundingBox, f, f, f, 1f);
//						}
//						RenderSystem.popMatrix();
//					}
//				}
//
//				RenderSystem.popMatrix();
//			}
//			RenderSystem.enableTexture();
//			RenderSystem.disableBlend();
//			RenderSystem.popMatrix();
		}
	}
}
