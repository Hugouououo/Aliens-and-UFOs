package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.BlueLaserProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BlueLaserProjectileModel extends EntityModel<BlueLaserProjectileEntity> {

    public static final EntityModelLayer BLUE_LASER_PROJECTILE = new EntityModelLayer(Identifier.of(AlienMod.MOD_ID,"blue_laser"), "main");

    private final ModelPart laser;

    public BlueLaserProjectileModel(ModelPart root) {
        super();
        this.laser = root.getChild("laser");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData laser = modelPartData.addChild("laser", ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.5F, 0.0F,0f,0f,0f));
        return TexturedModelData.of(modelData, 32, 32);
    }
    
    public ModelPart getModelPart() {
        return this.laser;
    }

    @Override
    public void setAngles(BlueLaserProjectileEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        laser.render(matrices, vertices, light, overlay, color);
    }
}