package blueduck.outerend.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class EndFoliageDecorator extends Feature<NoFeatureConfig> {
	public EndFoliageDecorator(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		int maxIterations = rand.nextInt(8) + 6;
		for (int i = 0; i < maxIterations; i++) {
			BlockPos buffer = pos;
			pos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.add(rand.nextInt(16), 0, rand.nextInt(16)));
			if (reader.getBlockState(pos.down()).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:end_plantable_on")))) {
				BlockState block = BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:azure_foliage")).getRandomElement(rand).getDefaultState();
				if (reader.getBlockState(pos).getBlock() == Blocks.AIR) {
					reader.setBlockState(pos, block, 4);
					if (reader.getBlockState(pos).equals(BlockRegistry.TALL_ENDER_ROOTS.get().getDefaultState())) {
						reader.setBlockState(pos.up(), BlockRegistry.TALL_ENDER_ROOTS.get().getDefaultState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 4);
					}
				}
			}
			pos = buffer;
		}

		return true;
	}
}
