package blueduck.outerend.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelWriter;

import java.util.Random;

public class TreeGenerationContext<world extends LevelAccessor & LevelWriter> {
	public world world;
	public BlockPos pos;
	public Random rand;
	
	public TreeGenerationContext(world world, BlockPos pos, Random rand) {
		this.world = world;
		this.pos = pos;
		this.rand = rand;
	}
}
