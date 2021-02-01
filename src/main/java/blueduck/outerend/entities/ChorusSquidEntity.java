package blueduck.outerend.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class ChorusSquidEntity extends CreatureEntity {
    private static final DataParameter<ChorusSquidMode> MODE = EntityDataManager.createKey(ChorusSquidEntity.class, ChorusSquidMode.SERIALIZER);
    private static final DataParameter<Float> WANTED_TENTACLE_ANGLE = EntityDataManager.createKey(ChorusSquidEntity.class, DataSerializers.FLOAT);
    public float prevTentacleAngle;
    public float tentacleAngle;

    public float wantedPitchOffset;
    public float prevPitchOffset;
    public float pitchOffset;

    static {
        DataSerializers.registerSerializer(ChorusSquidMode.SERIALIZER);
    }

    public static boolean shouldSpawn(EntityType<ChorusSquidEntity> squid, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return !world.isAirBlock(pos.down())
            &&  world.isAirBlock(pos)
            &&  world.isAirBlock(pos.up())
            &&  world.isAirBlock(pos.up(2));
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT tag) {
        this.setPosition(this.getPosX(), this.getPosY() + 2d, this.getPosZ());
        return super.onInitialSpawn(world, difficulty, reason, entityData, tag);
    }

    public ChorusSquidEntity(EntityType<? extends ChorusSquidEntity> type, World worldIn) {
        super(type, worldIn);
        rand.setSeed(getEntityId());
        this.moveController = new ChorusSquidMovementController(this);
        this.lookController = new ChorusSquidLookController(this);
        tentacleAngle = 0f;
        prevTentacleAngle = 0f;
        wantedPitchOffset = 0f;
        pitchOffset = 0f;
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(MODE, ChorusSquidMode.IDLE);
        dataManager.register(WANTED_TENTACLE_ANGLE, 0f);
    }

    public ChorusSquidMode getSquidMode() {
        return dataManager.get(MODE);
    }

    public void setSquidMode(ChorusSquidMode mode) {
        dataManager.set(MODE, mode);
    }

    public enum ChorusSquidMode {
        IDLE,
        LUNGE,
        MOVE;

        public static final Serializer SERIALIZER = new Serializer();

        private static class Serializer implements IDataSerializer<ChorusSquidMode>{
            @Override
            public void write(PacketBuffer packet, ChorusSquidMode mode) {
                packet.writeEnumValue(mode);
            }

            @Override
            public ChorusSquidMode read(PacketBuffer packet) {
                return packet.readEnumValue(ChorusSquidMode.class);
            }

            @Override
            public ChorusSquidMode copyValue(ChorusSquidMode mode) {
                return mode;
            }
        }
    }

    class ChorusSquidMovementController extends MovementController {
        float moveSpeed;
        public ChorusSquidMovementController(ChorusSquidEntity squid) {
            super(squid);
            moveSpeed = 0f;
        }

        @Override
        public void tick() {
            ChorusSquidEntity squid = (ChorusSquidEntity) mob;
            LivingEntity target = squid.getAttackTarget();
            if (target == null || !target.isAlive() || squid.getDistanceSq(target) > 24*24) {
                return;
            }

            if (getSquidMode() == ChorusSquidMode.MOVE) {
                float wantedTentacleAngle = getWantedTentacleAngle();
                if (wantedTentacleAngle == 0) {
                    moveSpeed = 0.4f + (90f - tentacleAngle) / 90f * 0.8f;
                }
                else {
                    moveSpeed *= 0.9;
                }
            }
            else if (getSquidMode() == ChorusSquidMode.LUNGE) {
                moveSpeed = 0.6f;
            }
            else {
                moveSpeed = 0f;
            }


            float baseSpeed = (float) (this.speed * squid.getAttributeValue(Attributes.FLYING_SPEED));
            Vector3d delta = target.getEyePosition(0f).subtract(squid.getPositionVec());
            if (delta.lengthSquared() >= 0.5*0.5) {
                delta = delta.normalize();
                double factor = baseSpeed * moveSpeed * 0.3f;
                delta = delta.mul(factor, factor, factor);
                squid.move(MoverType.SELF, delta);
            }

//            squid.setAIMoveSpeed(moveSpeed * baseSpeed);
//            squid.setMoveForward(moveSpeed * baseSpeed);
//
//            float deltaX = (float) (this.posX - squid.getPosX());
//            float deltaY = (float) (this.posY - squid.getPosY()) + 0.5f;
//            float deltaZ = (float) (this.posZ - squid.getPosZ());
//            float deltaMag = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
//            float horizontalDelta = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ) / deltaMag;
//            squid.setMoveVertical(deltaY * moveSpeed * baseSpeed);
        }
    }

    class ChorusSquidLookController extends LookController {
        public ChorusSquidLookController(ChorusSquidEntity squid) {
            super(squid);
        }

        @Override
        public void tick() {
            ChorusSquidEntity squid = (ChorusSquidEntity) mob;

            if (this.isLooking) {
                this.isLooking = false;
                squid.rotationYaw = getTargetYaw();
                squid.rotationPitch = (getTargetPitch() - squid.rotationPitch) * 0.5f;
            }
        }
    }

    class ChorusSquidBodyController extends BodyController {
        public ChorusSquidBodyController(MobEntity mob) {
            super(mob);
        }

        @Override
        public void updateRenderAngles() {
            super.updateRenderAngles();
            ChorusSquidEntity squid = ChorusSquidEntity.this;
            squid.rotationYawHead = squid.renderYawOffset;
            squid.renderYawOffset = squid.rotationYaw;
        }
    }

    public static AttributeModifierMap createModifiers() {
        return CreatureEntity.func_233666_p_()
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.4f)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0f)
                .createMutableAttribute(Attributes.ARMOR, 0.0f)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0f)
                .createMutableAttribute(Attributes.MAX_HEALTH, 12.0f)
