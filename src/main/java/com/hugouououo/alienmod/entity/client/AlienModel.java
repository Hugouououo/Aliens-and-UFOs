package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import com.hugouououo.alienmod.item.ModItems;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class AlienModel extends BipedEntityModel<AlienRenderState> {

    public static final EntityModelLayer ALIEN = new EntityModelLayer(Identifier.of(AlienMod.MOD_ID,"alien"), "main");

    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;
    public final ModelPart hat;

    public AlienModel(ModelPart modelPart) {
        super(modelPart);
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
        this.hat = this.head.getChild(EntityModelPartNames.HAT);
        this.body = modelPart.getChild(EntityModelPartNames.BODY);
        this.rightArm = modelPart.getChild(EntityModelPartNames.RIGHT_ARM);
        this.leftArm = modelPart.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightLeg = modelPart.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftLeg = modelPart.getChild(EntityModelPartNames.LEFT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData modelPartData2 = modelPartData.addChild(
                EntityModelPartNames.HEAD, ModelPartBuilder
                        .create()
                        .uv(0, 0)
                        .cuboid(-4.0F, -7.0F, -5.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 1.0F));

        modelPartData2.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder
                .create()
                .uv(18, 28)
                .cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 16.0F, 0.0f));

        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder
                .create()
                .uv(26, 28)
                .cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 16.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder
                .create()
                .uv(18, 16)
                .cuboid(0.0F, 0.5F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 5.5F, 0.0f));

        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder
                .create()
                .uv(26, 16)
                .cuboid(-2.0F, 0.5F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 5.5F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder
                .create()
                .uv(0, 16)
                .cuboid(-3.0F, -18.0F, -1.5F, 6.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(AlienRenderState renderState) {
        super.setAngles(renderState);
        AlienEntity entity = renderState.getAlienEntity();

        // Levanta a arma se atacando
        if (entity.isAttacking() && entity.getMainHandStack().isOf(ModItems.RAY_GUN)) {
            this.rightArm.pitch = (float) (-Math.PI / 2.0F);
            this.rightArm.yaw = 0.0F;
        }
    }
}