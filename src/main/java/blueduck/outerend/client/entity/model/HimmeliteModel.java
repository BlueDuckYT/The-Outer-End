package blueduck.outerend.client.entity.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import blueduck.outerend.entities.HimmeliteEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class HimmeliteModel extends EntityModel<HimmeliteEntity> {
	private final ModelRenderer body;
	private final ModelRenderer hips;
	private final ModelRenderer frontleftleg;
	private final ModelRenderer backleftleg;
	private final ModelRenderer frontrightleg;
	private final ModelRenderer backrightleg;
	private final ModelRenderer head;
	private final ModelRenderer upjaw;
	private final ModelRenderer cube_r1;
	private final ModelRenderer downjaw;
	private final ModelRenderer cube_r2;
	
	public HimmeliteModel() {
		textureWidth = 64;
		textureHeight = 64;
		
		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 2.0F);
		
		
		hips = new ModelRenderer(this);
		hips.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(hips);
		hips.setTextureOffset(0, 26).addBox(-4.0F, -7.0F, -7.0F, 8.0F, 3.0F, 9.0F, 0.0F, false);
		
		frontleftleg = new ModelRenderer(this);
		frontleftleg.setRotationPoint(3.0F, -5.0F, -4.5F);
		hips.addChild(frontleftleg);
		frontleftleg.setTextureOffset(0, 40).addBox(-1.0F, 1.0F, -1.5F, 2.0F, 4.0F, 3.0F, 0.0F, false);
		
		backleftleg = new ModelRenderer(this);
		backleftleg.setRotationPoint(3.0F, -5.0F, 0.5F);
		hips.addChild(backleftleg);
		backleftleg.setTextureOffset(0, 40).addBox(-1.0F, 1.0F, -1.5F, 2.0F, 4.0F, 3.0F, 0.0F, false);
		
		frontrightleg = new ModelRenderer(this);
		frontrightleg.setRotationPoint(-3.0F, -5.0F, -4.5F);
		hips.addChild(frontrightleg);
		frontrightleg.setTextureOffset(0, 40).addBox(-1.0F, 1.0F, -1.5F, 2.0F, 4.0F, 3.0F, 0.0F, false);
		
		backrightleg = new ModelRenderer(this);
		backrightleg.setRotationPoint(-3.0F, -5.0F, 0.5F);
		hips.addChild(backrightleg);
		backrightleg.setTextureOffset(0, 40).addBox(-1.0F, 1.0F, -1.5F, 2.0F, 4.0F, 3.0F, 0.0F, false);
		
		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -6.0F, -4.0F);
		hips.addChild(head);
		head.setTextureOffset(25, 26).addBox(-5.0F, -7.0F, 0.0F, 10.0F, 4.0F, 3.0F, 0.0F, false);
		
		upjaw = new ModelRenderer(this);
		upjaw.setRotationPoint(0.0F, -7.0F, 0.0F);
		head.addChild(upjaw);
		upjaw.setTextureOffset(31, 0).addBox(-5.0F, 0.0F, -8.0F, 0.0F, 3.0F, 8.0F, 0.0F, false);
		upjaw.setTextureOffset(0, 0).addBox(-5.0F, -2.0F, -8.0F, 10.0F, 2.0F, 11.0F, 0.0F, false);
		upjaw.setTextureOffset(31, 0).addBox(5.0F, 0.0F, -8.0F, 0.0F, 3.0F, 8.0F, 0.0F, false);
		
		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-1.0F, 1.0F, -8.0F);
		upjaw.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, -1.5708F, 0.0F);
		cube_r1.setTextureOffset(0, 37).addBox(0.0F, -1.0F, -6.0F, 0.0F, 3.0F, 10.0F, 0.0F, false);
		
		downjaw = new ModelRenderer(this);
		downjaw.setRotationPoint(0.0F, -3.0F, 0.0F);
		head.addChild(downjaw);
		downjaw.setTextureOffset(31, 6).addBox(-5.0F, -3.0F, -8.0F, 0.0F, 3.0F, 8.0F, 0.0F, false);
		downjaw.setTextureOffset(0, 13).addBox(-5.0F, 0.0F, -8.0F, 10.0F, 2.0F, 11.0F, 0.0F, false);
		downjaw.setTextureOffset(31, 6).addBox(5.0F, -3.0F, -8.0F, 0.0F, 3.0F, 8.0F, 0.0F, false);
		
		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-1.0F, -1.0F, -8.0F);
		downjaw.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, -1.5708F, 0.0F);
		cube_r2.setTextureOffset(0, 40).addBox(0.0F, -2.0F, -6.0F, 0.0F, 3.0F, 10.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(HimmeliteEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		frontleftleg.rotateAngleX = MathHelper.sin(limbSwing/2) * limbSwingAmount;
		frontrightleg.rotateAngleX = -frontleftleg.rotateAngleX;
		backleftleg.rotateAngleX = frontrightleg.rotateAngleX;
		backrightleg.rotateAngleX = frontleftleg.rotateAngleX;
//		frontrightleg.rotateAngleX = MathHelper.sin(-limbSwing/2) * limbSwingAmount;
//		backleftleg.rotateAngleX = MathHelper.sin(-limbSwing/2) * limbSwingAmount;
//		backrightleg.rotateAngleX = MathHelper.sin(limbSwing/2) * limbSwingAmount;
		
		if (entity.getBiteFactor() >= 1) {
			int start = entity.getLastBiteFactor();
			int end = entity.getBiteFactor();
			if (end == entity.getLastBiteFactor()) {
				if (entity.getBiteFactor() <= 15)
					end+=1;
				if (end > 17) {
					start = 0;
					end = 0;
				}
				if (end == 17)
					end = 0;
			}
			
			upjaw.rotateAngleX = (float)-Math.toRadians(MathHelper.lerp(Minecraft.getInstance().getRenderPartialTicks(),start,end)*2);
//			downjaw.rotateAngleX = (float)Math.toRadians(MathHelper.lerp(Minecraft.getInstance().getRenderPartialTicks(),start,end)*2);
			entity.markForRefresh();
		} else {
			upjaw.rotateAngleX = -Math.abs(MathHelper.sin(limbSwing / 3)) * limbSwingAmount / 8f;
//			downjaw.rotateAngleX = Math.abs(MathHelper.sin(limbSwing / 3)) * limbSwingAmount / 8f;
		}
		downjaw.rotateAngleX = -upjaw.rotateAngleX;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}