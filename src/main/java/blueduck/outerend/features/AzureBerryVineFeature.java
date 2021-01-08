package blueduck.outerend.features;

import blueduck.outerend.blocks.AzureBerryVineBlock;
import blueduck.outerend.registry.BlockRegistry;
import com.mojang.serialization.Codec;
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

public class AzureBerryVineFeature extends Feature<NoFeatureConfig> {
	public AzureBerryVineFeature(Codec<NoFeatureConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		if (!reader.getBlockState(pos.down()).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:end_plantable_on")))) return false;
		int height = rand.nextInt(4);
		for (int i = 0; i < height; i++) {
			reader.setBlockState(pos.up(i), BlockRegistry.AZURE_BERRY_VINE.get().getDefaultState().with(AzureBerryVineBlock.AGE, rand.nextInt(4)), 4);
		}
		reader.setBlockState(pos.up(height), BlockRegistry.AZURE_BERRY_VINE_TOP.get().getDefaultState(), 4);
		return true;
	}
}
