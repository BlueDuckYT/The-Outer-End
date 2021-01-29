package blueduck.outerend.client.entity.model;

import blueduck.outerend.entities.ChorusSquidEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class ChorusSquidModel extends EntityModel<ChorusSquidEntity> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer maw1;
    private final ModelRenderer teeth1;
    private final ModelRenderer maw2;
    private final ModelRenderer teeth2;
    private final ModelRenderer maw3;
    private final ModelRenderer teeth3;
    private final ModelRenderer maw4;
    private final ModelRenderer teeth4;

    public ChorusSquidModel() {
        textureWidth = 128;
        textureHeight = 128;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 48.0F, 0.0F);


        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -34.0F, 0.0F);
        body.addChild(head);
        head.setTextureOffset(48, 0).addBox(-6.0F, -6.0F, 6.0F, 12.0F, 12.0F, 2.0F, 0.0F, false);
        head.setTextureOffset(0, 0).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 14.0F, 12.0F, 0.0F, false);
        head.setTextureOffset(0, 40).addBox(6.0F, -6.0F, -6.0F, 2.0F, 12.0F, 12.0F, 0.0F, false);
        head.setTextureOffset(36, 36).addBox(-8.0F, -6.0F, -6.0F, 2.0F, 12.0F, 12.0F, 0.0F, false);
        head.setTextureOffset(0, 26).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 2.0F, 12.0F, 0.0F, false);
        head.setTextureOffset(48, 14).addBox(-6.0F, -6.0F, -8.0F, 12.0F, 12.0F, 2.0F, 0.0F, false);

        // +x, +z
        maw1 = new ModelRenderer(this);
        maw1.setRotationPoint(0f, 8f, 0f);
        head.addChild(maw1);
        maw1.setTextureOffset(112, 5).addBox(4f, 0f, 0f, 0f, 30f, 4f);
        maw1.setTextureOffset(96, 9).addBox(0f, 0f, 4f, 4f, 30f, 0f);
        maw1.setTextureOffset(0, 0).addBox(0f, 30f, 0f, 4f, 0f, 4f);

        teeth1 = new ModelRenderer(this);
        teeth1.setRotationPoint(4f, 0f, 4f);
        maw1.addChild(teeth1);
        teeth1.rotateAngleY = 3f * 2f * (float) Math.PI / 8f;
        teeth1.setTextureOffset(36, 60).addBox(0f, 0f, 0f, 4f, 30f, 0f, true);

        // -x, +z
        maw2 = new ModelRenderer(this);
        maw2.setRotationPoint(0f, 8f, 0f);
        head.addChild(maw2);
        maw2.setTextureOffset(96, 5).addBox(-4f, 0f, 0f, 0f, 30f, 4f);
        maw2.setTextureOffset(112, 9).addBox(-4f, 0f, 4f, 4f, 30f, 0f);
        maw2.setTextureOffset(0, 0).addBox(-4f, 30f, 0f, 4f, 0f, 4f);

        teeth2 = new ModelRenderer(this);
        teeth2.setRotationPoint(-4f, 0f, 4f);
        maw2.addChild(teeth2);
        teeth2.rotateAngleY = 2f * (float) Math.PI / 8f;
        teeth2.setTextureOffset(36, 60).addBox(0f, 0f, 0f, 4f, 30f, 0f, true);

        // -x -z
        maw3 = new ModelRenderer(this);
        maw3.setRotationPoint(0f, 8f, 0f);
        head.addChild(maw3);
        maw3.setTextureOffset(112, 5).addBox(-4f, 0f, -4f, 0f, 30f, 4f);
        maw3.setTextureOffset(96, 9).addBox(-4f, 0f, -4f, 4f, 30f, 0f);
        maw3.setTextureOffset(0, 0).addBox(-4f, 30f, -4f, 4f, 0f, 4f);

        teeth3 = new ModelRenderer(this);
        teeth3.setRotationPoint(-4f, 0f, -4f);
        maw3.addChild(teeth3);
        teeth3.rotateAngleY = 7f * 2f * (float) Math.PI / 8f;
        teeth3.setTextureOffset(36, 60).addBox(0f, 0f, 0f, 4f, 30f, 0f, true);

        // +x, -z
        maw4 = new ModelRenderer(this);
        maw4.setRotationPoint(0f, 8f, 0f);
        head.addChild(maw4);
        maw4.setTextureOffset(96, 5).addBox(4f, 0f, -4f, 0f, 30f, 4f);
        maw4.setTextureOffset(112, 9).addBox(0f, 0f, -4f, 4f, 30f, 0f);
        maw4.setTextureOffset(0, 0).addBox(0f, 30f, -4f, 4f, 0f, 4f);

        teeth4 = new ModelRenderer(this);
        teeth4.setRotationPoint(4f, 0f, -4f);
        maw4.addChild(teeth4);
        teeth4.rotateAngleY = 5f * 2f * (float) Math.PI / 8f;
        teeth4.setTextureOffset(36, 60).addBox(0f, 0f, 0f, 4f, 30f, 0f, true);
    }

    @Override
    public void setRotationAngles(ChorusSquidEntity squid, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //    tentacleAngle :: [0, 90]
//        float tentacleAngle = (1f + (float) Math.cos(ageInTicks * 0.1)) / 2f * 90f;//squid.getTentacleAngle();
        float partialTicks = ageInTicks - MathHelper.floor(ageInTicks);
        float tentacleAngle = MathHelper.lerp(partialTicks, squid.prevTentacleAngle, squid.tentacleAngle);
//        System.out.println("Age in ticks: " + ageInTicks);
        float theta = tentacleAngle / 90f * (float) Math.PI / 2f;
        Vector3f rotAxis = new Vector3f(1f, 0f, -1f);
        rotAxis.normalize();
        Quaternion rot = new Quaternion(rotAxis, theta, false);
        maw1.rotateAngleX = rot.getX();
        maw1.rotateAngleY = rot.getY();
        maw1.rotateAngleZ = rot.getZ();

        rotAxis.set(1f, 0f, 1f);
        rotAxis.normalize();
        rot = new Quaternion(rotAxis, theta, false);
        maw2.rotateAngleX = rot.getX();
        maw2.rotateAngleY = rot.getY();
        maw2.rotateAngleZ = rot.getZ();

        rotAxis.set(-1f, 0f, 1f);
        rotAxis.normalize();
        rot = new Quaternion(rotAxis, theta, false);
        maw3.rotateAngleX = rot.getX();
        maw3.rotateAngleY = rot.getY();
        maw3.rotateAngleZ = rot.getZ();

        rotAxis.set(-1f, 0f, -1f);
        rotAxis.normalize();
        rot = new Quaternion(rotAxis, theta, false);
        maw4.rotateAngleX = rot.getX();
        maw4.rotateAngleY = rot.getY();
        maw4.rotateAngleZ = rot.getZ();
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}