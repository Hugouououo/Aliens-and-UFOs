package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;

public class LaserProjectileModel extends EntityModel<LaserProjectileRenderState> {

    public static final EntityModelLayer LASER_PROJECTILE = new EntityModelLayer(Identifier.of(AlienMod.MOD_ID,"laser"), "main");

    private final ModelPart laser;

    public LaserProjectileModel(ModelPart root) {
        super(root);
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
}