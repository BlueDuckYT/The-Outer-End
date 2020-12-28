package blueduck.outerend.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StalkerEntity extends MonsterEntity implements IAngerable {
	public int angerTime = 0;
	public UUID angerTarget = null;
	
	public StalkerEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.navigator = new GroundPathNavigator(this,worldIn);
	}
	
	public Path getPath() {
		//cuz game dumb or smth
		if (navigator == null)
			navigator = new GroundPathNavigator(this,world);
		if (this.angerTarget != null) {
			ArrayList<LivingEntity> entities = new ArrayList<>();
			entities.add(world.getPlayerByUuid(angerTarget));
			if (!entities.isEmpty() && entities.get(0) != null)
				return navigator.getPathToEntity(entities.get(0),1);
		}
		if (this.navigator.noPath()) {
			if (this.ticksExisted % 120 == 1 && rand.nextBoolean() && rand.nextBoolean()) {
				Vector3d look = this.getLookVec();
				if (rand.nextBoolean() || rand.nextBoolean() || rand.nextBoolean()) {
					look = Vector3d.fromPitchYaw(
							0,
							this.rand.nextInt(360)
					);
				}
				look = look.mul(1, 0, 1);
				Vector3d targ = this.getPositionVec();
				for (int i = 0; i <= 16; i++) {
					targ = targ.add(look);
					if (!this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY(), targ.getZ())).isAir()) {
						if (!this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY() + 1, targ.getZ())).isAir()) {
							targ = targ.add(look.scale(-1));
							break;
						} else {
							targ = targ.add(0, 1, 0);
						}
					} else if (this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY() - 1, targ.getZ())).isAir()) {
						targ = targ.add(0, -1, 0);
						if (this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY() - 1, targ.getZ())).isAir()) {
							targ = targ.add(0, -1, 0);
							if (this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY() - 1, targ.getZ())).isAir()) {
								targ = targ.add(0, 2, 0);
								targ = targ.add(look.scale(-1));
								break;
							}
						}
					}
				}
				return navigator.getPathToPos(new BlockPos(targ.getX(), targ.getY(), targ.getZ()), 1);
			}
			return null;
		}
		return null;
	}
	
	@Override
	public int getAngerTime() {
		return angerTime;
	}
	
	@Override
	public void setAngerTime(int time) {
		this.angerTime = time;
	}
	
	@Nullable
	@Override
	public UUID getAngerTarget() {
		return angerTarget;
	}
	
	@Override
	public void setAngerTarget(@Nullable UUID target) {
		this.angerTarget = target;
	}
	
	@Override
	public void func_230258_H__() {
		this.setAngerTime(1000);
	}
	
	public static AttributeModifierMap createModifiers() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3)
				.createMutableAttribute(Attributes.ARMOR, 0)
				.createMutableAttribute(Attributes.MAX_HEALTH, 12).create();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getTrueSource() != null) {
			anger(source.getTrueSource().getUniqueID());
			List<StalkerEntity> stalkers = world.getEntitiesWithinAABB(StalkerEntity.class,new AxisAlignedBB(this.getPosX()-64,this.getPosY()-64,this.getPosZ()-64,this.getPosX()+64,this.getPosY()+64,this.getPosZ()+64));
			for (StalkerEntity stalkerEntity : stalkers) stalkerEntity.anger(source.getTrueSource().getUniqueID());
		}
		return super.attackEntityFrom(source, amount);
	}
	
	public void anger(UUID uniqueID) {
		func_230258_H__();
		this.setAngerTarget(uniqueID);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (this.isAIDisabled() || this.world.isRemote)
			return;
		
		if (world.getDifficulty().equals(Difficulty.PEACEFUL))
			this.setAngerTarget(null);
		
		Path path = getPath();
		if (path != null) {
			navigator.setPath(path,1);
			this.setMotion(this.getMotion().getX()+0.01f,this.getMotion().getY(),this.getMotion().getZ());
		}
		
		this.setAngerTime(this.getAngerTime()-1);
		if (this.getAngerTime() <= 0)
			this.setAngerTarget(null);
		
		if (this.angerTarget != null && !world.getDifficulty().equals(Difficulty.PEACEFUL)) {
			ArrayList<LivingEntity> entities = new ArrayList<>();
			entities.add(world.getPlayerByUuid(angerTarget));
			if (!entities.isEmpty() && entities.get(0) != null && this.getDistance(entities.get(0)) <= 3)
				entities.get(0).attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
			if (!entities.isEmpty() && entities.get(0) != null && (entities.get(0).isSpectator() || (entities.get(0) instanceof PlayerEntity && ((PlayerEntity) entities.get(0)).isCreative())))
				this.setAngerTarget(null);
		}
		
		List<LivingEntity> entities = this.world.getEntitiesWithinAABB(LivingEntity.class,new AxisAlignedBB(this.getPosX()-16,this.getPosY()-16,this.getPosZ()-16,this.getPosX()+16,this.getPosY()+16,this.getPosZ()+16));
		if (entities.contains(this)) entities.remove(this);
		Entity nearest = this.world.getClosestEntity(entities,EntityPredicate.DEFAULT,this,this.getPosX(),this.getPosY(),this.getPosZ());
		if (nearest != null && (this.ticksExisted % 400 >= 200 || (nearest instanceof PlayerEntity)) && !this.getNavigator().hasPath())
			this.lookAt(EntityAnchorArgument.Type.FEET,nearest.getPositionVec());
		
		if (FMLEnvironment.dist.isClient() && !FMLEnvironment.production) {
			if (this.navigator.getPath() != null) {
				Minecraft.getInstance().debugRenderer.pathfinding.addPath(this.getEntityId(), navigator.getPath(), 0.5f);
			}
		}
	}
}
