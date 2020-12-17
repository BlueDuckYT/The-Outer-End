package blueduck.outerend.features.helpers;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class RotatableFeaturePart {
	private final ArrayList<Direction> offsets = new ArrayList<>();
	
	public RotatableFeaturePart(Collection<Direction> offsets) {
		this.offsets.addAll(offsets);
	}
	
	public RotatableFeaturePart(Direction[] offsets) {
		this.offsets.addAll(Arrays.asList(offsets));
	}
	
	public void generate(Direction rotation, BlockPos position, Consumer<BlockPos> setter) {
		AtomicReference<BlockPos> currentPos = new AtomicReference<>(position);
		offsets.forEach(dir->{
			if (dir == null) currentPos.set(position);
			else {
				Direction offset;
				if (dir.getAxis().isHorizontal()) offset = Direction.fromAngle(dir.getHorizontalAngle()+rotation.getHorizontalAngle());
				else offset = dir;
				currentPos.set(currentPos.get().offset(offset));
				setter.accept(currentPos.get());
			}
		});
	}
}
