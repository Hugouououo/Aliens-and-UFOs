package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.BlueLaserProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;

public class BlueLaserProjectileRenderer extends EntityRenderer<BlueLaserProjectileEntity> {

    protected BlueLaserProjectileModel model;
    public BlueLaserProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new BlueLaserProjectileModel(context.getPart(BlueLaserProjectileModel.BLUE_LASER_PROJECTILE));
    }

    public Identifier getTexture(BlueLaserProjectileEntity entity) {
        return Identifier.of(AlienMod.MOD_ID, "textures/entity/laser/blue_laser.png");
    }

    @Override
    public void render(BlueLaserProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push(); // Salva o estado atual da MatrixStack

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-entity.getInitialYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getInitialPitch()));

        VertexConsumer vertexconsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), true, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }

    protected int getBlockLight(BlueLaserProjectileEntity laserProjectileEntity, BlockPos blockPos) {
        return 15;
    }
}

