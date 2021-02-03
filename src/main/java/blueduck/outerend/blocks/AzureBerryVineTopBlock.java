package blueduck.outerend.blocks;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlockHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
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
    
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (!isValidPosition(null, context.getWorld(), context.getPos())) return null;
        return super.getStateForPlacement(context);
    }
    
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState state1 = worldIn.getBlockState(pos.down());
        return state1.isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:end_plantable_on"))) || state1.getBlock() instanceof AzureBerryVineBlock || state1.getBlock() instanceof AzureBerryVineTopBlock;
    }
}
