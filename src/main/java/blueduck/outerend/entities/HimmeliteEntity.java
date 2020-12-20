package blueduck.outerend.entities;

import blueduck.outerend.items.OuterEndSpawnEgg;
import blueduck.outerend.registry.EntityRegistry;
import blueduck.outerend.registry.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.World;

public class HimmeliteEntity extends MonsterEntity {

    public HimmeliteEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        ItemRegistry.ITEMS.register("himmelite_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), () -> EntityRegistry.HIMMELITE.get(), 1994982, 5413563));
    }

    public void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    public void applyEntityAI() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(HimmeliteEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public static AttributeModifierMap createModifiers() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6)
                .createMutableAttribute(Attributes.ARMOR, 0)
                .createMutableAttribute(Attributes.MAX_HEALTH, 30).create();
    }

}
