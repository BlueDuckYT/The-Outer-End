package blueduck.outerend.features;

import blueduck.outerend.registry.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EndGrassDecorator extends Feature<NoFeatureConfig> {
	public EndGrassDecorator(Codec<NoFeatureConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		if (!reader.getBlockState(pos.down()).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:end_plantable_on")))) return false;

		reader.setBlockState(pos, BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:azure_foliage")).getRandomElement(rand).getDefaultState(),4);
		if (reader.getBlockState(pos).equals(BlockRegistry.TALL_ENDER_ROOTS.get().getDefaultState()))
		{
			reader.setBlockState(pos.up(), BlockRegistry.TALL_ENDER_ROOTS.get().getDefaultState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 4);
		}
		return true;
	}
}
