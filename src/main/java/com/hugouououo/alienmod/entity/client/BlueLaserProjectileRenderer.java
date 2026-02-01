package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.BlueLaserProjectileEntity;
import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;

public class BlueLaserProjectileRenderer extends EntityRenderer<BlueLaserProjectileEntity, BlueLaserProjectileRenderState> {

    protected BlueLaserProjectileModel model;
    public BlueLaserProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new BlueLaserProjectileModel(context.getPart(BlueLaserProjectileModel.BLUE_LASER_PROJECTILE));
    }

    public Identifier getTexture(BlueLaserProjectileRenderState state) {
        return Identifier.of(AlienMod.MOD_ID, "textures/entity/laser/blue_laser.png");
    }

    LivingEntity shooter;

    //@Override
    public void render(BlueLaserProjectileRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push(); // Salva o estado atual da MatrixStack

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-state.initialYaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(state.initialPitch));

        VertexConsumer vertexconsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(this.createRenderState())), true, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }

    @Override
    public void updateRenderState(BlueLaserProjectileEntity entity, BlueLaserProjectileRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        state.initialYaw = entity.getInitialYaw();
        state.initialPitch = entity.getInitialPitch();
    }

    @Override
    public BlueLaserProjectileRenderState createRenderState() {
        return new BlueLaserProjectileRenderState();
    }

    protected int getBlockLight(BlueLaserProjectileEntity blueLaserProjectileEntity, BlockPos blockPos) {
        return 15;
    }
}

