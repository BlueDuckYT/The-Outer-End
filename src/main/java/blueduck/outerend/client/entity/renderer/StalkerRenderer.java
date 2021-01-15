package blueduck.outerend.client.entity.renderer;

import blueduck.outerend.client.entity.model.StalkerModel;
import blueduck.outerend.client.entity.renderer.overlay.StalkerGlowLayer;
import blueduck.outerend.entities.StalkerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class StalkerRenderer extends MobRenderer<StalkerEntity, StalkerModel<Entity>> {
	public StalkerRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new StalkerModel<Entity>(), 1);
		this.addLayer(new StalkerGlowLayer(this));
	}
	
	@Override
	public void render(StalkerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		if (entityIn.getGrowingAge() <= 0) {
			matrixStackIn.scale(0.5f,0.5f,0.5f);
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.pop();
	}
	
	@Override
	public ResourceLocation getEntityTexture(StalkerEntity entity) {
		return new ResourceLocation("outer_end:textures/entity/stalker_" + entity.getPersistentData().getString("Color") +".png");
	}
}
