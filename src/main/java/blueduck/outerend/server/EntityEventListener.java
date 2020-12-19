package blueduck.outerend.server;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;

public class EntityEventListener {
	public static void onUsedOnBlock(LivingEntityUseItemEvent event) {
//		if (event.getItem().getItem() instanceof BoneMealItem) {
//			if (FMLEnvironment.dist.isClient()) {
//				if (ClientItemUseHelper.isLookingAtEntity()) {
//					return;
//				}
//			} else if (ServerItemUseHelper.isLookingAtEntity(event.getEntity())) {
//				return;
//			}
//			event.getItem().shrink(1);
//		}
	}
	
	public static void onBonemeal(BonemealEvent event) {
		if (event.getBlock().equals(Blocks.END_STONE.getDefaultState())) {
			boolean hasEndGrassNeighbor = false;
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						hasEndGrassNeighbor = event.getWorld().getBlockState(event.getPos().add(x,y,z)).getBlock().equals(BlockRegistry.AZURE_GRASS.get());
						if (hasEndGrassNeighbor) break;
					}
					if (hasEndGrassNeighbor) break;
				}
				if (hasEndGrassNeighbor) break;
			}
			if (hasEndGrassNeighbor) {
				event.getWorld().setBlockState(event.getPos(), BlockRegistry.AZURE_GRASS.get().getDefaultState());
				event.setResult(Event.Result.ALLOW);
			}
		}
	}
}
