package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AlienModel extends EntityModel<AlienRenderState> {

    public static final EntityModelLayer ALIEN = new EntityModelLayer(Identifier.of(AlienMod.MOD_ID,"alien"), "main");

    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart root;

    public AlienModel(ModelPart root) {
        super(root);
        this.torso = root.getChild("torso");
        this.head = root.getChild("head");
        this.root = root.getChild("root");


    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0f));
        ModelPartData mantis = root.addChild("alien", ModelPartBuilder.create(), ModelTransform.of(-0.5F, -3.0F, -28.0F, 0.3927F, 0.0F, 0.0F));


        ModelPartData r_leg = modelPartData.addChild("r_leg", ModelPartBuilder.create()
                .uv(24, 17)                                                                                                                                                              //0.4363F
                .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 16.0F, 0.0F, 0f, 0.0F, 0.0F));

        ModelPartData l_leg = modelPartData.addChild("l_leg", ModelPartBuilder.create()
                .uv(24, 28)
                .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 16.0F, 0.0F, 0F, 0.0F, 0.0F));

        ModelPartData r_arm = modelPartData.addChild("r_arm", ModelPartBuilder.create()
                .uv(0, 32)
                .cuboid(-1.0F, 0.5F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 5.5F, 0.0F, 0F, 0.0F, 0.0F));

        ModelPartData l_arm = modelPartData.addChild("l_arm", ModelPartBuilder.create()
                .uv(8, 32)
                .cuboid(-1.0F, 0.5F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 5.5F, 0.0F, 0F, 0.0F, 0.0F));

        ModelPartData torso = modelPartData.addChild("torso", ModelPartBuilder.create()
                .uv(0, 17)
                .cuboid(-4.0F, -19.0F, -2.0F, 8.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-5.0F, -9.0F, -4.0F, 10.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, 0.0F, 0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(AlienRenderState state) {
        super.setAngles(state);
        this.setHeadAngles(state.relativeHeadYaw, state.pitch);

        this.animateWalking(AlienAnimations.ALIEN_ANIM_WALK, state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2f, 2.5f);
        this.animate(AlienAnimations.ALIEN_ANIM_ANGRY);

        //if(AlienEntity.angry)
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}