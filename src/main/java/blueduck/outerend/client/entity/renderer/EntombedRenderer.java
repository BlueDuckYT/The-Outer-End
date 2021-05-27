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
		String name;
		try {
			name = entity.getCustomName().getString().toLowerCase();
		}
		catch (Exception e) {
			name = "";
		}
		if (entity.getDataManager().get(entity.SKIN).equals("amogus") || (!name.equals(null) && (name.equals("sus") || name.equals("imposter")))) {
			return new ResourceLocation("outer_end:textures/entity/entombed_amogus.png");
		}
		return new ResourceLocation("outer_end:textures/entity/entombed.png");
	}

}
