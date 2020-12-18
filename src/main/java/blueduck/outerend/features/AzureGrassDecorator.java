package blueduck.outerend.features;

import blueduck.outerend.registry.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class AzureGrassDecorator extends Feature<NoFeatureConfig> {
	public AzureGrassDecorator(Codec<NoFeatureConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		reader.setBlockState(pos, BlockRegistry.AZURE_TALL_GRASS.get().getDefaultState(),4);
		return true;
	}
}
