package blueduck.outerend.entities;

import blueduck.outerend.registry.EntityRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class StalkerEntity extends AnimalEntity implements IAngerable {
	public int angerTime = 0;
	public UUID angerTarget = null;
	public UUID breedWith = null;
	private static final DataParameter<Boolean> ANGERED = EntityDataManager.createKey(StalkerEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> LAST_FEED = EntityDataManager.createKey(StalkerEntity.class, DataSerializers.VARINT);
	//private static final DataParameter<Integer> AGE = EntityDataManager.createKey(StalkerEntity.class, DataSerializers.VARINT);

	public StalkerEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.navigator = new GroundPathNavigator(this, worldIn);

		if (this.rand.nextFloat() <= 0.05f) {
			this.setGrowingAge(-24000);
		}
	}

//	public void setGrowingAge(int age) {
//		this.dataManager.set(AGE, age);
//	}
//
//	public int getAge() {
//		return this.dataManager.get(AGE);
//	}

	public void setLastFeed(int time) {
		this.dataManager.set(LAST_FEED, time);
	}

	public int getLastFeed() {
		return this.dataManager.get(LAST_FEED);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		if (breedWith != null)
			compound.putUniqueId("love", breedWith);
		compound.putInt("lastFeed", getLastFeed());
		//compound.putInt("age", getAge());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("love")) breedWith = compound.getUniqueId("love");
		setLastFeed(compound.getInt("lastFeed"));
		//setGrowingAge(compound.getInt("age"));
	}



	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return EntityRegistry.STALKER.get().create(p_241840_1_);
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ANGERED, false);
		this.dataManager.register(LAST_FEED, 0);
		//this.dataManager.register(AGE, 1);
	}

	public static boolean canSpawn(EntityType<StalkerEntity> type, IWorld world, SpawnReason spawnReason, BlockPos pos,
								   Random random) {

		if (world.getBlockState(pos.down()).equals(Blocks.END_STONE.getDefaultState())) {
			return true;
		}
		return false;

	}

//	public Path getPath() {
//		//cuz game dumb or smth
//		if (navigator == null)
//			navigator = new GroundPathNavigator(this, world);
//		if (this.angerTarget != null) {
//			ArrayList<LivingEntity> entities = new ArrayList<>();
//			entities.add(world.getPlayerByUuid(angerTarget));
//			if (!entities.isEmpty() && entities.get(0) != null)
//				return navigator.getPathToEntity(entities.get(0), 1);
//		}
//		if (breedWith != null) {
//			List<StalkerEntity> stalkers = world.getEntitiesWithinAABB(StalkerEntity.class,
//					new AxisAlignedBB(
//							(int) this.getPosX() - 16, (int) this.getPosY() - 16, (int) this.getPosZ() - 16,
//							(int) this.getPosX() + 16, (int) this.getPosY() + 16, (int) this.getPosZ() + 16
//					), null);
//			Entity entity = world.getClosestEntity(stalkers, EntityPredicate.DEFAULT.setCustomPredicate((entity1) -> entity1.getUniqueID().equals(breedWith)), this, this.getPosX(), this.getPosY(), this.getPosZ());
//			if (entity != null) return navigator.getPathToEntity(entity, 3);
//		}
//		if (this.navigator.noPath()) {
//			if (this.ticksExisted % 120 == 1 && rand.nextBoolean() && rand.nextBoolean()) {
//				Vector3d look = this.getLookVec();
//				if (rand.nextBoolean() || rand.nextBoolean() || rand.nextBoolean()) {
//					look = Vector3d.fromPitchYaw(
//							0,
//							this.rand.nextInt(360)
//					);
//				}
//				look = look.mul(1, 0, 1);
//				Vector3d targ = this.getPositionVec();
//				for (int i = 0; i <= 16; i++) {
//					targ = targ.add(look);
//					if (!this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY(), targ.getZ())).isAir()) {
//						if (!this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY() + 1, targ.getZ())).isAir()) {
//							targ = targ.add(look.scale(-1));
//							break;
//						} else {
//							targ = targ.add(0, 1, 0);
//						}
//					} else if (this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY() - 1, targ.getZ())).isAir()) {
//						targ = targ.add(0, -1, 0);
//						if (this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY() - 1, targ.getZ())).isAir()) {
//							targ = targ.add(0, -1, 0);
//							if (this.getEntityWorld().getBlockState(new BlockPos(targ.getX(), targ.getY() - 1, targ.getZ())).isAir()) {
//								targ = targ.add(0, 2, 0);
//								targ = targ.add(look.scale(-1));
//								break;
//							}
//						}
//					}
//				}
//				return navigator.getPathToPos(new BlockPos(targ.getX(), targ.getY(), targ.getZ()), 1);
//			}
//			return null;
//		}
//		return null;
//	}

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

	public void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.applyEntityAI();
	}

	public void applyEntityAI() {
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1D, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(StalkerEntity.class));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.CHORUS_FRUIT), false));
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getTrueSource() != null) {
			anger(source.getTrueSource().getUniqueID());
			List<StalkerEntity> stalkers = world.getEntitiesWithinAABB(StalkerEntity.class, new AxisAlignedBB(this.getPosX() - 64, this.getPosY() - 64, this.getPosZ() - 64, this.getPosX() + 64, this.getPosY() + 64, this.getPosZ() + 64));
			for (StalkerEntity stalkerEntity : stalkers) stalkerEntity.anger(source.getTrueSource().getUniqueID());
		}
		return super.attackEntityFrom(source, amount);
	}

	public void anger(UUID uniqueID) {
		func_230258_H__();
		this.setAngerTarget(uniqueID);
	}

	public void setAngered(boolean val) {
		this.dataManager.set(ANGERED, val);
	}

	public boolean isAngered() {
		return this.dataManager.get(ANGERED);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.CHORUS_FRUIT;
	}

