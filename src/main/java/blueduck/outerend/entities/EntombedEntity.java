package blueduck.outerend.entities;

import blueduck.outerend.registry.ItemRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class EntombedEntity extends MonsterEntity {
    public static final DataParameter<String> SKIN = EntityDataManager.createKey(EntombedEntity.class, DataSerializers.STRING);
    private static final DataParameter<Float> ARM_SWING = EntityDataManager.createKey(EntombedEntity.class, DataSerializers.FLOAT);

    public EntombedEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
        this.dataManager.register(SKIN, "");
        this.dataManager.register(ARM_SWING, 0f);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("skin", this.getDataManager().get(SKIN));

    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (!this.getDataManager().get(SKIN).equals("")) {
            this.getDataManager().set(SKIN, compound.getString("skin"));
        }
        else {
            if (rand.nextInt(5) == 1) {
                this.dataManager.set(SKIN, "amogus");
            }
            else {
                this.dataManager.set(SKIN, "default");
            }
        }
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (rand.nextInt(5) == 1) {
            this.dataManager.set(SKIN, "amogus");
        }
        else {
            this.dataManager.set(SKIN, "default");
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    
    public void applyEntityAI() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1D, false));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(EntombedEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, 5, false, false, (p_234199_0_) -> {
            return !(p_234199_0_ instanceof IMob);
        }));
    }
    
    public static AttributeModifierMap createModifiers() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4)
                .createMutableAttribute(Attributes.ARMOR, 0)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32)
                .createMutableAttribute(Attributes.MAX_HEALTH, 20).create();
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
        
        this.fallDistance = 0;
        this.setAir(10);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.setSwingProgress(0.1f);
        return super.attackEntityAsMob(entityIn);
    }

    
    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ItemRegistry.ENTOMBED_SPAWN_EGG.get());
    }



    public int getExperiencePoints(PlayerEntity player) {
        return player.getEntityWorld().getRandom().nextInt(25);
    }

}
