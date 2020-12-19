package blueduck.outerend.registry;

import blueduck.outerend.OuterEndMod;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, OuterEndMod.MODID);

    public static final RegistryObject<SoundEvent> AZURE_MUSIC = SOUNDS.register("azure_music", () -> new SoundEvent(new ResourceLocation("outer_end", "music.azure")));

    public static final RegistryObject<SoundEvent> GALACTIC_WAVE = SOUNDS.register("galactic_wave", () -> new SoundEvent(new ResourceLocation("outer_end", "music.galactic_wave")));


    public static void init() {
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
