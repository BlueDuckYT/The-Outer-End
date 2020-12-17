package blueduck.outerend.client.entity.renderer.overlay;

import blueduck.outerend.client.entity.model.DragonflyEntityModel;
import blueduck.outerend.entities.DragonflyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class DragonflyRenderLayer extends LayerRenderer<DragonflyEntity, DragonflyEntityModel> {
	private static final RenderType renderType = RenderType.getEntitySolid(new ResourceLocation("outer_end:textures/entity/glowdragonglow.png"));
	private static final RenderType renderType1 = RenderType.getEntityTranslucent(new ResourceLocation("outer_end:textures/entity/glowdragon.png"));

	public DragonflyRenderLayer(IEntityRenderer<DragonflyEntity, DragonflyEntityModel> entityRendererIn) {
		super(entityRendererIn);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, DragonflyEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		IVertexBuilder ivertexbuilder;
		
		ivertexbuilder = bufferIn.getBuffer(renderType);
		this.getEntityModel().setupForGlow();
		this.getEntityModel().render(matrixStackIn, ivertexbuilder, LightTexture.packLight(15,15),
				OverlayTexture.getPackedUV(2, OverlayTexture.getV(entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0)), 1.0F, 1.0F, 1.0F, 1.0F);
		
		ivertexbuilder = bufferIn.getBuffer(renderType1);
		this.getEntityModel().setupForGel();
		this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn,
				OverlayTexture.getPackedUV(OverlayTexture.getU(0), OverlayTexture.getV(entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0)), 1.0F, 1.0F, 1.0F, 1.0F);
		
		this.getEntityModel().setupForSolid();
	}
}
