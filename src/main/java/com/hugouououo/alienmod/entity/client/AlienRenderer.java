package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.client.feature.AlienHeldItemFeatureRenderer;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import com.hugouououo.alienmod.item.ModItems;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

public class AlienRenderer extends BipedEntityRenderer<AlienEntity, AlienModel> {

    public AlienRenderer(EntityRendererFactory.Context context) {
        super(context, AlienRenderer.createModel(context), 0.55f);
        // Remove o renderer padrão
        this.features.removeIf(renderer -> renderer instanceof HeldItemFeatureRenderer);
        // Adiciona o especifico do alien
        this.addFeature(new AlienHeldItemFeatureRenderer(this /*, context.getItemModelManager()*/));
    }

    private static AlienModel createModel(EntityRendererFactory.Context context) {
        ModelPart root = context.getPart(AlienModel.ALIEN);
        return new AlienModel(root);
    }

    @Override
    public Identifier getTexture(AlienEntity entity) {
        return Identifier.of(AlienMod.MOD_ID, "textures/entity/alien/alien.png");
    }

//    @Override
//    public void updateRenderState(AlienEntity alienEntity, AlienRenderState renderState, float tickDelta) {
//        super.updateRenderState(alienEntity, renderState, tickDelta);
//
//        renderState.setAlienEntity(alienEntity);
//        renderState.tickDelta = tickDelta;
//        renderState.handSwingProgress = alienEntity.handSwingProgress;
//        renderState.setAttacking(alienEntity.isAttacking());
//    }

    @Override
    public void render(AlienEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(0.85f, 0.85f, 0.85f);
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}