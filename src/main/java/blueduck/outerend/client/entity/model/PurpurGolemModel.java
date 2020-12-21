package blueduck.outerend.client.entity.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import blueduck.outerend.entities.PurpurGolemEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class PurpurGolemModel extends EntityModel<PurpurGolemEntity> {
	private final ModelRenderer hips;
	private final ModelRenderer chest;
	private final ModelRenderer rightarm;
	private final ModelRenderer cube_r1;
	private final ModelRenderer leftarm;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer head;
	private final ModelRenderer leftleg;
	private final ModelRenderer rightleg;

	public PurpurGolemModel() {
		textureWidth = 128;
		textureHeight = 128;

		hips = new ModelRenderer(this);
		hips.setRotationPoint(0.0F, 3.0F, 0.0F);
		hips.setTextureOffset(0, 23).addBox(-6.0F, -5.0F, -3.0F, 12.0F, 10.0F, 8.0F, 0.0F, false);

		chest = new ModelRenderer(this);
		chest.setRotationPoint(0.0F, -5.3333F, 0.1667F);
		hips.addChild(chest);
		chest.setTextureOffset(0, 0).addBox(-10.0F, -11.6667F, -4.1667F, 20.0F, 12.0F, 10.0F, 0.0F, false);

		rightarm = new ModelRenderer(this);
		rightarm.setRotationPoint(-10.0F, -8.6667F, 0.8333F);
		chest.addChild(rightarm);
		rightarm.setTextureOffset(56, 15).addBox(-7.0F, 4.0F, -4.0F, 6.0F, 17.0F, 8.0F, 0.0F, false);
		rightarm.setTextureOffset(30, 31).addBox(-8.0F, -4.0F, -5.0F, 8.0F, 8.0F, 10.0F, 0.0F, false);
		rightarm.setTextureOffset(0, 105).addBox(-8.0F, -9.0F, -5.0F, 8.0F, 5.0F, 0.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-8.0F, -6.5F, 0.0F);
		rightarm.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 3.1416F, 0.0F);
		cube_r1.setTextureOffset(16, 95).addBox(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, 0.0F, false);

		leftarm = new ModelRenderer(this);
		leftarm.setRotationPoint(10.0F, -8.6667F, 0.8333F);
		chest.addChild(leftarm);
		leftarm.setTextureOffset(0, 59).addBox(1.0F, 4.0F, -4.0F, 6.0F, 17.0F, 8.0F, 0.0F, false);
		leftarm.setTextureOffset(0, 41).addBox(0.0F, -4.0F, -5.0F, 8.0F, 8.0F, 10.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(8.0F, -6.5F, 0.0F);
		leftarm.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 3.1416F, 0.0F);
		cube_r2.setTextureOffset(16, 95).addBox(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(4.0F, -4.0F, -5.0F);
		leftarm.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 3.1416F, 0.0F);
		cube_r3.setTextureOffset(0, 105).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 5.0F, 0.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -9.6667F, 0.8333F);
		chest.addChild(head);
		head.setTextureOffset(0, 97).addBox(-3.0F, -10.0F, -5.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(36, 49).addBox(-5.0F, -12.0F, -4.0F, 10.0F, 10.0F, 8.0F, 0.0F, false);

		leftleg = new ModelRenderer(this);
		leftleg.setRotationPoint(5.0F, 3.0F, 1.0F);
		hips.addChild(leftleg);
		leftleg.setTextureOffset(64, 64).addBox(-3.0F, 2.0F, -4.0F, 6.0F, 16.0F, 8.0F, 0.0F, false);

		rightleg = new ModelRenderer(this);
		rightleg.setRotationPoint(-5.0F, 3.0F, 1.0F);
		hips.addChild(rightleg);
		rightleg.setTextureOffset(28, 67).addBox(-3.0F, 2.0F, -4.0F, 6.0F, 16.0F, 8.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(PurpurGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
		rightleg.rotateAngleX = MathHelper.sin(limbSwing/2) * limbSwingAmount;
		leftleg.rotateAngleX = -rightleg.rotateAngleX;
		if (entity.getSwingProgress() != 0) {
			if (entity.getSwingProgress() <= 1) {
				float amt = entity.getSwingProgress() + (Minecraft.getInstance().getRenderPartialTicks()*0.15f);
				rightarm.rotateAngleX = (float)Math.toRadians(-(amt*180));
			} else {
				float amt = -(entity.getSwingProgress() + (Minecraft.getInstance().getRenderPartialTicks()*0.15f) - 0.15f);
				rightarm.rotateAngleX = (float)Math.toRadians(-(amt*180));
			}
			rightarm.rotateAngleX += Math.toRadians(0.1f*180);
			leftarm.rotateAngleX = rightarm.rotateAngleX;
		} else {
			leftarm.rotateAngleX = rightleg.rotateAngleX;
			rightarm.rotateAngleX = -leftarm.rotateAngleX;
		}
		this.head.rotateAngleZ = (float) Math.toRadians(netHeadYaw);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		hips.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}