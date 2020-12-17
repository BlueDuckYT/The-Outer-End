package blueduck.outerend.entities;

import blueduck.outerend.registry.BlockRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

public class DragonflyEntity extends MobEntity {
	public DragonflyEntity(EntityType<? extends MobEntity> type, World worldIn) {
		super(type, worldIn);
		navigator = new FlyingPathNavigator(this, this.world);
	}
	
	public static AttributeModifierMap createModifiers() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.FLYING_SPEED, 1)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 0)
				.createMutableAttribute(Attributes.ARMOR, 0)
				.createMutableAttribute(Attributes.MAX_HEALTH, 10).create();
	}
	
	public boolean shouldRepathfind() {
		return this.ticksExisted % 100 == 0;
	}
	
	public Path getPath() {
		BlockPos pos = null;
		for (int i = 0; i <= 32; i++) {
			int x = (int) this.getPosX()+rand.nextInt(64)-32;
			int z = (int) this.getPosZ()+rand.nextInt(64)-32;
			int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
			boolean shouldFind = rand.nextBoolean();
			if (shouldFind && pos == null)
				pos = new BlockPos(x, y, z);
			else if (world.getBlockState(new BlockPos(x, y, z).add(0, -1, 0)).getBlock().equals(BlockRegistry.AZURE_STAMEN.get()))
				if (pos == null || y > pos.getY()) pos = new BlockPos(x, y, z);
		}
		if (pos == null) {
			int x = (int) this.getPosX()+rand.nextInt(64)-32;
			int z = (int) this.getPosZ()+rand.nextInt(64)-32;
			int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
			pos = new BlockPos(x, y, z);
		}
		return navigator.getPathToPos(pos, 128);
	}
	
	@Override
	public void tick() {
		if (navigator.getPath() != null && navigator.getPath().getTarget() != null && navigator.getPath().getTarget().getY() > this.getPosY())
			this.setMotion(this.getMotion().x, MathHelper.lerp(0.2f,Math.max(-0.1,this.getMotion().getY()),0.5f), this.getMotion().z);
		this.setMotion(this.getMotion().x, Math.max(-0.1,this.getMotion().getY()), this.getMotion().z);
		super.tick();
		this.fallDistance = 0;
		if ((!navigator.hasPath() || navigator.getPath().isFinished()) && shouldRepathfind())
			navigator.setPath(getPath(), 1);
		navigator.tick();
		navigator.updatePath();
	}
}
