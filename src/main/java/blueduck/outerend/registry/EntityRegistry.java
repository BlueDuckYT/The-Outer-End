package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.entities.DragonflyEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, OuterEndMod.MODID);
	
	public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY = ENTITIES.register("dragonfly", () -> EntityType.Builder
			.create(DragonflyEntity::new, EntityClassification.CREATURE)
			.setTrackingRange(64).setUpdateInterval(2).size(1, 0.625f)
			.build(OuterEndMod.MODID + ":dragonfly"));
	
	public static void init() {
		ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
