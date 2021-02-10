package blueduck.outerend.entities;

import blueduck.outerend.registry.EntityRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class StalkerEntity extends AnimalEntity implements IAngerable {

    public static final DataParameter<String> COLOR = EntityDataManager.createKey(StalkerEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(BeeEntity.class, DataSerializers.VARINT);
    public String color = "";

    public static String[] COLORS = new String[]{"rose", "mint", "cobalt"};

    private UUID angerTarget = null;
    private int recruitHelpTimer = 0;
    private int angryNoiseTimer = 0;
    private static final RangedInteger recruitHelpRange = TickRangeConverter.convertRange(0, 1);
    private static final RangedInteger angryNoiseRange = TickRangeConverter.convertRange(4, 6);
    private static final RangedInteger angerTimeRange = TickRangeConverter.convertRange(20, 39);

    public StalkerEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        //this.getDataManager().set(COLOR, this.getPersistentData().getString("Color"));
        color = getDataManager().get(COLOR);
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.color = COLORS[worldIn.getRandom().nextInt(COLORS.length)];
        this.dataManager.set(COLOR, color);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        StalkerEntity ent = EntityRegistry.STALKER.get().create(p_241840_1_);
        ent.dataManager.set(COLOR, p_241840_2_.getDataManager().get(COLOR));
        return ent;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.CHORUS_FRUIT;
    }

    public static AttributeModifierMap createModifiers() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.26)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4)
                .createMutableAttribute(Attributes.ARMOR, 0)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32)
                .createMutableAttribute(Attributes.MAX_HEALTH, 16).create();
    }

    public void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new StalkerPanicGoal(this));
        this.goalSelector.addGoal(1, new StalkerAttackGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.CHORUS_FRUIT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new StalkerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(3, new ResetAngerGoal(this, true));
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();

        // func_241359_a_ = tickAnger
        func_241359_a_((ServerWorld) this.world, true);
        // func_233678_J__ = isAngryEntity
        if (func_233678_J__() && getAttackTarget() != null) {
            // If angry, try to recruit help
            tryRecruitHelp();
            // If angry, try make a noise
            tryPlayAngryNoise();

            this.recentlyHit = this.ticksExisted;
        }
    }

    private void tryRecruitHelp() {
        if (recruitHelpTimer-- <= 0) {
            if (getEntitySenses().canSee(getAttackTarget())) {
                recruitHelp();
            }
            recruitHelpTimer = recruitHelpRange.getRandomWithinRange(rand);
        }
    }

    private void recruitHelp() {
        double range = getAttributeValue(Attributes.FOLLOW_RANGE);
        Vector3d pos = getPositionVec().add(-0.5d, -0.5d, -0.5d);
        AxisAlignedBB box = AxisAlignedBB.fromVector(pos).grow(range, 10d, range);
        LivingEntity target = getAttackTarget();
        world.getLoadedEntitiesWithinAABB(StalkerEntity.class, box).stream()
                .filter(e -> e != this)
                .filter(e -> e.getAttackTarget() == null)
                .filter(e -> !e.isOnSameTeam(target))
                .forEach(e -> e.setAttackTarget(target));
    }

    private void tryPlayAngryNoise() {
        if (angryNoiseTimer-- <= 0) {
            playAngryNoise();
            angryNoiseTimer = angryNoiseRange.getRandomWithinRange(rand);
        }
    }

    private void playAngryNoise() {
//        this.playSound(SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_ANGRY, getSoundVolume() * 2.0f, getSoundPitch() * 1.8f);
    }

    @Override
    public void setAttackTarget(@Nullable LivingEntity entity) {
        if (this.getAttackTarget() == null && entity != null) {
            angryNoiseTimer = angryNoiseRange.getRandomWithinRange(this.rand);
            recruitHelpTimer = recruitHelpRange.getRandomWithinRange(this.rand);
        }

        if (entity instanceof PlayerEntity) {
            this.func_230246_e_((PlayerEntity)entity);
        }

        super.setAttackTarget(entity);
    }

    @Override
    public int getAngerTime() {
        return dataManager.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int time) {
        dataManager.set(ANGER_TIME, time);
    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return angerTarget;
    }

    @Override
    public void setAngerTarget(@Nullable UUID uuid) {
        angerTarget = uuid;
    }

    // void randomizeAngerTime()
    public void func_230258_H__() {
        this.setAngerTime(angerTimeRange.getRandomWithinRange(this.rand));
    }

    public static boolean canStalkerSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return true;
    }

    @Override
    public boolean canSpawn(IWorld p_213380_1_, SpawnReason reason) {
        return true;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (!this.getDataManager().get(COLOR).equals("")) {
            compound.putString("Color", this.getDataManager().get(COLOR));
        }
        this.writeAngerNBT(compound);
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (this.getDataManager().get(COLOR).equals("")) {
            this.getDataManager().set(COLOR, compound.getString("Color"));
        }
        this.readAngerNBT((ServerWorld) world, compound);
    }

    public void registerData() {
        super.registerData();
        this.dataManager.register(COLOR, "");
        dataManager.register(ANGER_TIME, 0);
    }

    public class StalkerAttackGoal extends MeleeAttackGoal {
        public StalkerAttackGoal(StalkerEntity stalker) {
            super(stalker, 1d, false);
        }

        @Override
        public boolean shouldExecute() {
            StalkerEntity stalker = (StalkerEntity) attacker;
            return stalker.getGrowingAge() >= 0 && super.shouldExecute();
        }
    }

    public class StalkerPanicGoal extends PanicGoal {
        public StalkerPanicGoal(StalkerEntity stalker) {
            super(stalker, 2.0d);
        }

        @Override
        public boolean shouldExecute() {
            StalkerEntity stalker = (StalkerEntity) creature;
            return stalker.getGrowingAge() < 0 && super.shouldExecute();
        }
    }

    class StalkerHurtByTargetGoal extends HurtByTargetGoal {
        public StalkerHurtByTargetGoal(StalkerEntity stalker) {
            super(stalker);
            setCallsForHelp();
        }

        public boolean shouldContinueExecuting() {
            StalkerEntity stalker = (StalkerEntity) goalOwner;
            return stalker.func_233678_J__() && super.shouldContinueExecuting();
        }

        protected void alertOthers() {
            ((StalkerEntity) goalOwner).recruitHelp();
        }

        protected void setAttackTarget(MobEntity entity, LivingEntity target) {
            ((StalkerEntity) entity).playAngryNoise();
            entity.setAttackTarget(target);
        }
    }
}
