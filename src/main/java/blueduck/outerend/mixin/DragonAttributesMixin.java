package blueduck.outerend.mixin;

import blueduck.outerend.OuterEndMod;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnderDragonEntity.class)
public abstract class DragonAttributesMixin {
	/**
	 * @author Outer End Team
	 * @reason increase dragon's health so it's a bit less of a whimp
	 */
	@Overwrite
	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, OuterEndMod.CONFIG.ENDER_DRAGON_HEALTH.get());
	}

}
