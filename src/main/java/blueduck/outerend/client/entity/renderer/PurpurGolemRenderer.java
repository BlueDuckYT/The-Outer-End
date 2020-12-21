package blueduck.outerend.client.entity.renderer;

import blueduck.outerend.client.entity.model.HimmeliteModel;
import blueduck.outerend.client.entity.model.PurpurGolemModel;
import blueduck.outerend.entities.HimmeliteEntity;
import blueduck.outerend.entities.PurpurGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PurpurGolemRenderer extends MobRenderer<PurpurGolemEntity, PurpurGolemModel> {
	public PurpurGolemRenderer(EntityRendererManager renderManager) {
		super(renderManager, new PurpurGolemModel(), 0.8f);
	}
	
	@Override
	public ResourceLocation getEntityTexture(PurpurGolemEntity entity) {
		return new ResourceLocation("outer_end:textures/entity/purpur_golem.png");
	}

}
