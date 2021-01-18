package blueduck.outerend.blocks;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BoneMealItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class StamenBlock extends Block {


    public StamenBlock(Properties properties) {
        super(properties.tickRandomly());
    }



    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.2) {
            for (int i = 0; i < random.nextInt(20); i++){
                BlockPos pos2 = new BlockPos(getRandomLocation(pos.getX(), 3, random), getRandomLocation(pos.getY(), 2, random), getRandomLocation(pos.getZ(), 3, random));
                Block block = worldIn.getBlockState(pos2).getBlock();
                BlockState blockstate = worldIn.getBlockState(pos2);
                if (block instanceof IGrowable && ((IGrowable) block).canGrow(worldIn, pos2, worldIn.getBlockState(pos2), worldIn.isRemote)) {
                    if (blockstate.hasProperty(CropsBlock.AGE)) {
                        worldIn.setBlockState(pos2, blockstate.with(CropsBlock.AGE, blockstate.get(CropsBlock.AGE) + 1));
                    }
                    if (blockstate.hasProperty(SweetBerryBushBlock.AGE)) {
                        worldIn.setBlockState(pos2, blockstate.with(SweetBerryBushBlock.AGE, blockstate.get(SweetBerryBushBlock.AGE) + 1));
                    }
                    }
            }
        }
    }

    public int getRandomLocation(int pos, int n, Random rand) {
        return pos + (rand.nextInt(n * 2) - n);
    }



}
