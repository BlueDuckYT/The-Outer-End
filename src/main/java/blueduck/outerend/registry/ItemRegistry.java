package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.items.CrystalShardItem;
import blueduck.outerend.items.OuterEndSpawnEgg;
import com.minecraftabnormals.abnormals_core.common.items.AbnormalsBoatItem;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OuterEndMod.MODID);
	
	public static final RegistryObject<Item> AZURE_BERRIES = ITEMS.register("azure_berries", () -> new BlockNamedItem(BlockRegistry.AZURE_BERRY_VINE_TOP.get(), new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().fastToEat().hunger(3).saturation(0.3f).build())));
	public static final RegistryObject<Item> FLORAL_PASTE = ITEMS.register("floral_paste", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Item> SPECTRAGEL = ITEMS.register("spectragel", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Item> STALKER_MEAT = ITEMS.register("stalker_meat", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(4).saturation(0.3f).build())));
	public static final RegistryObject<Item> COOKED_STALKER_MEAT = ITEMS.register("cooked_stalker_meat", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(8).saturation(0.7f).build())));


	public static final RegistryObject<Item> MUSIC_DISC_GALACTIC_WAVE = ITEMS.register("music_disc_galactic_wave", () -> new MusicDiscItem(15, () -> SoundRegistry.DISC_GALACTIC_WAVE.get(), new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));
	public static final RegistryObject<Item> MUSIC_DISC_UNKNOWN_FRONTIER = ITEMS.register("music_disc_unknown_frontier", () -> new MusicDiscItem(15, () -> SoundRegistry.DISC_UNKNOWN_FRONTIER.get(), new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

	public static final RegistryObject<Item> AZURE_BOAT = ITEMS.register("azure_boat", () -> new AbnormalsBoatItem("outer_end:azure", new Item.Properties().group(ItemGroup.TRANSPORTATION)));

	public static final RegistryObject<Item> ROSE_CRYSTAL_SHARD = ITEMS.register("rose_crystal_shard", () -> new CrystalShardItem(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> MINT_CRYSTAL_SHARD = ITEMS.register("mint_crystal_shard", () -> new CrystalShardItem(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> COBALT_CRYSTAL_SHARD = ITEMS.register("cobalt_crystal_shard", () -> new CrystalShardItem(new Item.Properties().group(ItemGroup.MISC)));



	public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, OuterEndMod.MODID);


	public static final RegistryObject<Item> SPECTRAFLY_SPAWN_EGG = SPAWN_EGGS.register("spectrafly_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.DRAGONFLY::get, 3093129, 11056703));
	public static final RegistryObject<Item> PURPUR_GOLEM_SPAWN_EGG = SPAWN_EGGS.register("purpur_golem_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.PURPUR_GOLEM::get, 10105599, 4794755));
	public static final RegistryObject<Item> HIMMELITE_SPAWN_EGG = SPAWN_EGGS.register("himmelite_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.HIMMELITE::get, 1994982, 5413563));
	public static final RegistryObject<Item> STALKER_SPAWN_EGG = SPAWN_EGGS.register("stalker_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.STALKER::get, 1575190, 6031692));
	public static final RegistryObject<Item> ENTOMBED_SPAWN_EGG = SPAWN_EGGS.register("entombed_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.ENTOMBED::get, 1575190, 6031692));
	public static final RegistryObject<Item> CHORUS_SQUID_SPAWN_EGG = SPAWN_EGGS.register("chorus_squid_spawn_egg", () -> new OuterEndSpawnEgg(new Item.Properties().group(ItemGroup.MISC), EntityRegistry.CHORUS_SQUID::get, 0x660099, 0xff99ff));



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
