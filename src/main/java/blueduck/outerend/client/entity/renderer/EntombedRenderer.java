package blueduck.outerend.client.entity.renderer;

import blueduck.outerend.client.entity.model.EntombedModel;
import blueduck.outerend.client.entity.model.PurpurGolemModel;
import blueduck.outerend.entities.EntombedEntity;
import blueduck.outerend.entities.PurpurGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class EntombedRenderer extends MobRenderer<EntombedEntity, EntombedModel> {
	public EntombedRenderer(EntityRendererManager renderManager) {
		super(renderManager, new EntombedModel(), 0.8f);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntombedEntity entity) {
		return new ResourceLocation("outer_end:textures/entity/entombed.png");
	}

}
