package blueduck.outerend.client.entity.renderer;

import blueduck.outerend.client.entity.model.DragonflyEntityModel;
import blueduck.outerend.client.entity.model.HimmeliteModel;
import blueduck.outerend.client.entity.renderer.overlay.DragonflyRenderLayer;
import blueduck.outerend.entities.DragonflyEntity;
import blueduck.outerend.entities.HimmeliteEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HimmeliteRenderer extends MobRenderer<HimmeliteEntity, HimmeliteModel> {
	public HimmeliteRenderer(EntityRendererManager renderManager) {
		super(renderManager, new HimmeliteModel(), 0.5f);
	}
	
	@Override
	public ResourceLocation getEntityTexture(HimmeliteEntity entity) {
		return new ResourceLocation("outer_end:textures/entity/himmelite.png");
	}

}
