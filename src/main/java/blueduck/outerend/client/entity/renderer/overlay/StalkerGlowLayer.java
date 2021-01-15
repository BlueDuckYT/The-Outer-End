package blueduck.outerend.client.entity.renderer.overlay;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.client.entity.model.StalkerModel;
import blueduck.outerend.entities.StalkerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class StalkerGlowLayer<T extends StalkerEntity, M extends EntityModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(OuterEndMod.MODID, "textures/entity/stalkerglow.png"));

    public StalkerGlowLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    @Override
    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
