package blueduck.outerend.client.entity.renderer;

import blueduck.outerend.OuterEndMod;
import blueduck.outerend.client.entity.model.ChorusSquidModel;
import blueduck.outerend.entities.ChorusSquidEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChorusSquidRenderer extends MobRenderer<ChorusSquidEntity, ChorusSquidModel> {
    private static final ResourceLocation TEXTURE_INACTIVE = new ResourceLocation(OuterEndMod.MODID, "textures/entity/chorus_squid_inactive.png");
    private static final ResourceLocation TEXTURE_ACTIVE = new ResourceLocation(OuterEndMod.MODID, "textures/entity/chorus_squid_active.png");

    public ChorusSquidRenderer(EntityRendererManager renderManager) {
        super(renderManager, new ChorusSquidModel(), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(ChorusSquidEntity squid) {
        if (squid.getSquidMode() == ChorusSquidEntity.ChorusSquidMode.IDLE) {
            return TEXTURE_INACTIVE;
        }
        else {
            return TEXTURE_ACTIVE;
        }
    }

    @Override
    public void render(ChorusSquidEntity squid, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        super.render(squid, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    @Override
    protected void applyRotations(ChorusSquidEntity squid, MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStack.translate(0.0d, 7d/16d, 0.0d);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180f - rotationYaw));
        float renderPitchOffset = MathHelper.lerp(partialTicks, squid.prevPitchOffset, squid.pitchOffset);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(270f - squid.rotationPitch + renderPitchOffset));
        matrixStack.translate(0.0D, -9d/16d, 0.0D);
    }
}
