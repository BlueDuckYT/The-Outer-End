package blueduck.outerend.blocks;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

import java.util.Random;

public class AzureBerryVineTopBlock extends AbstractTopPlantBlock {

    public static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    public AzureBerryVineTopBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1d);
    }

    @Override
    public int getGrowthAmount(Random rand) {
        return PlantBlockHelper.getGrowthAmount(rand);
    }

    @Override
    public boolean canGrowIn(BlockState state) {
        return PlantBlockHelper.isAir(state);
    }


    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public AbstractTopPlantBlock getTopPlantBlock() {
        return (AbstractTopPlantBlock) BlockRegistry.AZURE_BERRY_VINE_TOP.get();
    }

    @Override
    public Block getBodyPlantBlock() {
        return BlockRegistry.AZURE_BERRY_VINE.get();
    }
}
