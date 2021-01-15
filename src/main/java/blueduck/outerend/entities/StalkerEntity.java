package blueduck.outerend.entities;

import blueduck.outerend.registry.BlockRegistry;
import blueduck.outerend.registry.EntityRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class StalkerEntity extends AnimalEntity {

    public static final DataParameter<String> COLOR = EntityDataManager.createKey(StalkerEntity.class, DataSerializers.STRING);

    public String color = "";

    public static String[] COLORS = new String[]{"rose", "mint", "cobalt"};

    public StalkerEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
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
        ent.dataManager.set(COLOR, ((StalkerEntity) (p_241840_2_)).dataManager.get(COLOR));
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
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.CHORUS_FRUIT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.down()).isIn(BlockRegistry.VIOLITE.get());
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("Color", color);
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.color = compound.getString("Color");
    }

    public void registerData() {
        super.registerData();
        this.dataManager.register(COLOR, "");
    }
}
