package blueduck.outerend.blocks;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class CrystalBlock extends AbstractGlassBlock {

    public Supplier<Block> BUD_BLOCK;

    public CrystalBlock(Properties properties, Supplier<Block> bud) {
        super(properties.tickRandomly());
        BUD_BLOCK = bud;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
       if (random.nextDouble() < 0.1 && pos.getY() <= 24 && worldIn.getBlockState(pos.down()).equals(BlockRegistry.VIOLITE.get().getDefaultState()) && worldIn.getBlockState(pos.up()).equals(Blocks.AIR.getDefaultState())) {
           worldIn.setBlockState(pos.up(), BUD_BLOCK.get().getDefaultState());
       }
    }


}
