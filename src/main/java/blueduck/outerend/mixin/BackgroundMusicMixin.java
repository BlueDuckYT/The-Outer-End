package blueduck.outerend.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import blueduck.outerend.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(Minecraft.class)
public abstract class BackgroundMusicMixin {

	@Nullable
	@Shadow
	public ClientPlayerEntity player;

	@Shadow
	@Final
	@Mutable
	public IngameGui ingameGUI;

	@Inject(method = "getBackgroundMusicSelector", at = @At("HEAD"), cancellable = true)
	private void OUTER_END_getMusicType(CallbackInfoReturnable<BackgroundMusicSelector> ci) {
		if (this.player != null) {
			if (this.player.world.getDimensionKey() == World.THE_END) {
				if (this.ingameGUI.getBossOverlay().shouldPlayEndBossMusic()) {
					ci.setReturnValue(BackgroundMusicTracks.DRAGON_FIGHT_MUSIC);
				} else if (this.player.abilities.isCreativeMode && this.player.abilities.allowFlying) {
					ci.setReturnValue(SoundRegistry.createEndMusic(SoundRegistry.CREATIVE_OUTER_END.get()));
				} else {
					ci.setReturnValue(this.player.world.getBiomeManager().getBiomeAtPosition(this.player.getPosition()).getBackgroundMusic().orElse(SoundRegistry.createEndMusic(SoundRegistry.GAME_OUTER_END.get())));
				}
			}
		}
	}

}
