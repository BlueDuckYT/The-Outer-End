package blueduck.outerend.blocks;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class CragTallGrass extends TallGrassBlock {

	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final VoxelShape UP_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);
	public static final VoxelShape DOWN_SHAPE = Block.makeCuboidShape(1.0D, 5.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	public static final VoxelShape SIDE_SHAPE = Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 15.0D, 16.0D);

	public CragTallGrass(Properties properties) {
		super(properties.sound(SoundType.STONE));
		this.setDefaultState(this.getDefaultState().with(FACING, (Direction.UP)));

	}

	@Override
	public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).equals(BlockRegistry.VIOLITE.get().getDefaultState());
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(FACING).equals(Direction.UP)) {
			return UP_SHAPE;
		}
		if (state.get(FACING).equals(Direction.DOWN)) {
			return DOWN_SHAPE;
		}
		return SIDE_SHAPE;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return false;
	}

	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction direction = state.get(FACING);
		BlockPos blockpos = pos.offset(direction.getOpposite());
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return blockstate.isSolidSide(worldIn, blockpos, direction);
	}
}
