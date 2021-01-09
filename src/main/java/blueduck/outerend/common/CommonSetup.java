package blueduck.outerend.common;

import blueduck.outerend.entities.*;
import blueduck.outerend.registry.EntityRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(CommonSetup::afterCommonSetup);
	}
	
	public static void afterCommonSetup() {
		GlobalEntityTypeAttributes.put(EntityRegistry.DRAGONFLY.get(), DragonflyEntity.createModifiers());
		GlobalEntityTypeAttributes.put(EntityRegistry.PURPUR_GOLEM.get(), PurpurGolemEntity.createModifiers());
		GlobalEntityTypeAttributes.put(EntityRegistry.HIMMELITE.get(), HimmeliteEntity.createModifiers());
		//GlobalEntityTypeAttributes.put(EntityRegistry.STALKER.get(), StalkerEntity.createModifiers());
		GlobalEntityTypeAttributes.put(EntityRegistry.ENTOMBED.get(), EntombedEntity.createModifiers());

		EntitySpawnPlacementRegistry.register(EntityRegistry.PURPUR_GOLEM.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PurpurGolemEntity::canSpawn);
		//EntitySpawnPlacementRegistry.register(EntityRegistry.STALKER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StalkerEntity::canAnimalSpawn);

	}
}
