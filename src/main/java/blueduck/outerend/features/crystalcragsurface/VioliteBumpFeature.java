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

public class VioliteBumpFeature extends Feature<NoFeatureConfig> {

	public VioliteBumpFeature(Codec<NoFeatureConfig> codec) {
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

			int amount = random.nextInt(3) + 2;

			Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));
			for (int i = 0; i < amount; i++) {
				int height = random.nextInt(8) + 5;
				double radius = random.nextDouble() * 2 + 2;
				double xtheta = (random.nextDouble() * 15) + 15;
				double ztheta = (random.nextDouble() * 40) + 35;
				double ytheta = random.nextDouble() * 360;

				shape = shape.applyLayer(new AddLayer(Shapes.ellipsoid(radius, height, radius).applyLayer(new RotateLayer(Quaternion.of(xtheta, ytheta, ztheta, true)))));
			}

			shape.applyLayer(new TranslateLayer(Position.of(pos))).applyLayer(new TranslateLayer(Position.of(0, -2, 0))).validate(new SafelistValidator(reader, WHITELIST), (validShape) -> {
				validShape.fill(new SimpleFiller(reader, VIOLITE));
			});

			return true;
		}

		return false;
	}

}
