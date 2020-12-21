package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.entities.DragonflyEntity;
import blueduck.outerend.entities.HimmeliteEntity;
import blueduck.outerend.entities.PurpurGolemEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, OuterEndMod.MODID);
	
	public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY = ENTITIES.register("spectrafly", () -> EntityType.Builder
			.create(DragonflyEntity::new, EntityClassification.CREATURE)
			.setTrackingRange(64).setUpdateInterval(2).size(12f/16, 10f/16)
			.build(OuterEndMod.MODID + ":spectrafly"));

	public static final RegistryObject<EntityType<PurpurGolemEntity>> PURPUR_GOLEM = ENTITIES.register("purpur_golem", () -> EntityType.Builder
			.create(PurpurGolemEntity::new, EntityClassification.MONSTER)
			.setTrackingRange(32).setUpdateInterval(2).size(1.6f, 3f)
			.immuneToFire()
			.build(OuterEndMod.MODID + ":himmelite"));

	public static final RegistryObject<EntityType<HimmeliteEntity>> HIMMELITE = ENTITIES.register("himmelite", () -> EntityType.Builder
			.create(HimmeliteEntity::new, EntityClassification.MONSTER)
			.setTrackingRange(64).setUpdateInterval(2).size(14f/16, 14f/16)
			.build(OuterEndMod.MODID + ":himmelite"));




	public static void init() {
		ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
