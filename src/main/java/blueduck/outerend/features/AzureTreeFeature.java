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
import net.minecraft.world.IWorldReader;
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
	
	private static IChunk chunkGenerating = null;
	
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		if (reader.getBlockState(pos.add(0,-1,0)).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:invalid_tree_spawn"))))
			return false;
		generateTree(new TreeGenerationContext<>(reader,pos,rand));
		return true;
	}
	
	public static <world extends IWorldReader & IWorldWriter> void generateTree(TreeGenerationContext context) {
		world world = (world) context.world;
		BlockPos pos = context.pos;
		Random rand = context.rand;
		int yPos;
		//Gen center logs
		for (yPos=0;yPos<rand.nextInt(2)+4;yPos++)
			setLog(world, pos.add(0,yPos,0), BlockRegistry.AZURE_STEM.get().getDefaultState());
		
		//Gen surrounding logs
		setLog(world, pos.add(1,0,0), BlockRegistry.AZURE_STEM.get().getDefaultState());
		setLog(world, pos.add(-1,0,0), BlockRegistry.AZURE_STEM.get().getDefaultState());
		setLog(world, pos.add(0,0,1), BlockRegistry.AZURE_STEM.get().getDefaultState());
		setLog(world, pos.add(0,0,-1), BlockRegistry.AZURE_STEM.get().getDefaultState());
		
		//Gen transitional leaves
		{
			BlockState leavesState = BlockRegistry.AZURE_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE,1);
			setLeaves(world, pos.add(1,yPos-1,0), leavesState);
			setLeaves(world, pos.add(-1,yPos-1,0), leavesState);
			setLeaves(world, pos.add(0,yPos-1,1), leavesState);
			setLeaves(world, pos.add(0,yPos-1,-1), leavesState);
		}
		
		//Gen + shape of leaves
		for (int horizontalOffset = 0; horizontalOffset <= 2; horizontalOffset++) {
			BlockState leavesState = BlockRegistry.AZURE_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE,horizontalOffset+1);
			setLeaves(world, pos.add(horizontalOffset,yPos,0), leavesState);
			setLeaves(world, pos.add(-horizontalOffset,yPos,0), leavesState);
			setLeaves(world, pos.add(0,yPos,horizontalOffset), leavesState);
			setLeaves(world, pos.add(0,yPos,-horizontalOffset), leavesState);
		}
		
		//Gen Corner Leaves
		for (int yOff = 0; yOff <= 1; yOff++) {
			BlockState leavesState = BlockRegistry.AZURE_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE,3);
			setLeaves(world, pos.add(1,yPos+yOff,1), leavesState);
			setLeaves(world, pos.add(1,yPos+yOff,-1), leavesState);
			setLeaves(world, pos.add(-1,yPos+yOff,-1), leavesState);
			setLeaves(world, pos.add(-1,yPos+yOff,1), leavesState);
		}
		
		//Gen Stamen
		setLeaves(world, pos.add(0,yPos+1,0), BlockRegistry.AZURE_STAMEN.get().getDefaultState());
	}
	
	protected static <world extends IWorldReader & IWorldWriter> void setLeaves(world world, BlockPos pos, BlockState state) {
		if (world.getBlockState(pos).canBeReplacedByLeaves(world,pos))
			setBlock(world,pos,state);
	}
	
	protected static <world extends IWorldReader & IWorldWriter> void setLog(world world, BlockPos pos, BlockState state) {
		if (world.getBlockState(pos).canBeReplacedByLogs(world,pos))
			setBlock(world,pos,state);
	}
	
	protected static void setBlock(IWorldWriter world, BlockPos pos, BlockState state) {
		if (world instanceof WorldGenRegion) {
			if (chunkGenerating == null || !chunkGenerating.getPos().equals(new ChunkPos(pos)))
				chunkGenerating = ((WorldGenRegion) world).getChunk(pos);
			chunkGenerating.setBlockState(pos, state, false);
		} else {
			world.setBlockState(pos, state, 3);
		}
	}
}
