package blueduck.outerend.features;

import blueduck.outerend.registry.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class CragPlantDecorator extends Feature<NoFeatureConfig> {
	public CragPlantDecorator(Codec<NoFeatureConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		if (!reader.getBlockState(pos.down()).equals(BlockRegistry.VIOLITE.get().getDefaultState())) return false;

		reader.setBlockState(pos, BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:crystal_crag_foliage")).getRandomElement(rand).getDefaultState(),4);
		return true;
	}
}
