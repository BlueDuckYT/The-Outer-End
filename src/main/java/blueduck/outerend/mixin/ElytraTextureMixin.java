package blueduck.outerend.mixin;

import blueduck.outerend.OuterEndMod;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(ElytraLayer.class)
public abstract class ElytraTextureMixin<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    @Shadow
    @Final
    private static ResourceLocation TEXTURE_ELYTRA;
    private static final ResourceLocation TEXTURE_DEVLYTRA = new ResourceLocation("outer_end:textures/entity/outer_end_cape.png");

    public ElytraTextureMixin(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    /**
     * @author Outer End Team
     * @reason Custom Elytra for the devs
     */

    @Inject(method = "getElytraTexture(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), remap = false, cancellable = true)
    public void OUTER_END_getElytraTexture(ItemStack stack, T entity, CallbackInfoReturnable info) {
        if (OuterEndMod.DEVS.contains(entity.getUniqueID())) {
            info.setReturnValue(TEXTURE_DEVLYTRA);
        }
    }


}
