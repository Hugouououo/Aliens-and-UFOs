package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class AlienRenderer extends MobEntityRenderer<AlienEntity, AlienRenderState, AlienModel> {

    public AlienRenderer(EntityRendererFactory.Context context) {
        super(context, createModel(context), 0.55f);
    }

    private static AlienModel createModel(EntityRendererFactory.Context context) {
        ModelPart root = context.getPart(AlienModel.ALIEN);
        return new AlienModel(
                root,
                root.getChild("head"),
                root.getChild("hat"),
                root.getChild("body"),
                root.getChild("right_arm"),
                root.getChild("left_arm"),
                root.getChild("right_leg"),
                root.getChild("left_leg")
        );
    }

    @Override
    public Identifier getTexture(AlienRenderState state) {
        return Identifier.of(AlienMod.MOD_ID, "textures/entity/alien/alien.png");
    }

    public void render(AlienRenderState state, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        matrices.scale(0.85f, 0.85f, 0.85f);
        super.render(state, matrices, provider, light);
    }

    @Override
    public AlienRenderState createRenderState() {
        return new AlienRenderState();
    }

    @Override
    public void updateRenderState(AlienEntity alienEntity, AlienRenderState renderState, float tickDelta) {
        super.updateRenderState(alienEntity, renderState, tickDelta);

        renderState.setAlienEntity(alienEntity);
        renderState.tickDelta = tickDelta;
        renderState.handSwingProgress = alienEntity.handSwingProgress;
    }

//    //@Override
//    protected BipedEntityModel.ArmPose getArmPose(AlienEntity alienEntity, Arm arm) {
//        if (alienEntity.getMainArm() == arm && alienEntity.isAttacking() && alienEntity.getMainHandStack().isOf(ModItems.RAY_GUN)) {
//            return BipedEntityModel.ArmPose.ITEM;
//        }
//        return BipedEntityModel.ArmPose.EMPTY;
//    }

}

//package com.hugouououo.alienmod.entity.client;
//
//import com.hugouououo.alienmod.AlienMod;
//import com.hugouououo.alienmod.entity.custom.AlienEntity;
//import com.hugouououo.alienmod.item.ModItems;
//import net.minecraft.client.render.VertexConsumerProvider;
//import net.minecraft.client.render.entity.EntityRendererFactory;
//import net.minecraft.client.render.entity.MobEntityRenderer;
//import net.minecraft.client.render.entity.state.LivingEntityRenderState;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.item.Items;
//import net.minecraft.util.Arm;
//import net.minecraft.util.Identifier;
//
//public class AlienRenderer extends MobEntityRenderer<AlienEntity, AlienModel> { // CORRIGIDO AQUI!
//
//    public AlienRenderer(EntityRendererFactory.Context context) {
//        super(context, new AlienModel(context.getPart(AlienModel.ALIEN)), 0.75f);
//    }
//
//    @Override
//    public Identifier getTexture(AlienRenderState state) {
//        return Identifier.of(AlienMod.MOD_ID, "textures/entity/alien/alien.png");
//    }
//
//    @Override
//    public void render(AlienRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
//        matrixStack.scale(0.85f, 0.85f, 0.85f);   //praticamente altera as proporções dele
//        super.render(state, matrixStack, vertexConsumerProvider, i);
//    }
//
//    @Override
//    public AlienRenderState createRenderState() {
//        return new AlienRenderState();
//    }
//
//    @Override
//    public void updateRenderState(AlienEntity alienEntity, AlienRenderState alienEntityRenderState, float f) {
//        super.updateRenderState(alienEntity, alienEntityRenderState, f);
//
//        alienEntityRenderState.idleAnimationState.copyFrom(alienEntity.idleAnimationState);
//        alienEntityRenderState.attacking = alienEntity.isAttacking();
//        alienEntityRenderState.holdingRayGun = alienEntity.getMainHandStack().isOf(Items.BOW);
//    }
//
//    protected AlienModel.ArmPose getArmPose(AlienEntity alienEntity, Arm arm) {
//        return alienEntity.getMainArm() == arm && alienEntity.isAttacking() && alienEntity.getMainHandStack().isOf(ModItems.RAY_GUN)
//                ? AlienModel.ArmPose.BOW_AND_ARROW
//                : AlienModel.ArmPose.EMPTY;
//    }
//
//}