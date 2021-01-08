package blueduck.outerend.entities;

import blueduck.outerend.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Random;

public class PurpurGolemEntity extends MonsterEntity {
    private static final DataParameter<Float> ARM_SWING = EntityDataManager.createKey(PurpurGolemEntity.class, DataSerializers.FLOAT);
    
    public PurpurGolemEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.navigator = new GroundPathNavigator(this, worldIn);
    }
    
    public void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }
    
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ARM_SWING, 0f);
    }

    
    public void applyEntityAI() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1D, false));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(PurpurGolemEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, 5, false, false, (p_234199_0_) -> {
            return !(p_234199_0_ instanceof IMob);
        }));
    }
    
    public static AttributeModifierMap createModifiers() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 16)
                .createMutableAttribute(Attributes.ARMOR, 0)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 8)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 12)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 100000000)
                .createMutableAttribute(Attributes.MAX_HEALTH, 80).create();
    }
    
    public Entity getNearest(Class<? extends LivingEntity>... classes) {
        for (Class<? extends LivingEntity> clazz: classes) {
            Entity e = world.getClosestEntityWithinAABB(clazz, EntityPredicate.DEFAULT.setLineOfSiteRequired().setUseInvisibilityCheck().setCustomPredicate(entity -> {
                if (entity instanceof PlayerEntity) {
                    return !((PlayerEntity) entity).isCreative();
                }
                return true;
            }), null, this.getPosX(), this.getPosY(), this.getPosZ(), new AxisAlignedBB(-16,-16,-16,16,16,16).offset(this.getPositionVec()));
            if (e != null) {
                return e;
            }
        }
        return null;
    }
    
    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.setSwingProgress(0.1f);
        boolean flag = super.attackEntityAsMob(entityIn);
        entityIn.setMotion(entityIn.getMotion().add(0.0D, (double)0.4F, 0.0D));
        return flag;
    }
    
    public void setSwingProgress(float amt) {
        this.dataManager.set(ARM_SWING, amt);
    }
    
    public float getSwingProgress() {
        return this.dataManager.get(ARM_SWING);
    }
    
    public void incrementSwingProgress(float amt) {
        this.dataManager.set(ARM_SWING, getSwingProgress()+amt);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        if (getSwingProgress() != 0) {
            incrementSwingProgress(0.15f);
            if (getSwingProgress() >= 2) {
                setSwingProgress(0);
            }
        }
        
        if (FMLEnvironment.dist.isClient() && !FMLEnvironment.production) {
            if (this.navigator.getPath() != null) {
                Minecraft.getInstance().debugRenderer.pathfinding.addPath(this.getEntityId(), navigator.getPath(), 0.5f);
            }
        }
        
        this.fallDistance = 0;
        this.setAir(10);
    }
    
    @Override
    public boolean addPotionEffect(EffectInstance effectInstanceIn) {
        if (
                effectInstanceIn.getPotion().equals(Effects.POISON) ||
                effectInstanceIn.getPotion().equals(Effects.LEVITATION)
        )
            return false;
        return super.addPotionEffect(effectInstanceIn);
    }
    
    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ItemRegistry.PURPUR_GOLEM_SPAWN_EGG.get());
    }

    public static boolean canSpawn(EntityType<PurpurGolemEntity> type, IWorld world, SpawnReason spawnReason, BlockPos pos,
                                   Random random) {

        if (world instanceof ServerWorld && ((ServerWorld) world).func_241112_a_().getStructureStart(pos, true, Structure.END_CITY).isValid())
            return true;

        int radius = 15;
        final BlockPos min = pos.add(-radius, -radius, -radius);
        final BlockPos max = pos.add(radius, radius, radius);

        for (BlockPos posAround : BlockPos.Mutable.getAllInBoxMutable(min, max))
        {
            if (world instanceof ServerWorld && ((ServerWorld) world).func_241112_a_().getStructureStart(posAround, true, Structure.END_CITY).isValid())
                return true;
        }

        return false;

    }


    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    public int getExperiencePoints(PlayerEntity player) {
        return player.getEntityWorld().getRandom().nextInt(50);
    }

}
