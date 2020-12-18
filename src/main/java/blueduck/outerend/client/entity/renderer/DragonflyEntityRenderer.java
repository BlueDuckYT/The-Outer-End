package blueduck.outerend.client.entity.renderer;

import blueduck.outerend.client.entity.model.DragonflyEntityModel;
import blueduck.outerend.client.entity.renderer.overlay.DragonflyRenderLayer;
import blueduck.outerend.entities.DragonflyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class DragonflyEntityRenderer extends MobRenderer<DragonflyEntity, DragonflyEntityModel> {
	public DragonflyEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new DragonflyEntityModel(), 0.5f);
		addLayer(new DragonflyRenderLayer(this));
	}
	
	@Override
	public ResourceLocation getEntityTexture(DragonflyEntity entity) {
		return new ResourceLocation("outer_end:textures/entity/glowdragon.png");
	}
	
	@Override
	public void render(DragonflyEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		entityModel.setupForSolid();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn,
				LightTexture.packLight(Math.min(LightTexture.getLightBlock(packedLightIn)+3,15),LightTexture.getLightSky(packedLightIn)));
	}
}