//	@Override
//	public void tick() {
//		super.tick();
//
//		if (this.isAIDisabled() || this.world.isRemote)
//			return;
//
//		if (world.getDifficulty().equals(Difficulty.PEACEFUL))
//			this.setAngerTarget(null);
//
//		Path path = getPath();
//		if (path != null) {
//			navigator.setPath(path, 1);
//			this.setMotion(this.getMotion().getX() + 0.01f, this.getMotion().getY(), this.getMotion().getZ());
//		}
//
//		this.setAngerTime(this.getAngerTime() - 1);
//		if (this.getAngerTime() <= 0)
//			this.setAngerTarget(null);
//
//		if (this.getLastFeed() != 0) {
//			this.setLastFeed(this.getLastFeed() + 1);
//			if (this.getLastFeed() >= 5001)
//				this.setLastFeed(0);
//		}
//
//		if (this.angerTarget != null && !world.getDifficulty().equals(Difficulty.PEACEFUL)) {
//			ArrayList<LivingEntity> entities = new ArrayList<>();
//			entities.add(world.getPlayerByUuid(angerTarget));
//			if (!entities.isEmpty() && entities.get(0) != null && this.getDistance(entities.get(0)) <= 3)
//				entities.get(0).attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
//			if (!entities.isEmpty() && entities.get(0) != null && (entities.get(0).isSpectator() || (entities.get(0) instanceof PlayerEntity && ((PlayerEntity) entities.get(0)).isCreative())))
//				this.setAngerTarget(null);
//		}
//
//		this.setGrowingAge(this.getAge() + 1);
//
//		this.setAngered(this.angerTarget != null);
//
//		List<LivingEntity> entities = this.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPosX() - 16, this.getPosY() - 16, this.getPosZ() - 16, this.getPosX() + 16, this.getPosY() + 16, this.getPosZ() + 16));
//		entities.remove(this);
//		Entity nearest = this.world.getClosestEntity(entities, EntityPredicate.DEFAULT, this, this.getPosX(), this.getPosY(), this.getPosZ());
//		if (nearest != null && (this.ticksExisted % 400 >= 200 || (nearest instanceof PlayerEntity)) && !this.getNavigator().hasPath())
//			this.lookAt(EntityAnchorArgument.Type.FEET, nearest.getPositionVec());
//
//		if (this.breedWith != null) {
//			StalkerEntity breedTarg = (StalkerEntity) this.world.getClosestEntity(entities, EntityPredicate.DEFAULT.setCustomPredicate((entity)-> {
//				if (entity instanceof StalkerEntity && ((StalkerEntity) entity).breedWith != null)
//					return ((StalkerEntity) entity).breedWith.equals(this.getUniqueID());
//				return false;
//			}), this, this.getPosX(), this.getPosY(), this.getPosZ());
//			if (breedTarg != null) {
//				if (this.getDistance(breedTarg) <= 3) {
//					if (this.getLastFeed() >= -5000) {
//						this.setLastFeed(-12000);
//					} else if (this.getLastFeed() >= -11950) {
//						StalkerEntity baby = new StalkerEntity(EntityRegistry.STALKER.get(),this.world);
//						ExperienceOrbEntity xp = new ExperienceOrbEntity(this.world, this.getPosX(),this.getPosY(),this.getPosZ(),2);
//						breedTarg.breedWith = null;
//						this.breedWith = null;
//						breedTarg.setLastFeed(-5000);
//						this.setLastFeed(-5000);
//						baby.setPosition(this.getPosX(),this.getPosY(),this.getPosZ());
//						baby.setGrowingAge(-24000);
//						this.world.addEntity(baby);
//						this.world.addEntity(xp);
//					}
//				}
//			}
//		}
//
//		if (FMLEnvironment.dist.isClient() && !FMLEnvironment.production) {
//			if (this.navigator.getPath() != null) {
//				Minecraft.getInstance().debugRenderer.pathfinding.addPath(this.getEntityId(), navigator.getPath(), 0.5f);
//			}
//		}
//    }
//	@Override
//	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
//		if (player.getHeldItem(hand).getItem().equals(Items.CHORUS_FRUIT)) {
//			if (this.getAge() <= 0) {
//				if (!player.isCreative()) player.getHeldItem(hand).shrink(1);
//				this.setGrowingAge(this.getAge() + 1000);
//				double d0 = this.rand.nextGaussian() * 0.02D;
//				double d1 = this.rand.nextGaussian() * 0.02D;
//				double d2 = this.rand.nextGaussian() * 0.02D;
//				this.world.addParticle(ParticleTypes.COMPOSTER, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
//				return ActionResultType.SUCCESS;
//			} else if (Math.abs(this.getLastFeed()) != 0)
//				return super.applyPlayerInteraction(player, vec, hand);
//			else {
//				double d0 = this.rand.nextGaussian() * 0.02D;
//				double d1 = this.rand.nextGaussian() * 0.02D;
//				double d2 = this.rand.nextGaussian() * 0.02D;
//				this.world.addParticle(ParticleTypes.HEART, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
//				this.world.addParticle(ParticleTypes.HEART, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
//				this.world.addParticle(ParticleTypes.HEART, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
//				this.world.addParticle(ParticleTypes.HEART, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
//				this.world.addParticle(ParticleTypes.HEART, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
//			}
//			if (!player.world.isRemote) {
//				if (!player.isCreative()) player.getHeldItem(hand).shrink(1);
//
//				this.setLastFeed(1);
//				List<LivingEntity> entities = this.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPosX() - 16, this.getPosY() - 16, this.getPosZ() - 16, this.getPosX() + 16, this.getPosY() + 16, this.getPosZ() + 16));
//				entities.remove(this);
//				StalkerEntity nearest = (StalkerEntity) this.world.getClosestEntity(entities, EntityPredicate.DEFAULT.setCustomPredicate((entity) -> {
//					if (entity instanceof StalkerEntity)
//						return ((StalkerEntity) entity).getLastFeed() >= 1;
//					return false;
//				}), this, this.getPosX(), this.getPosY(), this.getPosZ());
//				if (nearest != null) {
//					nearest.setLastFeed(-5000);
//					setLastFeed(-5000);
//					nearest.breedWith = this.getUniqueID();
//					this.breedWith = nearest.getUniqueID();
//				}
//			}
//			return ActionResultType.SUCCESS;
//		} else {
//			return super.applyPlayerInteraction(player, vec, hand);
//		}
//	}
}
