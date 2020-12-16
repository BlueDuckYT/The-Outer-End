package blueduck.outerend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class DyingBlock extends Block {
	private final Supplier<BlockState> diesTo;
	
	public DyingBlock(Properties properties, Supplier<BlockState> diesTo) {
		super(properties);
		this.diesTo = diesTo;
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
		return true;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.randomTick(state, worldIn, pos, random);
//		if (!worldIn.isAreaLoaded(pos, 3)) return;
		if (worldIn.getBlockState(pos.up()).causesSuffocation(worldIn,pos.up())) worldIn.setBlockState(pos,diesTo.get());
		
		for(int i = 0; i < 4; ++i) {
			BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
			if (worldIn.getBlockState(blockpos).isIn(Blocks.END_STONE)) {
				if (!worldIn.getBlockState(blockpos.up()).causesSuffocation(worldIn,blockpos.up())) {
//					ResourceLocation biome = worldIn.getBiome(blockpos).getRegistryName();
//					if (biome != null && biome.toString().equals("outer_end:azure_forest")) {
					if (worldIn.getLight(pos.up()) >= 10) {
						worldIn.setBlockState(blockpos,state);
					}
//					}
				}
			}
		}
	}
}
