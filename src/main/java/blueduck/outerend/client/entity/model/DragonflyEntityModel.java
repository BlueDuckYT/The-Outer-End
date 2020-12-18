// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports
package blueduck.outerend.client.entity.model;

import blueduck.outerend.entities.DragonflyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Quaternion;

public class DragonflyEntityModel extends EntityModel<DragonflyEntity> {
	private final ModelRenderer body;
	private final ModelRenderer translucent;
	private final ModelRenderer emissive;
	private final ModelRenderer tail;
	private final ModelRenderer solid;
	private final ModelRenderer wing1;
	private final ModelRenderer wing3;
	private final ModelRenderer wing2;
	private final ModelRenderer wing4;

	public DragonflyEntityModel() {
		textureWidth = 80;
		textureHeight = 80;
		
		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		
		
		solid = new ModelRenderer(this);
		solid.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(solid);
		solid.setTextureOffset(0, 0).addBox(1.0F, -8.0F, -7.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);
		solid.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -7.0F, 3.0F, 4.0F, 2.0F, 0.0F, true);
		
		translucent = new ModelRenderer(this);
		translucent.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(translucent);
		translucent.setTextureOffset(0, 0).addBox(-5.0F, -10.0F, -8.0F, 10.0F, 10.0F, 16.0F, 0.0F, false);
		
		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -7.0F, 8.0F);
		translucent.addChild(tail);
		tail.setTextureOffset(26, 34).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		
		emissive = new ModelRenderer(this);
		emissive.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(emissive);
		emissive.setTextureOffset(0, 26).addBox(-3.0F, -9.0F, -6.0F, 6.0F, 6.0F, 12.0F, 0.0F, false);
		
		wing1 = new ModelRenderer(this);
		wing1.setRotationPoint(5.0F, -10.0F, -3.0F);
		body.addChild(wing1);
		wing1.setTextureOffset(30, 0).addBox(0.0F, 0.0F, -3.0F, 10.0F, 0.0F, 6.0F, 0.0F, true);
		
		wing3 = new ModelRenderer(this);
		wing3.setRotationPoint(-5.0F, -10.0F, -3.0F);
		body.addChild(wing3);
		wing3.setTextureOffset(30, 0).addBox(-10.0F, 0.0F, -3.0F, 10.0F, 0.0F, 6.0F, 0.0F, false);
		
		wing2 = new ModelRenderer(this);
		wing2.setRotationPoint(5.0F, -10.0F, 4.0F);
		body.addChild(wing2);
		wing2.setTextureOffset(30, 0).addBox(0.0F, 0.0F, -3.0F, 10.0F, 0.0F, 6.0F, 0.0F, true);
		
		wing4 = new ModelRenderer(this);
		wing4.setRotationPoint(-5.0F, -10.0F, 4.0F);
		body.addChild(wing4);
		wing4.setTextureOffset(30, 0).addBox(-10.0F, 0.0F, -3.0F, 10.0F, 0.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(DragonflyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
		body.rotateAngleX = 0;
		body.rotateAngleY = 0;
		body.rotateAngleZ = 0;
		
		if (!entity.isOnGround()) body.rotateAngleX = (float)(entity.getMotion().y)*-2;
		
		tail.setRotationPoint(0.0F, -7.0F, 8.0F);
		if (entity.isOnGround()) {
			body.rotateAngleX = 0;
			if (entity.world.getBlockState(entity.getPosition().add(0,-1,0)).isAir()) {
				wing1.rotateAngleZ = (float)(Math.cos((ageInTicks*0.5f)*0.5f));
				wing2.rotateAngleZ = (float)(Math.cos(0.2+(ageInTicks*0.5f)*0.5f));
				wing3.rotateAngleZ = (float)(-Math.cos((ageInTicks*0.5f)*0.5f));
				wing4.rotateAngleZ = (float)(-Math.cos(0.2+(ageInTicks*0.5f)*0.5f));
				tail.rotateAngleX = (float)(Math.cos((ageInTicks)*0.05f)*0.1f)-(float)Math.toRadians(15);
			} else {
				wing1.rotateAngleZ = (float)Math.toRadians(15);
				wing2.rotateAngleZ = (float)Math.toRadians(15);
				wing3.rotateAngleZ = (float)Math.toRadians(-15);
				wing4.rotateAngleZ = (float)Math.toRadians(-15);
				tail.rotateAngleX = (float)Math.toRadians(-15);
			}
		} else {
			wing1.rotateAngleZ = (float)(Math.cos((ageInTicks)*1f));
			wing2.rotateAngleZ = (float)(Math.cos(0.2+(ageInTicks)*1f));
			wing3.rotateAngleZ = (float)(-Math.cos((ageInTicks)*1f));
			wing4.rotateAngleZ = (float)(-Math.cos(0.2+(ageInTicks)*1f));
			tail.rotateAngleX = (float)(Math.cos((ageInTicks)*0.1f)*0.1f)-(float)Math.toRadians(15);
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrixStack.push();
		matrixStack.rotate(new Quaternion(
				0,180,0,true
		));
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.pop();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	public void setupForGlow() {
		this.wing1.showModel = false;
		this.wing2.showModel = false;
		this.wing3.showModel = false;
		this.wing4.showModel = false;
		this.solid.showModel = false;
		this.translucent.showModel = false;
		this.emissive.showModel = true;
		this.body.showModel = true;
	}
	
	public void setupForGel() {
		this.wing1.showModel = true;
		this.wing2.showModel = true;
		this.wing3.showModel = true;
		this.wing4.showModel = true;
		this.solid.showModel = false;
		this.translucent.showModel = true;
		this.emissive.showModel = false;
		this.body.showModel = true;
	}
	
	public void setupForSolid() {
		this.wing1.showModel = false;
		this.wing2.showModel = false;
		this.wing3.showModel = false;
		this.wing4.showModel = false;
		this.solid.showModel = true;
		this.translucent.showModel = false;
		this.emissive.showModel = false;
		this.body.showModel = true;
	}
}