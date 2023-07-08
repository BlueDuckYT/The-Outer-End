package blueduck.outerend.server;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;

public class BlockEventListener {

    public static void onFluidChangeBlock(BlockEvent.FluidPlaceBlockEvent event) {
        if (event.getWorld().getBlockState(event.getPos().below()).equals(BlockRegistry.ANCIENT_ICE.get().defaultBlockState()) && event.getNewState().equals(Blocks.COBBLESTONE.defaultBlockState())) {
            event.setNewState(BlockRegistry.ANCIENT_STONE.get().defaultBlockState());
        }
        if (event.getWorld().getBlockState(event.getPos().below()).equals(BlockTags.create(new ResourceLocation("outer_end:crystal_blocks"))) && event.getNewState().equals(Blocks.COBBLESTONE.defaultBlockState())) {
            event.setNewState(BlockRegistry.VIOLITE.get().defaultBlockState());
        }
    }

}
