package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
//import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, OuterEndMod.MODID);

	// Ingame Music
	public static final RegistryObject<SoundEvent> GAME_OUTER_END = register("music.outer_end.game");
	public static final RegistryObject<SoundEvent> CREATIVE_OUTER_END = register("music.outer_end.creative");
	public static final RegistryObject<SoundEvent> AZURE_MUSIC = register("music.outer_end.azure");
	public static final RegistryObject<SoundEvent> CRYSTAL_CRAG_MUSIC = register("music.outer_end.crystal_crag");

	// Music Discs
	public static final RegistryObject<SoundEvent> DISC_GALACTIC_WAVE = register("music.outer_end.disc.galactic_wave");
	public static final RegistryObject<SoundEvent> DISC_UNKNOWN_FRONTIER = register("music.outer_end.disc.unknown_frontier");

	// Blocks
	public static final RegistryObject<SoundEvent> BLOCK_VIOLITE_BREAK = register("block.outer_end.violite.break");
	public static final RegistryObject<SoundEvent> BLOCK_VIOLITE_STEP = register("block.outer_end.violite.step");
	public static final RegistryObject<SoundEvent> BLOCK_VIOLITE_PLACE = register("block.outer_end.violite.place");
	public static final RegistryObject<SoundEvent> BLOCK_VIOLITE_HIT = register("block.outer_end.violite.hit");
	public static final RegistryObject<SoundEvent> BLOCK_VIOLITE_FALL = register("block.outer_end.violite.fall");

	public static final SoundType VIOLITE_SOUND = new SoundType(1.0F, 1.5F, new SoundEvent(new ResourceLocation(OuterEndMod.MODID, "block.outer_end.violite.break")), new SoundEvent(new ResourceLocation(OuterEndMod.MODID, "block.outer_end.violite.step")), new SoundEvent(new ResourceLocation(OuterEndMod.MODID, "block.outer_end.violite.place")), new SoundEvent(new ResourceLocation(OuterEndMod.MODID, "block.outer_end.violite.hit")), new SoundEvent(new ResourceLocation(OuterEndMod.MODID, "block.outer_end.violite.fall")));

	private static RegistryObject<SoundEvent> register(String key) {
		return SOUNDS.register(key, () -> new SoundEvent(new ResourceLocation(OuterEndMod.MODID, key)));
	}

	public static Music createEndMusic(SoundEvent sound) {
		return new Music(sound, 8000, 15000, false);
	}

	public static void init() {
		SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
