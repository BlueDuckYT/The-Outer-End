package blueduck.outerend.mixin;

import blueduck.outerend.OuterEndMod;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonEntity.class)
public abstract class DragonAttributesMixin {
	@Inject(at = @At("HEAD"), method = "registerAttributes()Lnet/minecraft/entity/ai/attributes/AttributeModifierMap$MutableAttribute;", cancellable = true)
	private static void OUTER_END_registerAttributes(CallbackInfoReturnable<AttributeModifierMap.MutableAttribute> cir) {
		if (OuterEndMod.CONFIG.ENDER_DRAGON_HEALTH.get() != 0) {
			cir.setReturnValue(MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, OuterEndMod.CONFIG.ENDER_DRAGON_HEALTH.get()));
		}
	}
}
