package blueduck.outerend.features.crystalcragsurface;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.Shapes;
import com.terraformersmc.terraform.shapes.impl.filler.SimpleFiller;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.BendLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.DilateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
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

		if (random.nextDouble() < 0.1) {
			BlockState VIOLITE = BlockRegistry.VIOLITE.get().getDefaultState();
			List<BlockState> WHITELIST = Arrays.asList(VIOLITE, Blocks.AIR.getDefaultState(), Blocks.END_STONE.getDefaultState());

			pos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);

			while (reader.isAirBlock(pos.down()) && pos.getY() != 0) {
				pos = pos.down();
			}

			if (reader.getBlockState(pos.down()) == VIOLITE) {

				double length = (random.nextDouble() * 15) + 20;
				double radius = (random.nextDouble() * 6) + 4;
				double arc = 80 + MathHelper.lerp(random.nextDouble(), -10, 10);
				double arc2 = 80 + MathHelper.lerp(random.nextDouble(), -10, 10);
				double scale = MathHelper.lerp(random.nextDouble(), 0.7, 1.3);
				double downY = random.nextDouble() * (length / 5) + 1;

				Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

				double nextArchPos = -new BendLayer(arc, radius, length).modifyMin(shape).getX();

				boolean suitable = false;
				int tries = 0;

				double fullRotation = random.nextDouble() * 360;
				BlockPos nextArch = pos.add(-Math.cos(Math.toRadians(fullRotation)) * nextArchPos * scale, pos.getY(), Math.sin(Math.toRadians(fullRotation)) * nextArchPos * scale);

				while (!suitable && tries <= 360) {
					fullRotation += 1;
					nextArch = pos.add(-Math.cos(Math.toRadians(fullRotation)) * nextArchPos * scale, 0, Math.sin(Math.toRadians(fullRotation)) * nextArchPos * scale);
					Iterator<BlockPos> iterator = BlockPos.getAllInBoxMutable(nextArch.add(0, -3, 0), nextArch.add(0, 3, 0)).iterator();

					searchForViolite: while (iterator.hasNext()) {
						BlockPos boxPos = iterator.next();
						if (!suitable) {
							suitable = reader.getBlockState(boxPos).isIn(VIOLITE.getBlock());
						} else {
							break searchForViolite;
						}
					}
					tries++;
				}

				if (suitable) {

					nextArchPos = -Math.abs(nextArch.getX() - pos.getX());

					/* Shape */
					shape = shape.applyLayer(new AddLayer(
							/* Shape */
							Shapes.ellipticalPyramid(radius, radius, length)
									/* Bend */
									.applyLayer(new BendLayer(arc, radius, length))
									/* Scale */
									.applyLayer(new DilateLayer(Position.of(scale, scale, scale)))));

					/* Shape */
					shape = shape.applyLayer(new AddLayer(
							/* Shape */
							Shapes.ellipticalPyramid(radius, radius, length)
									/* Bend */
									.applyLayer(new BendLayer(arc2, radius, length))
									/* Rotate */
									.applyLayer(new RotateLayer(Quaternion.of(0, 180, 0, true)))
									/* Scale */
									.applyLayer(new DilateLayer(Position.of(scale, scale, scale)))
									/* Movement */
									.applyLayer(new TranslateLayer(Position.of(nextArchPos, 0, 0)))));

					/* Rotation */
					shape = shape.applyLayer(new RotateLayer(Quaternion.of(0, fullRotation, 0, true)));

					/* Movement */
					shape = shape.applyLayer(new TranslateLayer(Position.of(pos)));

					/* Movement */
					shape = shape.applyLayer(new TranslateLayer(Position.of(0, -downY, 0)));

					shape.validate(new SafelistValidator(reader, WHITELIST), (validShape) -> {
						validShape.fill(new SimpleFiller(reader, VIOLITE));
					});

					return true;
				}
			}
		}

		return false;
	}

}
