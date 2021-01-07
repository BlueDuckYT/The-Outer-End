package blueduck.outerend.client;

import blueduck.outerend.client.colors.items.SpawnEggColors;
import blueduck.outerend.client.entity.renderer.*;
import blueduck.outerend.registry.BlockRegistry;
import blueduck.outerend.registry.EntityRegistry;
import blueduck.outerend.registry.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
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
		RenderTypeLookup.setRenderLayer(BlockRegistry.TALL_ENDER_ROOTS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_SPROUTS.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(BlockRegistry.POTTED_AZURE_BUD.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.POTTED_ENDER_ROOTS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.POTTED_AZURE_SPROUTS.get(), RenderType.getCutout());


		if (BlockRegistry.isLoaded("quark")) {
			RenderTypeLookup.setRenderLayer(BlockRegistry.AZURE_LADDER.get(), RenderType.getCutout());
		}
		RenderTypeLookup.setRenderLayer(BlockRegistry.ROSE_CRYSTAL.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(BlockRegistry.MINT_CRYSTAL.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(BlockRegistry.COBALT_CRYSTAL.get(), RenderType.getTranslucent());

		RenderTypeLookup.setRenderLayer(BlockRegistry.ROSE_CRYSTAL_BUD.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.MINT_CRYSTAL_BUD.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.COBALT_CRYSTAL_BUD.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(BlockRegistry.COBALT_ROOTS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.ROSE_ROOTS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.MINT_ROOTS.get(), RenderType.getCutout());

		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.DRAGONFLY.get(), DragonflyEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HIMMELITE.get(), HimmeliteRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.PURPUR_GOLEM.get(), PurpurGolemRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.STALKER.get(), StalkerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.ENTOMBED.get(), EntombedRenderer::new);
		
		Minecraft.getInstance().getItemColors().register(new SpawnEggColors(), ItemRegistry.SPECTRAFLY_SPAWN_EGG::get);
		Minecraft.getInstance().getItemColors().register(new SpawnEggColors(), ItemRegistry.HIMMELITE_SPAWN_EGG::get);
		Minecraft.getInstance().getItemColors().register(new SpawnEggColors(), ItemRegistry.PURPUR_GOLEM_SPAWN_EGG::get);
		Minecraft.getInstance().getItemColors().register(new SpawnEggColors(), ItemRegistry.STALKER_SPAWN_EGG::get);
		Minecraft.getInstance().getItemColors().register(new SpawnEggColors(), ItemRegistry.ENTOMBED_SPAWN_EGG::get);
	}
}
