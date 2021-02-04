package blueduck.outerend.features.crystalcragsurface;

import static com.google.common.base.Preconditions.checkArgument;

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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class VioliteDeadRainbowFeature extends Feature<NoFeatureConfig> {

	public VioliteDeadRainbowFeature(Codec<NoFeatureConfig> codec) {
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

			double length = (random.nextDouble() * 20) + 20;
			double radius = (random.nextDouble() * 12) + 4;
			double maxArcDeviation = 20;
			double arc = 80 + ((random.nextDouble() * maxArcDeviation) - (maxArcDeviation / 2));
			double maxArcDeviationSecond = 15;
			double arcSecond = 80 + ((random.nextDouble() * maxArcDeviationSecond) - (maxArcDeviationSecond / 2));
			double fullRotation = random.nextDouble() * 360;
			double scale = MathHelper.lerp(random.nextDouble(), 0.7, 1.3);
			if (random.nextDouble() < 0.7) {
				scale *= 1.4;
			}
			double nextArchPos = -((length / 5) * 4);

			int tries = 0;

			while (!closeDown(reader, modifyTriangle(pos, fullRotation, nextArchPos * scale), VIOLITE, 3) && tries < 360) {
				tries++;
				fullRotation = random.nextDouble() * 360;
			}

			if (closeDown(reader, modifyTriangle(pos, fullRotation, nextArchPos * scale), VIOLITE, 3)) {

				double movementDown = closeDownNumber(reader, modifyTriangle(pos, fullRotation, nextArchPos * scale), VIOLITE, 3) + (random.nextDouble() * 2.5) + 1;

				Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0))
						/* Shape */
						.applyLayer(new AddLayer(
								/* Shape */
								Shapes.bentCone(radius, radius, length, arc)
										/* Movement */
										.applyLayer(new TranslateLayer(Position.of(0, -movementDown, 0)))))
						/* Shape */
						.applyLayer(new AddLayer(
								/* Shape */
								Shapes.bentCone(radius, radius, length, arcSecond)
										/* Movement */
										.applyLayer(new TranslateLayer(Position.of(nextArchPos, -movementDown, 0)))
										/* Rotation */
										.applyLayer(new RotateLayer(Quaternion.of(0, 180, 0, true)))))
						/* Scale */
						.applyLayer(ScaleLayer.of(scale))
						/* Rotation */
						.applyLayer(new RotateLayer(Quaternion.of(0, 180, 0, true)))
						/* Rotation */
						.applyLayer(new RotateLayer(Quaternion.of(0, fullRotation, 0, true)))
						/* Movement */
						.applyLayer(new TranslateLayer(Position.of(pos)))
						/* Placement */
						.validate(new SafelistValidator(reader, WHITELIST), (validShape) -> {
							validShape.fill(new SimpleFiller(reader, VIOLITE));
						});

				return true;
			}

		}

		return false;
	}

	private BlockPos modifyTriangle(BlockPos pos, double a, double l) {

		double bufferAngle = a;

		for (int i = 0; i < 4; i++) {
			if (a > 90) {
				a -= 90;
			}
		}

		double angle = constrainToRange(a, 0.001, 89.999);
		double x = l * Math.sin(Math.toRadians(90 - angle));
		double z = l * Math.sin(Math.toRadians(angle));

		if (isBetween(bufferAngle, 0, 90)) {
			return pos.add(x, 0, -z);
		}
		if (isBetween(bufferAngle, 90, 180)) {
			return pos.add(-z, 0, -x);
		}
		if (isBetween(bufferAngle, 180, 270)) {
			return pos.add(-x, 0, z);
		}
		if (isBetween(bufferAngle, 270, 360)) {
			return pos.add(z, 0, x);
		}

		return pos;

	}

	private double constrainToRange(double value, double min, double max) {
		checkArgument(min <= max, "min (%s) must be less than or equal to max (%s)", min, max);
		return Math.min(Math.max(value, min), max);
	}

	private boolean isBetween(double x, double lower, double upper) {
		return lower <= x && x <= upper;
	}

	private boolean closeDown(ISeedReader reader, BlockPos pos, BlockState state, int tries) {
		boolean close = false;
		for (int i = 0; i < tries; i++) {
			if (!close) {
				close = reader.getBlockState(pos.down(i)) == state;
			} else {
				return close;
			}
		}
		return close;
	}

	private int closeDownNumber(ISeedReader reader, BlockPos pos, BlockState state, int tries) {
		for (int i = 0; i < tries; i++) {
			if (reader.getBlockState(pos.down(i)) == state) {
				return i;
			}
		}
		return 0;
	}
}
