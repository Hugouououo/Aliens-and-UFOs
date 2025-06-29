package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.entity.LivingEntity;

public class LaserProjectileRenderer extends EntityRenderer<LaserProjectileEntity, LaserProjectileRenderState> {

    protected LaserProjectileModel model;
    public LaserProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new LaserProjectileModel(context.getPart(LaserProjectileModel.LASER_PROJECTILE));
    }

    protected Identifier getTexture(LaserProjectileRenderState state) {
        return Identifier.of(AlienMod.MOD_ID, "textures/entity/laser/laser.png");
    }

    LivingEntity shooter;

    @Override
    public void render(LaserProjectileRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push(); // Salva o estado atual da MatrixStack

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-state.initialYaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(state.initialPitch));

        VertexConsumer vertexconsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(this.createRenderState())), true, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }

    @Override
    public void updateRenderState(LaserProjectileEntity entity, LaserProjectileRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        state.initialYaw = entity.getInitialYaw();
        state.initialPitch = entity.getInitialPitch();
    }

    @Override
    public LaserProjectileRenderState createRenderState() {
        return new LaserProjectileRenderState();
    }

    protected int getBlockLight(LaserProjectileEntity laserProjectileEntity, BlockPos blockPos) {
        return 15;
    }
}