//                .createMutableAttribute(ForgeMod.ENTITY_GRAVITY.get(), 0.0f)
                .create();
    }

    // Find paths through the air.
    // This defaults to paths on the ground.
    @Override
    protected PathNavigator createNavigator(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanSwim(true);
        flyingpathnavigator.setCanEnterDoors(true);
        return flyingpathnavigator;
    }

    @Override
    protected BodyController createBodyController() {
        return new ChorusSquidBodyController(this);
    }

    @Override
    protected void registerGoals() {
        targetSelector.addGoal(0, new HurtByTargetGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, PlayerEntity.class, 50,  true, false, null));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, AnimalEntity.class, 50,  true, false, null));
        goalSelector.addGoal(0, new AttackGoal(this, 2.0f));
        goalSelector.addGoal(1, new IdleGoal(this));
    }

    class AttackGoal extends MeleeAttackGoal {
        boolean isBiting;
        public AttackGoal(CreatureEntity squid, double speed) {
            super(squid, speed, true);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            ((ChorusSquidEntity) attacker).setSquidMode(ChorusSquidMode.MOVE);
            isBiting = false;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting();
        }

        @Override
        public void tick() {
            ChorusSquidEntity squid = (ChorusSquidEntity) attacker;
            super.tick();
            if (squid.getAttackTarget().getDistanceSq(squid) <= getAttackReachSqr(squid)) {
                squid.setSquidMode(ChorusSquidMode.LUNGE);
//                System.out.println(isBiting + ", " + tentacleAngle + " / " + squid.getWantedTentacleAngle());
                if (squid.tentacleAngle <= 15f) {
                    squid.setWantedTentacleAngle(80f);
                }
                else if (squid.tentacleAngle >= 75f) {
                    squid.setWantedTentacleAngle(10f);
                }
            }
            else {
                squid.setSquidMode(ChorusSquidMode.MOVE);
            }
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distSq) {
            ChorusSquidEntity squid = (ChorusSquidEntity) attacker;
            if (squid.pitchOffset > 120f && distSq <= getAttackReachSqr(enemy) && !isBiting) {
                if (squid.tentacleAngle >= 75f) {
                    isBiting = true;
                }
            }

            if (isBiting && squid.tentacleAngle <= 40f) {
                squid.attackEntityAsMob(enemy);
                isBiting = false;
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity enemy) {
            return 9 + attacker.getAttackTarget().getWidth();
        }
    }

    class IdleGoal extends Goal {
        private ChorusSquidEntity squid;

        public IdleGoal(ChorusSquidEntity squid) {
            this.squid = squid;
            setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public void startExecuting() {
            squid.setSquidMode(ChorusSquidMode.IDLE);
        }

        @Override
        public boolean shouldExecute() {
            if (squid.getAttackTarget() != null && squid.getAttackTarget().isAlive()) {
                return squid.getDistanceSq(squid.getAttackTarget()) > 24*24;
            }
            else {
                return true;
            }
        }

        @Override
        public void tick() {
            squid.rotationPitch *= 0.98f;
            squid.rotationYaw *= 0.92f;
            Vector3d pos = getPositionVec();
            Vector3d posBelow = getPositionVec().add(0d, -1.85d, 0d);
            RayTraceResult hitBelow = world.rayTraceBlocks(new RayTraceContext(pos, posBelow, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, squid));
            if (hitBelow.getType() != RayTraceResult.Type.BLOCK) {
                squid.move(MoverType.SELF, new Vector3d(0d, -0.04d, 0d));
            }
            else {
                double deltaY = squid.getPosY() - hitBelow.getHitVec().y;
                if (deltaY < 1.85) {
                    squid.move(MoverType.SELF, new Vector3d(0d, 0.04d, 0d));
                }
            }
        }
    }

    public void setWantedTentacleAngle(float angle) {
        dataManager.set(WANTED_TENTACLE_ANGLE, angle);
    }

    public float getWantedTentacleAngle() {
        return dataManager.get(WANTED_TENTACLE_ANGLE);
    }

    @Override
    public void livingTick() {
        prevTentacleAngle = tentacleAngle;
        prevPitchOffset = pitchOffset;
        // Make conditional based on current dimension?
        setNoGravity(true);
        float pitchFactor = 0.1f;
        switch (getSquidMode()) {
            case MOVE:
                wantedPitchOffset = 0f;
                pitchFactor = 0.05f;
                float wantedTentacleAngle = getWantedTentacleAngle();
                if (wantedTentacleAngle != 90f && wantedTentacleAngle != 0f) {
                    setWantedTentacleAngle(90f);
                }

                if (MathHelper.abs(wantedTentacleAngle - tentacleAngle) < 0.5f) {
                    if (wantedTentacleAngle == 90f) {
                        setWantedTentacleAngle(0f);
                    }
                    else {
                        setWantedTentacleAngle(90f);
                    }
                }
                break;
            case IDLE:
                wantedPitchOffset = 90f;
                pitchFactor = 0.1f;
                setWantedTentacleAngle(0f);
                break;
            case LUNGE:
                wantedPitchOffset = 160f;
                pitchFactor = 0.2f;
                break;
        }
        pitchOffset += (wantedPitchOffset - pitchOffset) * pitchFactor;
        tentacleAngle += (getWantedTentacleAngle() - tentacleAngle) * 0.2f;
        super.livingTick();
    }

    @Override
    public void tick() {
        if (this.getPosY() < 1) {
            this.damageEntity(DamageSource.OUT_OF_WORLD, 5);
        }
        super.tick();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (super.attackEntityFrom(source, amount) && this.getRevengeTarget() != null) {
//            this.squirtInk();
            return true;
        } else {
            return false;
        }
    }

    public void travel(Vector3d travelVector) {
        if (this.isInWater()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale((double)0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.5D));
        } else {
            BlockPos ground = new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ());
            float f = 0.91F;
            if (this.onGround) {
                f = this.world.getBlockState(ground).getSlipperiness(this.world, ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround) {
                f = this.world.getBlockState(ground).getSlipperiness(this.world, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale((double)f));
        }

        this.func_233629_a_(this, false);
    }

    @Override
    public int getMaxFallHeight() {
        return 16;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 19) {
        }
        super.handleStatusUpdate(id);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SQUID_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SQUID_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SQUID_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
}

