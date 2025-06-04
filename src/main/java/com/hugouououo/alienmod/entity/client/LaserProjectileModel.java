package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LaserProjectileModel extends EntityModel<EntityRenderState> {

    public static final EntityModelLayer LASER_PROJECTILE = new EntityModelLayer(Identifier.of(AlienMod.MOD_ID,"laser"), "main");

    private final ModelPart laser;

    public LaserProjectileModel(ModelPart root) {
        super(root);
        this.laser = root.getChild("laser");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData laser = modelPartData.addChild("laser", ModelPartBuilder.create(),
                ModelTransform.of(0.0F, 0F, 0.0F,0f,0f,0f));

        ModelPartData paralelepipedo = laser.addChild("paralelepipedo", ModelPartBuilder
                .create()
                .uv(-3, -1
                ).cuboid(-8.0F, -2.0F, 0.0F, 16.0F, 2.0F, 2.0F,
                        new Dilation(0.0F)), ModelTransform.of(0F, 0.0F, 0.0F, 0.0F, 0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    public ModelPart getModelPart() {
        return this.laser;
    }
}