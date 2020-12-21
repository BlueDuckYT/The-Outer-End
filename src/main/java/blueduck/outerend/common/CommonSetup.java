package blueduck.outerend.common;

import blueduck.outerend.entities.DragonflyEntity;
import blueduck.outerend.entities.HimmeliteEntity;
import blueduck.outerend.entities.PurpurGolemEntity;
import blueduck.outerend.registry.EntityRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(CommonSetup::afterCommonSetup);
	}
	
	public static void afterCommonSetup() {
//		if (!GlobalEntityTypeAttributes.doesEntityHaveAttributes(EntityRegistry.DRAGONFLY.get()))
		GlobalEntityTypeAttributes.put(EntityRegistry.DRAGONFLY.get(), DragonflyEntity.createModifiers());
		GlobalEntityTypeAttributes.put(EntityRegistry.PURPUR_GOLEM.get(), PurpurGolemEntity.createModifiers());
		GlobalEntityTypeAttributes.put(EntityRegistry.HIMMELITE.get(), HimmeliteEntity.createModifiers());
	}
}
