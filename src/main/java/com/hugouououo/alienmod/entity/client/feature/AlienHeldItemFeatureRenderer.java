package com.hugouououo.alienmod.entity.client.feature;

import com.hugouououo.alienmod.entity.client.AlienModel;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.client.model.ModelPart;

public class AlienHeldItemFeatureRenderer extends FeatureRenderer<AlienEntity, AlienModel> {

    private final ItemRenderer itemRenderer;

    public AlienHeldItemFeatureRenderer(FeatureRendererContext<AlienEntity, AlienModel> context) {
        super(context);
        this.itemRenderer = MinecraftClient.getInstance().getItemRenderer();  //new ItemRenderer(itemModelManager);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AlienEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        ItemStack itemStack = entity.getMainHandStack();

        if (entity.isAttacking()) {

            AlienModel model = this.getContextModel();
            ModelPart rightArm = model.rightArm;
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(rightArm.pitch));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(rightArm.yaw));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(rightArm.roll));
            matrices.translate(rightArm.pivotX / 16.0F, rightArm.pivotY / 16.0F, rightArm.pivotZ / 16.0F);

            // posicao          esq-dir  | cima-baixo | frente-tras
            matrices.translate(0.085F, 0.25F, 0.4F);
            // direcao
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));

            // escala
            matrices.scale(0.75F, 0.75F, 0.75F);

            itemRenderer.renderItem(
                    entity,
                    itemStack,
                    ModelTransformationMode.FIRST_PERSON_RIGHT_HAND,
                    false,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    light,
                    OverlayTexture.DEFAULT_UV,
                    entity.getId()
            );
        }

        matrices.pop();
    }
}