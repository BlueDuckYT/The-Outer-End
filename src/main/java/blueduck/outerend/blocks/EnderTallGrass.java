package blueduck.outerend.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class EnderTallGrass extends TallGrassBlock {
	public EnderTallGrass(Properties properties) {
		super(properties);
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:end_plantable_on")));
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return false;
	}
}
