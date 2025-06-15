package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import com.hugouououo.alienmod.item.ModItems;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AlienModel extends EntityModel<AlienRenderState> {

    public static final EntityModelLayer ALIEN = new EntityModelLayer(Identifier.of(AlienMod.MOD_ID,"alien"), "main");

    public final ModelPart head;
    public final ModelPart hat;
    public final ModelPart body;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    public AlienModel(ModelPart modelPart, ModelPart head, ModelPart hat, ModelPart body, ModelPart rightArm, ModelPart leftArm, ModelPart rightLeg, ModelPart leftLeg) {
        super(modelPart, RenderLayer::getEntityCutoutNoCull);
        this.head = head;
        this.hat = hat;
        this.body = body;
        this.rightArm = rightArm;
        this.leftArm = leftArm;
        this.rightLeg = rightLeg;
        this.leftLeg = leftLeg;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0f));
        //ModelPartData alien = root.addChild("alien", ModelPartBuilder.create(), ModelTransform.of(-0.5F, -3.0F, -28.0F, 0.3927F, 0.0F, 0.0F));
        modelPartData.addChild("right_leg", ModelPartBuilder.create()
                .uv(24, 17)                                                                                                                                                              //0.4363F
                .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 16.0F, 0.0F, 0f, 0.0F, 0.0F));
        modelPartData.addChild("left_leg", ModelPartBuilder.create()
                .uv(24, 28)
                .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 16.0F, 0.0F, 0F, 0.0F, 0.0F));
        modelPartData.addChild("right_arm", ModelPartBuilder.create()
                .uv(0, 32)
                .cuboid(-1.0F, 0.5F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 5.5F, 0.0F, 0F, 0.0F, 0.0F));
        modelPartData.addChild("left_arm", ModelPartBuilder.create()
                .uv(8, 32)
                .cuboid(-1.0F, 0.5F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 5.5F, 0.0F, 0F, 0.0F, 0.0F));
        modelPartData.addChild("body", ModelPartBuilder.create()
                .uv(0, 17)
                .cuboid(-4.0F, -19.0F, -2.0F, 8.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0F, 0.0F, 0.0F));
        modelPartData.addChild("head", ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-5.0F, -9.0F, -4.0F, 10.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, 0.0F, 0F, 0.0F, 0.0F));
        modelPartData.addChild("hat", ModelPartBuilder.create(), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 64, 64);
    }


    @Override
    public void setAngles(AlienRenderState renderState) {
        super.setAngles(renderState);

        // Animação de Andar!!!
        this.animateWalking(AlienAnimations.ALIEN_ANIM_WALK, renderState.limbSwingAnimationProgress, renderState.limbSwingAmplitude, 2f, 2.5f);

        //this.hat.visible = false;

        AlienEntity entity = renderState.getAlienEntity();

        float animationProgress = renderState.age + renderState.tickDelta;

        if (entity.idleAnimationState.isRunning()) {
            //this.body.roll = 0.05F * MathHelper.sin(animationProgress * 0.1F);
            this.head.pitch += 0.05F * MathHelper.sin(animationProgress * 0.15F);
        } else {
            this.body.roll = 0.0F;
        }

//        if (renderState.handSwingProgress > 0.0F) {
//
//            if (entity.isAttacking() && entity.getMainHandStack().isOf(ModItems.RAY_GUN)) {
//                float f = renderState.handSwingProgress;
//                float g = MathHelper.sin(f * (float)Math.PI);
//                float h = MathHelper.sin((1.0F - (1.0F - f) * (1.0F - f)) * (float)Math.PI);
//
//                this.rightArm.roll = 0.0F;
//                this.leftArm.roll = 0.0F;
//                this.rightArm.yaw = -(0.1F - g * 0.6F);
//                this.leftArm.yaw = 0.1F - g * 0.6F;
//                this.rightArm.pitch = (float) (-Math.PI / 2);
//                this.leftArm.pitch = (float) (-Math.PI / 2);
//                this.rightArm.pitch -= g * 1.2F - h * 0.4F;
//                this.leftArm.pitch -= g * 1.2F - h * 0.4F;
//                this.hat.yaw = 0f;
//                this.hat.pitch = 0f;
//                this.body.yaw = MathHelper.cos(animationProgress * 0.09F) * 0.05F;
//                this.body.pitch = MathHelper.sin(animationProgress * 0.09F) * 0.05F;
//            }
//        }
        if (entity.isAttacking() && entity.getMainHandStack().isOf(ModItems.RAY_GUN)) {
            this.rightArm.pitch = -MathHelper.PI / 2;
            this.rightArm.yaw = 0.0F;
        }

    }
}