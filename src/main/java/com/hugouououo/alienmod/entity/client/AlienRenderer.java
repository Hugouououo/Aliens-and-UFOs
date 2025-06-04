package com.hugouououo.alienmod.entity.client;

import com.google.common.collect.Maps;
import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import com.hugouououo.alienmod.entity.client.AlienRenderState;
import com.hugouououo.alienmod.entity.client.AlienModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import java.util.Map;

public class AlienRenderer extends MobEntityRenderer<AlienEntity, AlienRenderState, AlienModel> {

    public AlienRenderer(EntityRendererFactory.Context context) {
        super(context, new AlienModel(context.getPart(AlienModel.ALIEN)), 0.75f);
    }

    @Override
    public Identifier getTexture(AlienRenderState state) {
        return Identifier.of(AlienMod.MOD_ID, "textures/entity/alien/alien.png");
    }

    @Override
    public void render(AlienRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(state.baby) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(0.85f, 0.85f, 0.85f);   //praticamente altera as proporções dele
        }

        super.render(state, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public AlienRenderState createRenderState() {
        return new AlienRenderState();
    }

    @Override
    public void updateRenderState(AlienEntity livingEntity, AlienRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
        //livingEntityRenderState.variant = livingEntity.getVariant();
    }
}