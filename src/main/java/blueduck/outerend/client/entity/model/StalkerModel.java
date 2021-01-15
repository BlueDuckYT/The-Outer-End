package blueduck.outerend.client.entity.model;
// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import blueduck.outerend.entities.StalkerEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class StalkerModel<T extends Entity> extends AgeableModel<StalkerEntity> {
	private final ModelRenderer rearFootLeft;
	private final ModelRenderer rearFootRight;
	private final ModelRenderer haunchLeft;
	private final ModelRenderer haunchRight;
	private final ModelRenderer body;
	private final ModelRenderer frontLegLeft;
	private final ModelRenderer bone4;
	private final ModelRenderer frontLegRight;
	private final ModelRenderer bone;
	private final ModelRenderer head;
	private final ModelRenderer bone2;
	private final ModelRenderer tail;
	private final ModelRenderer bone3;
	private final ModelRenderer getHead;

	public StalkerModel() {
		textureWidth = 128;
		textureHeight = 128;

		rearFootLeft = new ModelRenderer(this);
		rearFootLeft.setRotationPoint(-3.0F, 17.5F, 3.7F);


		rearFootRight = new ModelRenderer(this);
		rearFootRight.setRotationPoint(3.0F, 17.5F, 3.7F);


		haunchLeft = new ModelRenderer(this);
		haunchLeft.setRotationPoint(-5.25F, -3.2424F, 3.2F);
		haunchLeft.setTextureOffset(0, 26).addBox(-1.75F, -2.2576F, -4.5F, 3.0F, 12.0F, 7.0F, 0.0F, false);
		haunchLeft.setTextureOffset(79, 61).addBox(-0.75F, 9.7424F, -0.5F, 2.0F, 17.0F, 3.0F, 0.0F, false);

		haunchRight = new ModelRenderer(this);
		haunchRight.setRotationPoint(4.25F, -3.2424F, 2.851F);
		haunchRight.setTextureOffset(0, 26).addBox(-1.25F, -2.25F, -4.5F, 3.0F, 12.0F, 7.0F, 0.0F, false);
		haunchRight.setTextureOffset(79, 61).addBox(-1.25F, 9.75F, -0.5F, 2.0F, 17.0F, 3.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 1.0F, 6.0F);
		setRotationAngle(body, -0.1745F, 0.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-4.0F, -5.637F, -24.1599F, 7.0F, 6.0F, 20.0F, 0.0F, false);
		body.setTextureOffset(0, 26).addBox(-1.0F, -6.5762F, -24.8545F, 1.0F, 2.0F, 21.0F, 0.0F, false);

		frontLegLeft = new ModelRenderer(this);
		frontLegLeft.setRotationPoint(-3.75F, -5.0F, -12.5F);
		frontLegLeft.setTextureOffset(0, 26).addBox(-3.25F, -2.0F, -2.5F, 3.0F, 14.0F, 5.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(-1.75F, 11.5F, 1.5F);
		frontLegLeft.addChild(bone4);
		bone4.setTextureOffset(80, 61).addBox(-1.0F, 0.5F, -2.0F, 2.0F, 17.0F, 4.0F, 0.0F, false);

		frontLegRight = new ModelRenderer(this);
		frontLegRight.setRotationPoint(2.0F, -5.0F, -12.5F);
		frontLegRight.setTextureOffset(1, 26).addBox(1.0F, -1.6947F, -2.4933F, 3.0F, 14.0F, 5.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(2.5F, 11.8053F, 1.5067F);
		frontLegRight.addChild(bone);
		bone.setTextureOffset(80, 61).addBox(-1.0F, 0.5F, -2.0F, 2.0F, 17.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(-0.5F, -7.0F, -17.1667F);
		head.setTextureOffset(25, 26).addBox(-2.0F, -5.0F, -4.8333F, 4.0F, 9.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-1.0F, -3.0F, -5.9333F, 2.0F, 5.0F, 1.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, -5.0F, -1.3333F);
		head.addChild(bone2);
		setRotationAngle(bone2, 0.1309F, 0.0F, -0.0436F);
		bone2.setTextureOffset(45, 18).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 9.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(-0.3333F, -3.9352F, 3.0711F);
		setRotationAngle(tail, -0.48F, 0.0F, 0.0F);
		tail.setTextureOffset(29, 32).addBox(-1.6667F, -0.8172F, -1.5857F, 3.0F, 5.0F, 17.0F, 0.0F, false);
		tail.setTextureOffset(34, 0).addBox(-0.6667F, -1.3681F, -1.3432F, 1.0F, 1.0F, 17.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-0.1667F, 1.6021F, 15.1508F);
		tail.addChild(bone3);
		setRotationAngle(bone3, -0.1309F, 0.0F, 0.0F);
		bone3.setTextureOffset(0, 49).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 13.0F, 0.0F, false);

		getHead = new ModelRenderer(this);
		getHead.setRotationPoint(0.0F, 0.0F, 0.0F);

	}
	
	@Override
	public void setRotationAngles(StalkerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.frontLegRight.rotateAngleX = (float) (Math.sin(limbSwing / 2f) * (limbSwingAmount / 2f));
		//this.frontLegRight.rotateAngleX += (float) (Math.sin(ageInTicks / 128f) * ((1 - limbSwingAmount) / 64f));
		this.frontLegLeft.rotateAngleX = -frontLegRight.rotateAngleX;
		this.haunchRight.rotateAngleX = -frontLegRight.rotateAngleX;
		this.haunchLeft.rotateAngleX = frontLegRight.rotateAngleX;
		
		this.tail.rotateAngleX = (float) (Math.toRadians(-27.5f) + (Math.sin(ageInTicks / 16f) / 32f));
		this.tail.rotateAngleX += (float) (Math.sin(limbSwing / 4f) * ((limbSwingAmount) / 16f));
		
		this.bone3.rotateAngleX = (float) (tail.rotateAngleX - (Math.toRadians(-20.5f))) * 2;
		
		this.head.rotateAngleX = (float) Math.toRadians(headPitch + 12);
		this.head.rotateAngleY = (float) Math.toRadians(netHeadYaw);

		this.bone2.rotateAngleX = (float) (Math.cos(ageInTicks / 32f) * 0.25f + Math.toRadians(15)) - head.rotateAngleX;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		rearFootLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		rearFootRight.render(matrixStack, buffer, packedLight, packedOverlay);
		haunchLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		haunchRight.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		frontLegLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		frontLegRight.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
	}


	@Override
	public Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	public Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}


}