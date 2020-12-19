package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.items.DebugToolItem;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OuterEndMod.MODID);
	
	public static final RegistryObject<Item> DEBUG_TOOL = conditionallyRegister("dev/debug_tool_ai",new DebugToolItem(new Item.Properties().group(ItemGroup.MISC)),()-> !FMLEnvironment.production);

	public static final RegistryObject<Item> AZURE_BERRIES = ITEMS.register("azure_berries", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(3).saturation(0.3f).build())));
	public static final RegistryObject<Item> FLORAL_PASTE = ITEMS.register("floral_paste", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Item> MUSIC_DISC_GALACTIC_WAVE = ITEMS.register("music_disc_galactic_wave", () -> new MusicDiscItem(15, () -> SoundRegistry.GALACTIC_WAVE.get(), new Item.Properties().group(ItemGroup.MISC)));



	public static RegistryObject<Item> conditionallyRegister(String registryName, Item item, Supplier<Boolean> condition) {
		if (condition.get())
			return ITEMS.register(registryName, () -> item);
		return null;
	}
	
	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
