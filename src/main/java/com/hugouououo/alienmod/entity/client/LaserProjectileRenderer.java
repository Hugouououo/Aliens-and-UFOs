package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;

public class LaserProjectileRenderer extends EntityRenderer<LaserProjectileEntity, EntityRenderState> {

    protected LaserProjectileModel model;
    public LaserProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new LaserProjectileModel(context.getPart(LaserProjectileModel.LASER_PROJECTILE));
    }

    @Override
    public void render(EntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        // chatgpt
        Vec3d velocity = state.positionOffset != null ? state.positionOffset : Vec3d.ZERO;
        float yaw = 0;
        float pitch = 0;
        if (velocity.lengthSquared() > 0.0001) {
            yaw = (float) (Math.atan2(velocity.x, velocity.z) * (180F / Math.PI)) - 90.0f;
            pitch = (float) (Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)) * (180F / Math.PI));

            matrices.multiply(new Quaternionf().rotationY((float) Math.toRadians(yaw)));
            matrices.multiply(new Quaternionf().rotationZ((float) Math.toRadians(pitch)));
        }
        // =-=-=-=

        VertexConsumer vertexconsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(Identifier.of(AlienMod.MOD_ID, "textures/entity/laser/laser.png")), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
        super.render(state, matrices, vertexConsumers, light);
    }

    protected int getBlockLight(LaserProjectileEntity laserProjectileEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}

