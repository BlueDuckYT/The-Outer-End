package blueduck.outerend.features.crystalcragsurface;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class VioliteRockBumpFlowerFeature extends Feature<NoFeatureConfig> {

	public VioliteRockBumpFlowerFeature(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {

		BlockState VIOLITE = BlockRegistry.VIOLITE.get().getDefaultState();
		List<BlockState> WHITELIST = Arrays.asList(VIOLITE, Blocks.AIR.getDefaultState(), Blocks.END_STONE.getDefaultState());

		pos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);

		while (reader.isAirBlock(pos.down()) && pos.getY() != 0) {
			pos = pos.down();
		}

		if (reader.getBlockState(pos.down()) == VIOLITE) {

			int shards = random.nextInt(4) + 3;

			for (int s = 0; s < shards; s++) {

				double maxRotationRange = 30;
				double rotation = ((360 / shards) * s) + (random.nextDouble() * maxRotationRange) - (maxRotationRange / 2);
				double length = (random.nextDouble() * 15) + 12;
				double maxTiltChange = 15;
				double tilt = 90 - ((10 / shards) * s) + (random.nextDouble() * maxTiltChange) - (maxTiltChange / 2);
				double width = (random.nextDouble() * 3) + 5;
				double height = (random.nextDouble() * 3) + 3.5;
				double arcTurn = random.nextDouble() * 3;
				double maxTurnRange = 20;
				double turn = (random.nextDouble() * maxTurnRange) - (maxTurnRange / 2);
				double scale = random.nextDouble() + 0.5;

				Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0))
						/* Shape */
						.applyLayer(new AddLayer(
								/* Shape */
								Shapes.bentCone(width, height, length, arcTurn)
										/* Rotation */
										.applyLayer(new RotateLayer(Quaternion.of(turn, rotation, tilt, true)))
										/* Movement */
										.applyLayer(new TranslateLayer(Position.of(0, -(width / 4), 0)))))
						/* Scale */
						.applyLayer(ScaleLayer.of(scale))
						/* Movement */
						.applyLayer(new TranslateLayer(Position.of(pos)))
						/* Placement */
						.validate(new SafelistValidator(reader, WHITELIST), (validShape) -> {
							validShape.fill(new SimpleFiller(reader, VIOLITE));
						});
			}

			return true;
		}

		return false;
	}

}
