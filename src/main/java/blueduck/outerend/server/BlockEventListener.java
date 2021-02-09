package blueduck.outerend.server;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;

public class BlockEventListener {

    public static void onFluidChangeBlock(BlockEvent.FluidPlaceBlockEvent event) {
        if (event.getWorld().getBlockState(event.getPos().down()).equals(BlockRegistry.ANCIENT_ICE.get().getDefaultState()) && event.getNewState().equals(Blocks.COBBLESTONE.getDefaultState())) {
            event.setNewState(BlockRegistry.ANCIENT_STONE.get().getDefaultState());
        }
        if (event.getWorld().getBlockState(event.getPos().down()).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:crystal_blocks"))) && event.getNewState().equals(Blocks.COBBLESTONE.getDefaultState())) {
            event.setNewState(BlockRegistry.VIOLITE.get().getDefaultState());
        }
    }

}
