package blueduck.outerend.client.entity.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import blueduck.outerend.entities.EntombedEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class EntombedModel extends EntityModel<EntombedEntity> {
	private final ModelRenderer Torso;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;

	public EntombedModel() {
		textureWidth = 64;
		textureHeight = 64;

		Torso = new ModelRenderer(this);
		Torso.setRotationPoint(0.0F, 24.0F, 0.0F);
		Torso.setTextureOffset(0, 0).addBox(-11.0F, -38.0F, -5.0F, 22.0F, 31.0F, 10.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-6.5F, -7.0F, 0.0F);
		Torso.addChild(RightLeg);
		RightLeg.setTextureOffset(36, 41).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-12.0F, -19.0F, 0.0F);
		Torso.addChild(RightArm);
		RightArm.setTextureOffset(12, 41).addBox(-2.0F, -1.0F, -1.5F, 3.0F, 18.0F, 3.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(6.5F, -7.0F, 0.0F);
		Torso.addChild(LeftLeg);
		LeftLeg.setTextureOffset(24, 41).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(12.0F, -19.0F, 0.0F);
		Torso.addChild(LeftArm);
		LeftArm.setTextureOffset(0, 41).addBox(-1.0F, -1.0F, -1.5F, 3.0F, 18.0F, 3.0F, 0.0F, false);}

	@Override
	public void setRotationAngles(EntombedEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
		RightLeg.rotateAngleX = MathHelper.sin(limbSwing/2) * limbSwingAmount;
		LeftLeg.rotateAngleX = -RightLeg.rotateAngleX;
		if (entity.getSwingProgress() != 0) {
			if (entity.getSwingProgress() <= 1) {
				float amt = entity.getSwingProgress() + (Minecraft.getInstance().getRenderPartialTicks()*0.15f);
				RightArm.rotateAngleX = (float)Math.toRadians(-(amt*180));

				//Torso.rotateAngleX = (float)Math.toRadians((amt*90));
			} else {
				float amt = -(entity.getSwingProgress() + (Minecraft.getInstance().getRenderPartialTicks()*0.15f) - 0.15f);
				RightArm.rotateAngleX = (float)Math.toRadians(-(amt*180));
				//Torso.rotateAngleX = (float)Math.toRadians((amt*90));
			}
			RightArm.rotateAngleX += Math.toRadians(0.1f*180);
			LeftArm.rotateAngleX = RightArm.rotateAngleX;

		} else {
			LeftArm.rotateAngleX = RightLeg.rotateAngleX;
			RightArm.rotateAngleX = -LeftArm.rotateAngleX;
			Torso.rotateAngleX = 0;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Torso.render(matrixStack, buffer, packedLight, packedOverlay);

	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}