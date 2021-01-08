package blueduck.outerend.blocks;

import blueduck.outerend.features.TreeGenerationContext;
import net.minecraft.block.*;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

import java.util.Random;
import java.util.function.Consumer;

import static net.minecraft.block.SaplingBlock.STAGE;

public class EnderSaplingBlock extends BushBlock implements IGrowable {
	public static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);


	private final Consumer<TreeGenerationContext> generator;
	
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}
	
	public EnderSaplingBlock(Properties properties, Consumer<TreeGenerationContext> generator) {
		super(properties);
		this.generator = generator;
		this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, Integer.valueOf(0)));
	}
	
	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return null;
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:end_plantable_on")));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(STAGE);
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}
	
	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		this.onPlantGrow(state,worldIn,pos,pos);
	}
	
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (worldIn.getLight(pos.up()) >= 9 && random.nextInt(7) == 0) {
			if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
			this.onPlantGrow(state,worldIn,pos,pos);
		}
	}
	
	@Override
	public void onPlantGrow(BlockState state, IWorld world, BlockPos pos, BlockPos source) {
		boolean flag = false;
		for (int i = 1; i < 6; i++) {
			if (world.getBlockState(pos.up(i)) != Blocks.AIR.getDefaultState()) {
				flag = true;
			}
		}
		if (flag) {
			return;
		}
		if (state.get(STAGE) == 0)
			world.setBlockState(pos,state.with(STAGE,1),4,0);
		else {
			if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, world.getRandom(), pos)) return;
			generator.accept(new TreeGenerationContext<>(world,pos,world.getRandom()));
		}
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
