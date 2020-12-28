package blueduck.outerend.client.entity.model;
// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import blueduck.outerend.entities.StalkerEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class StalkerModel extends AgeableModel<StalkerEntity> {
	private final ModelRenderer body;
	private final ModelRenderer haunchRight;
	private final ModelRenderer rearFootRight;
	private final ModelRenderer haunchLeft;
	private final ModelRenderer rearFootRight2;
	private final ModelRenderer frontLegRight;
	private final ModelRenderer bone;
	private final ModelRenderer frontLegLeft;
	private final ModelRenderer bone2;
	private final ModelRenderer head;
	private final ModelRenderer bone4;
	private final ModelRenderer tail;
	private final ModelRenderer bone3;
	
	public StalkerModel() {
		textureWidth = 80;
		textureHeight = 80;
		
		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -1.0F, 6.0F);
		setRotationAngle(body, -0.1745F, 0.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-4.0F, -5.637F, -24.1599F, 7.0F, 6.0F, 20.0F, 0.0F, false);
		body.setTextureOffset(0, 26).addBox(-1.5F, -6.5762F, -24.8545F, 2.0F, 2.0F, 21.0F, 0.0F, false);
		
		haunchRight = new ModelRenderer(this);
		haunchRight.setRotationPoint(3.0F, -2.5152F, -4.1264F);
		body.addChild(haunchRight);
		setRotationAngle(haunchRight, 0.1745F, 0.0F, 0.0F);
		haunchRight.setTextureOffset(0, 0).addBox(0.0F, -2.9924F, -3.349F, 3.0F, 10.0F, 7.0F, 0.0F, false);
		haunchRight.setTextureOffset(53, 0).addBox(0.0F, 7.0076F, 0.651F, 2.0F, 11.0F, 3.0F, 0.0F, false);
		
		rearFootRight = new ModelRenderer(this);
		rearFootRight.setRotationPoint(0.0F, 22.0F, 2.0F);
		haunchRight.addChild(rearFootRight);
		rearFootRight.setTextureOffset(38, 54).addBox(-1.0F, -4.5F, -0.7F, 2.0F, 11.0F, 3.0F, 0.0F, false);
		
		haunchLeft = new ModelRenderer(this);
		haunchLeft.setRotationPoint(-3.0F, -2.5152F, -4.1264F);
		body.addChild(haunchLeft);
		setRotationAngle(haunchLeft, 0.1745F, 0.0F, 0.0F);
		haunchLeft.setTextureOffset(0, 0).addBox(-3.0F, -2.9924F, -3.349F, 3.0F, 10.0F, 7.0F, 0.0F, true);
		haunchLeft.setTextureOffset(53, 0).addBox(-2.0F, 7.0076F, 0.651F, 2.0F, 11.0F, 3.0F, 0.0F, true);
		
		rearFootRight2 = new ModelRenderer(this);
		rearFootRight2.setRotationPoint(0.0F, 22.0F, 2.0F);
		haunchLeft.addChild(rearFootRight2);
		rearFootRight2.setTextureOffset(38, 54).addBox(-1.0F, -4.5F, -0.7F, 2.0F, 11.0F, 3.0F, 0.0F, true);
		
		frontLegRight = new ModelRenderer(this);
		frontLegRight.setRotationPoint(3.0F, -2.0456F, -18.4791F);
		body.addChild(frontLegRight);
		setRotationAngle(frontLegRight, 0.1745F, 0.0F, 0.0F);
		frontLegRight.setTextureOffset(0, 26).addBox(0.0F, -2.6947F, -1.9933F, 4.0F, 11.0F, 5.0F, 0.0F, false);
		frontLegRight.setTextureOffset(28, 54).addBox(1.5F, 20.3053F, 2.0067F, 1.0F, 10.0F, 4.0F, 0.0F, false);
		
		bone = new ModelRenderer(this);
		bone.setRotationPoint(-4.0F, 24.0F, 12.0F);
		frontLegRight.addChild(bone);
		setRotationAngle(bone, 0.0873F, 0.0F, 0.0F);
		bone.setTextureOffset(52, 29).addBox(5.0F, -16.7818F, -9.9895F, 2.0F, 13.0F, 3.0F, 0.0F, false);
		
		frontLegLeft = new ModelRenderer(this);
		frontLegLeft.setRotationPoint(-3.0F, -2.0456F, -18.4791F);
		body.addChild(frontLegLeft);
		setRotationAngle(frontLegLeft, 0.1745F, 0.0F, 0.0F);
		frontLegLeft.setTextureOffset(0, 26).addBox(-4.0F, -2.6947F, -1.9933F, 4.0F, 11.0F, 5.0F, 0.0F, true);
		frontLegLeft.setTextureOffset(28, 54).addBox(-2.5F, 20.3053F, 2.0067F, 1.0F, 10.0F, 4.0F, 0.0F, true);
		
		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(4.0F, 24.0F, 12.0F);
		frontLegLeft.addChild(bone2);
		setRotationAngle(bone2, 0.0873F, 0.0F, 0.0F);
		bone2.setTextureOffset(52, 29).addBox(-7.0F, -16.7818F, -9.9895F, 2.0F, 13.0F, 3.0F, 0.0F, true);
		
		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -2.8871F, -23.2902F);
		body.addChild(head);
		setRotationAngle(head, 0.1745F, 0.0F, 0.0F);
		head.setTextureOffset(25, 26).addBox(-2.5F, -6.0F, -5.0F, 4.0F, 9.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-1.5F, -4.0F, -6.0F, 2.0F, 5.0F, 1.0F, 0.0F, false);
		
		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(-0.5F, -5.0F, -3.0F);
		head.addChild(bone4);
		bone4.setTextureOffset(45, 18).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 9.0F, 0.0F, false);
		
		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -3.0F, 2.0F);
		setRotationAngle(tail, -0.48F, 0.0F, 0.0F);
		tail.setTextureOffset(29, 32).addBox(-2.0F, -3.5024F, -1.0147F, 3.0F, 5.0F, 17.0F, 0.0F, false);
		tail.setTextureOffset(34, 0).addBox(-1.0F, -4.0533F, -1.7722F, 1.0F, 1.0F, 17.0F, 0.0F, false);
		
		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, -0.3123F, 16.0F);
		tail.addChild(bone3);
		setRotationAngle(bone3, -0.0873F, 0.0F, 0.0F);
		bone3.setTextureOffset(0, 49).addBox(-1.0F, -2.2076F, -0.8079F, 1.0F, 4.0F, 13.0F, 0.0F, false);
	}
	
	@Override
	public void setRotationAngles(StalkerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.frontLegRight.rotateAngleX = (float) (Math.sin(limbSwing / 2f) * (limbSwingAmount / 2f));
		this.frontLegRight.rotateAngleX += (float) (Math.sin(ageInTicks / 128f) * ((1 - limbSwingAmount) / 64f));
		this.frontLegLeft.rotateAngleX = -frontLegRight.rotateAngleX;
		this.haunchRight.rotateAngleX = -frontLegRight.rotateAngleX;
		this.haunchLeft.rotateAngleX = frontLegRight.rotateAngleX;
		
		this.tail.rotateAngleX = (float) (Math.toRadians(-27.5f) + (Math.sin(ageInTicks / 16f) / 32f));
		this.tail.rotateAngleX += (float) (Math.sin(limbSwing / 4f) * ((limbSwingAmount) / 16f));
		
		this.bone3.rotateAngleX = (float) (tail.rotateAngleX - (Math.toRadians(-20.5f))) * 2;
		
		this.head.rotateAngleX = (float) Math.toRadians(headPitch + 12);
		this.head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
		
		if (entity.isAngered()) this.bone4.rotateAngleX = (float) Math.toRadians(45);
		else
			this.bone4.rotateAngleX = (float) (Math.cos(ageInTicks / 32f) * 0.25f + Math.toRadians(15)) - head.rotateAngleX;
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(matrixStack, buffer, packedLight, packedOverlay);
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


	public ModelRenderer getHead() {
		return this.head;
	}
}