package blueduck.outerend.features;

import blueduck.outerend.registry.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class AzureTreeFeature extends Feature<NoFeatureConfig> {
	public AzureTreeFeature(Codec<NoFeatureConfig> codec) {
		super(codec);
	}
	
	private IChunk chunkGenerating = null;
	
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		if (reader.getBlockState(pos.add(0,-1,0)).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:invalid_tree_spawn"))))
			return false;
		
		int yPos;
		//Gen center logs
		for (yPos=0;yPos<rand.nextInt(2)+4;yPos++)
			setBlockState(reader, pos.add(0,yPos,0), BlockRegistry.AZURE_STEM.get().getDefaultState());
		
		//Gen surrounding logs
		setBlockState(reader, pos.add(1,0,0), BlockRegistry.AZURE_STEM.get().getDefaultState());
		setBlockState(reader, pos.add(-1,0,0), BlockRegistry.AZURE_STEM.get().getDefaultState());
		setBlockState(reader, pos.add(0,0,1), BlockRegistry.AZURE_STEM.get().getDefaultState());
		setBlockState(reader, pos.add(0,0,-1), BlockRegistry.AZURE_STEM.get().getDefaultState());
		
		//Gen transitional leaves
		{
			BlockState leavesState = BlockRegistry.AZURE_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE,1);
			setBlockState(reader, pos.add(1,yPos-1,0), leavesState);
			setBlockState(reader, pos.add(-1,yPos-1,0), leavesState);
			setBlockState(reader, pos.add(0,yPos-1,1), leavesState);
			setBlockState(reader, pos.add(0,yPos-1,-1), leavesState);
		}
		
		//Gen + shape of leaves
		for (int horizontalOffset = 0; horizontalOffset <= 2; horizontalOffset++) {
			BlockState leavesState = BlockRegistry.AZURE_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE,horizontalOffset+1);
			setBlockState(reader, pos.add(horizontalOffset,yPos,0), leavesState);
			setBlockState(reader, pos.add(-horizontalOffset,yPos,0), leavesState);
			setBlockState(reader, pos.add(0,yPos,horizontalOffset), leavesState);
			setBlockState(reader, pos.add(0,yPos,-horizontalOffset), leavesState);
		}
		
		//Gen Corner Leaves
		for (int yOff = 0; yOff <= 1; yOff++) {
			BlockState leavesState = BlockRegistry.AZURE_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE,3);
			setBlockState(reader, pos.add(1,yPos+yOff,1), leavesState);
			setBlockState(reader, pos.add(1,yPos+yOff,-1), leavesState);
			setBlockState(reader, pos.add(-1,yPos+yOff,-1), leavesState);
			setBlockState(reader, pos.add(-1,yPos+yOff,1), leavesState);
		}
		
		//Gen Stamen
		setBlockState(reader, pos.add(0,yPos+1,0), BlockRegistry.AZURE_STAMEN.get().getDefaultState());
		
		return true;
	}
	
	@Override
	protected void setBlockState(IWorldWriter world, BlockPos pos, BlockState state) {
		if (world instanceof WorldGenRegion) {
			if (chunkGenerating == null || !chunkGenerating.getPos().equals(new ChunkPos(pos)))
				chunkGenerating = ((WorldGenRegion) world).getChunk(pos);
			chunkGenerating.setBlockState(pos, state, false);
		} else {
			super.setBlockState(world, pos, state);
		}
	}
}
