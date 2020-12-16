package blueduck.outerend.features;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorldWriter;

import java.util.Random;

public class TreeGenerationContext<world extends IWorldReader & IWorldWriter> {
	public world world;
	public BlockPos pos;
	public Random rand;
	
	public TreeGenerationContext(world world, BlockPos pos, Random rand) {
		this.world = world;
		this.pos = pos;
		this.rand = rand;
	}
}
