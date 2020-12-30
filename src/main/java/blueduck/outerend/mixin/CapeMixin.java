package blueduck.outerend.mixin;

import blueduck.outerend.OuterEndMod;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;


@Mixin(AbstractClientPlayerEntity.class)
public abstract class CapeMixin {


	@Shadow @Nullable protected abstract NetworkPlayerInfo getPlayerInfo();

	private static final ResourceLocation TEXTURE_DEV_CAPE = new ResourceLocation("outer_end:textures/entity/outer_end_cape.png");

	/**
	 * @author Outer End Team
	 * @reason Custom Cape for the devs
	 */

	@Inject(method = "Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;getLocationCape()Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), remap = false, cancellable = true)
	public void getLocationCape(CallbackInfoReturnable info) {
		if (OuterEndMod.DEVS.contains(getPlayerInfo().getGameProfile().getId())) {
			info.setReturnValue(TEXTURE_DEV_CAPE);
		}
	}



}
