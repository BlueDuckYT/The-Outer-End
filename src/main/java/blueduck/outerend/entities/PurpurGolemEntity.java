package blueduck.outerend.entities;

import blueduck.outerend.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class PurpurGolemEntity extends MonsterEntity {
    private static final DataParameter<Float> ARM_SWING = EntityDataManager.createKey(HimmeliteEntity.class, DataSerializers.FLOAT);
    
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
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
    }
    
    public static AttributeModifierMap createModifiers() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 16)
                .createMutableAttribute(Attributes.ARMOR, 5)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 8)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 12)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 10000000)
                .createMutableAttribute(Attributes.MAX_HEALTH, 60).create();
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
        return super.attackEntityAsMob(entityIn);
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

}
