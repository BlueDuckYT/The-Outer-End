package blueduck.outerend.features.crystalcragsurface;

import blueduck.outerend.registry.BlockRegistry;
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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
			double radius = (random.nextDouble() * 6) + 6;
			double maxArcDeviation = 20;
			double arc = 80 + ((random.nextDouble() * maxArcDeviation) - (maxArcDeviation / 2));
			double fullRotation = random.nextDouble() * 360;
			double scale = (random.nextDouble() * 1.5) + 0.7;

			Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0))
					/* Shape */
					.applyLayer(new AddLayer(
							/* Shape */
							Shapes.bentCone(radius, radius, length, arc)))
					/* Shape */
					.applyLayer(new AddLayer(
							/* Shape */
							Shapes.bentCone(radius, radius, length, arc)
									/* Movement */
									.applyLayer(new TranslateLayer(Position.of(-((length / 5) * 4), 0, 0)))
									/* Rotation */
									.applyLayer(new RotateLayer(Quaternion.of(0, 180, 0, true)))))
					/* Scale */
					.applyLayer(ScaleLayer.of(scale))
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

		return false;
	}

}
