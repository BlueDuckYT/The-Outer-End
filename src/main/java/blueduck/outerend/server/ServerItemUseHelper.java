package blueduck.outerend.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class ServerItemUseHelper {
	public static boolean isLookingAtEntity(Entity entity) {
		List<Entity> entities = entity.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class,new AxisAlignedBB(
				entity.getPosX(),entity.getPosY(),entity.getPosZ(),
				entity.getPosX()+entity.getLookVec().x*8,entity.getPosY()+entity.getLookVec().y*8,entity.getPosZ()+entity.getLookVec().z*8
		));
		for (Entity e : entities) {
			AxisAlignedBB bb = e.getBoundingBox().offset(e.getPositionVec());
			if (bb.rayTrace(entity.getPositionVec(),entity.getPositionVec().add(entity.getLookVec().scale(8))).isPresent()) {
				return true;
			}
		}
		return false;
	}
}
