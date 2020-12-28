package blueduck.outerend.client.entity.renderer;

import blueduck.outerend.client.entity.model.StalkerModel;
import blueduck.outerend.entities.StalkerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class StalkerRenderer extends MobRenderer<StalkerEntity, StalkerModel> {
	public StalkerRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new StalkerModel(), 1);
	}
	
	@Override
	public ResourceLocation getEntityTexture(StalkerEntity entity) {
		return new ResourceLocation("outer_end:textures/entity/stalker.png");
	}
}
