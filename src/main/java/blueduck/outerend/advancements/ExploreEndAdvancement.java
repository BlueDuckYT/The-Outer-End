package blueduck.outerend.advancements;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class ExploreEndAdvancement {

	private final Advancement.Builder advancement;

	public ExploreEndAdvancement() {

		ResourceLocation parent = new ResourceLocation("end/elytra");
		DisplayInfo display = new DisplayInfo(new ItemStack(Items.COMPASS), new TranslationTextComponent("advancements.end.explore_end.title"), new TranslationTextComponent("advancements.end.explore_end.description"), null, FrameType.CHALLENGE, true, true, false);
		AdvancementRewards rewards = AdvancementRewards.Builder.experience(500).build();
		advancement = Advancement.Builder.builder().withParentId(parent).withDisplay(display).withRewards(rewards);

		ForgeRegistries.BIOMES.forEach((biome) -> {
			if (biome.getCategory() == Biome.Category.THEEND) {
				ResourceLocation end_id = biome.getRegistryName();
				advancement.withCriterion("find_" + end_id.toString(), new Criterion(PositionTrigger.Instance.forLocation(LocationPredicate.forBiome(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, end_id)))));
			}
		});
	}

	public Advancement.Builder getAdvancement() {
		return advancement;
	}

}
