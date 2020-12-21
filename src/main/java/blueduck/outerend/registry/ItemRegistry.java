package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.items.DebugToolItem;
import blueduck.outerend.items.OuterEndSpawnEgg;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.MusicDiscItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OuterEndMod.MODID);
	
	public static final RegistryObject<Item> DEBUG_TOOL = conditionallyRegister("dev/debug_tool_ai",new DebugToolItem(new Item.Properties().group(ItemGroup.MISC)),()-> !FMLEnvironment.production);

	public static final RegistryObject<Item> AZURE_BERRIES = ITEMS.register("azure_berries", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().fastToEat().hunger(3).saturation(0.3f).build())));
	public static final RegistryObject<Item> FLORAL_PASTE = ITEMS.register("floral_paste", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Item> MUSIC_DISC_GALACTIC_WAVE = ITEMS.register("music_disc_galactic_wave", () -> new MusicDiscItem(15, () -> SoundRegistry.GALACTIC_WAVE.get(), new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

	public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, OuterEndMod.MODID);


	public static final RegistryObject<Item> SPECTRAFLY_SPAWN_EGG = SPAWN_EGGS.register("spectrafly_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.DRAGONFLY::get, 3093129, 11056703));
	public static final RegistryObject<Item> PURPUR_GOLEM_SPAWN_EGG = SPAWN_EGGS.register("purpur_golem_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.PURPUR_GOLEM::get, 1994982, 5413563));
	public static final RegistryObject<Item> HIMMELITE_SPAWN_EGG = SPAWN_EGGS.register("himmelite_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.HIMMELITE::get, 1994982, 5413563));



	public static RegistryObject<Item> conditionallyRegister(String registryName, Item item, Supplier<Boolean> condition) {
		if (condition.get())
			return ITEMS.register(registryName, () -> item);
		return null;
	}
	
	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		SPAWN_EGGS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
