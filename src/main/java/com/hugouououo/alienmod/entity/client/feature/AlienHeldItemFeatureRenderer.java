package com.hugouououo.alienmod.entity.client.feature;

import com.hugouououo.alienmod.entity.client.AlienModel;
import com.hugouououo.alienmod.entity.client.AlienRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemDisplayContext; // Importação CORRETA para ItemDisplayContext
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.client.model.ModelPart; // Adicionar este import se não estiver presente

public class AlienHeldItemFeatureRenderer extends FeatureRenderer<AlienRenderState, AlienModel> {

    private final ItemRenderer itemRenderer;

    public AlienHeldItemFeatureRenderer(FeatureRendererContext<AlienRenderState, AlienModel> context, ItemModelManager itemModelManager) {
        super(context);
        this.itemRenderer = new ItemRenderer(itemModelManager);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AlienRenderState state, float limbAngle, float limbDistance) {
        com.hugouououo.alienmod.entity.custom.AlienEntity entity = state.getAlienEntity(); //
        matrices.push();
        ItemStack itemStack = entity.getMainHandStack();

        if (!itemStack.isEmpty() && entity.isAttacking()) {

            AlienModel model = this.getContextModel();
            ModelPart rightArm = model.rightArm;
            rightArm.applyTransform(matrices);

            // posicao
            matrices.translate(0F, 0.625F, 0.075F);
            // direcao
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));

            // escala
            matrices.scale(0.75F, 0.75F, 0.75F);

            this.itemRenderer.renderItem(
                    entity, // LivingEntity
                    itemStack, // ItemStack
                    ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, // ItemDisplayContext
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    light,
                    0,
                    entity.getId()
            );
        }

        matrices.pop();
    }
}