package blueduck.outerend.features;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.Shapes;
import com.terraformersmc.terraform.shapes.impl.filler.SimpleFiller;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.ScaleLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class LargeCrystalSpikeFeature extends Feature<NoFeatureConfig> {

	public LargeCrystalSpikeFeature(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {

		BlockState VIOLITE = BlockRegistry.VIOLITE.get().getDefaultState();

		List<BlockState> WHITELIST = Arrays.asList(VIOLITE, Blocks.AIR.getDefaultState(), Blocks.END_STONE.getDefaultState());

		while (reader.isAirBlock(pos.down()) && pos.getY() != 0) {
			pos = pos.down();
		}

		while (reader.getBlockState(pos.up()) == VIOLITE && pos.getY() != 255) {
			pos = pos.up();
		}

		if (reader.getBlockState(pos.down()) == VIOLITE) {

			BlockState CRYSTAL_TYPE = BlockTags.getCollection().getTagByID(new ResourceLocation("outer_end:crystal_blocks")).getRandomElement(random).getDefaultState();

			double length = (random.nextDouble() * 15) + 10;
			double radius = (random.nextDouble() * 2.3) + 3;
			double Xrot = random.nextDouble() * 30;
			double Zrot = random.nextDouble() * 15;
			double Yrot = random.nextDouble() * 360;
			double movementDown = length / 4;
			double scale = random.nextDouble() + 0.3;

			Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0))
					/* Shape */
					.applyLayer(new AddLayer(
							/* Shape */
							Shapes.ellipticalPyramid(radius, radius, length)
									/* Rotation */
									.applyLayer(new RotateLayer(Quaternion.of(Xrot, Yrot, Zrot, true)))))
					/* Scale */
					.applyLayer(ScaleLayer.of(scale))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(pos)))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(0, -movementDown, 0)))
					/* Placement */
					.validate(new SafelistValidator(reader, WHITELIST), (validShape) -> {
						validShape.fill(new SimpleFiller(reader, CRYSTAL_TYPE));
					});

			return true;
		}

		return false;

	}
}
