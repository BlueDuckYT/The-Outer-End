package blueduck.outerend.entities;

import blueduck.outerend.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class HimmeliteEntity extends MonsterEntity {
    private static final DataParameter<Integer> BITE_FACTOR = EntityDataManager.createKey(HimmeliteEntity.class, DataSerializers.VARINT);
    
    private Entity target = null;
    private boolean isRetreating = false;
    private int lastBiteFactor = 0;
    private boolean updateLastFactor;
    
    @OnlyIn(Dist.CLIENT)
    public int getLastBiteFactor() {
        return lastBiteFactor;
    }
    
    public HimmeliteEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.navigator = new GroundPathNavigator(this, worldIn);
        setBiteFactor(0);
    }
    
    public void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }
    
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BITE_FACTOR, 1);
    }
    
    public void applyEntityAI() {
//        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1D, false));
//        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(HimmeliteEntity.class));
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }
    
    public static AttributeModifierMap createModifiers() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6)
                .createMutableAttribute(Attributes.ARMOR, 0)
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
    
    public Path getPath() {
        if (!isRetreating) {
            target = getNearest(PlayerEntity.class,IronGolemEntity.class);
            if (target == null)
                return null;
            if (this.navigator.getPath() == null)
                return navigator.getPathToEntity(target,1);
            BlockPos targetPos = this.getNavigator().getPath().getTarget();
            if (target.getDistanceSq(targetPos.getX(),targetPos.getY(),targetPos.getZ()) > 1 && this.getDistance(target) >= 1) {
                return navigator.getPathToEntity(target,1);
            } else {
                return null;
            }
        } else {
            if (this.navigator.noPath()) {
                Vector3d targ = this.getLookVec().scale(-16).add(this.getPositionVec());
                Path path = this.navigator.getPathToPos(new BlockPos(targ.x,targ.y,targ.z),1);
                return path;
            }
            if (this.getPosition().distanceSq(this.navigator.getTargetPos()) <= 5)
                this.isRetreating = false;
            return null;
        }
    }
    
    public void setBiteFactor(int amt) {
        this.dataManager.set(BITE_FACTOR, amt);
    }
    
    public void incrementBiteFactor(int amt) {
        this.dataManager.set(BITE_FACTOR, getBiteFactor()+amt);
    }
    
    public int getBiteFactor() {
        return this.dataManager.get(BITE_FACTOR);
    }
    
    @Override
    public void tick() {
        super.tick();
    
        if (this.isAIDisabled() || this.world.isRemote) {
            if (this.world.isRemote) {
                if (this.updateLastFactor || getBiteFactor() == 0) {
                    lastBiteFactor = getBiteFactor();
                    updateLastFactor = false;
                }
            }
            return;
        }
        
        if (target != null && !isRetreating) {
            if (this.getBiteFactor() >= 17)
                this.setBiteFactor(0);
            if (this.getDistance(target) <= 10 && this.getBiteFactor() != 16)
                incrementBiteFactor(1);
            else if (this.getDistance(target) >= 10) setBiteFactor(0);
//            this.setBiteFactor(Math.min(this.getBiteFactor(),20));
            if (this.getDistance(target) <= 2) {
                if (this.getBiteFactor() == 16) {
                    setBiteFactor(17);
                    target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.isRetreating = true;
                    this.navigator.clearPath();
                }
            }
            this.lookAt(EntityAnchorArgument.Type.FEET,target.getPositionVec());
        } else {
            setBiteFactor(-1);
        }
        
        Path path = getPath();
        if (path != null) {
            navigator.setPath(path,1);
        }
        
        if (FMLEnvironment.dist.isClient() && !FMLEnvironment.production) {
            if (this.navigator.getPath() != null) {
                Minecraft.getInstance().debugRenderer.pathfinding.addPath(this.getEntityId(), navigator.getPath(), 0.5f);
            }
        }
    }
    
    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ItemRegistry.HIMMELITE_SPAWN_EGG.get());
    }
    
    public void markForRefresh() {
        this.updateLastFactor = true;
    }
}
