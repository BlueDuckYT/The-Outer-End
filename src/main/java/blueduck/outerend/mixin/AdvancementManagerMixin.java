package blueduck.outerend.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.google.gson.JsonElement;

import blueduck.outerend.registry.AdvancementRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

@Mixin(AdvancementManager.class)
public abstract class AdvancementManagerMixin {

	@Inject(method = "apply", at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void addOuterEndAdvancements(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn, CallbackInfo ci, Map<ResourceLocation, Advancement.Builder> map) {
		AdvancementRegistry.ADVANCEMENTS.forEach((id, advancement) -> {
			map.put(id, advancement);
		});
	}

}
