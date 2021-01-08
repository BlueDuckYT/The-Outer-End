package blueduck.outerend.registry;

import java.util.HashMap;
import java.util.Map;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.advancements.ExploreEndAdvancement;
import net.minecraft.advancements.Advancement;
import net.minecraft.util.ResourceLocation;

public class AdvancementRegistry {

	public static Map<ResourceLocation, Advancement.Builder> ADVANCEMENTS = new HashMap<ResourceLocation, Advancement.Builder>();

	public static Advancement.Builder EXPLORE_END = registerAdvancement("end/explore_end", new ExploreEndAdvancement().getAdvancement());

	private static Advancement.Builder registerAdvancement(String id, Advancement.Builder advancement) {
		ADVANCEMENTS.put(new ResourceLocation(OuterEndMod.MODID, id), advancement);
		return advancement;
	}

}
