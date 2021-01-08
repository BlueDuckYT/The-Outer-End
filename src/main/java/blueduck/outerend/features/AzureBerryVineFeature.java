package blueduck.outerend.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import blueduck.outerend.blocks.AzureBerryVineBlock;
import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class AzureBerryVineFeature extends Feature<NoFeatureConfig> {
	public AzureBerryVineFeature(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		int maxIterations = rand.nextInt(5) + 3;
		for (int i = 0; i < maxIterations; i++) {
			BlockPos buffer = pos;
			pos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.add(rand.nextInt(16), 0, rand.nextInt(16)));
			if (reader.getBlockState(pos.down()).isIn(BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:end_plantable_on")))) {
				int height = rand.nextInt(4);
				if (reader.getBlockState(pos.up(height)).getBlock() == Blocks.AIR) {
					for (int h = 0; h < height; h++) {
						if (reader.getBlockState(pos.up(h)).getBlock() == Blocks.AIR || reader.getBlockState(pos.up(h).down()).getBlock() == BlockRegistry.AZURE_BERRY_VINE.get()) {
							reader.setBlockState(pos.up(h), BlockRegistry.AZURE_BERRY_VINE.get().getDefaultState().with(AzureBerryVineBlock.AGE, rand.nextInt(4)), 4);
						}
					}
					reader.setBlockState(pos.up(height), BlockRegistry.AZURE_BERRY_VINE_TOP.get().getDefaultState(), 4);
				}
			}
			pos = buffer;
		}

		return true;
	}
}
