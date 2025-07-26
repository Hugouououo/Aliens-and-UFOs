package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import com.hugouououo.alienmod.item.ModItems;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AlienModel extends BipedEntityModel<AlienEntity> {

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
        this.hat = modelPart.getChild(EntityModelPartNames.HAT);
        this.body = modelPart.getChild(EntityModelPartNames.BODY);
        this.rightArm = modelPart.getChild(EntityModelPartNames.RIGHT_ARM);
        this.leftArm = modelPart.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightLeg = modelPart.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftLeg = modelPart.getChild(EntityModelPartNames.LEFT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder
                        .create()
                        .uv(0, 0)
                        .cuboid(-4.0F, -2F, -4.0F, 8.0F, 8.0F, 8.0F), // Mesmo cubo
                ModelTransform.pivot(0.0F, 2.0F, 0.0F)); // Mesmo pivot do Blockbench

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder
                        .create()
                        .uv(0, 16)
                        .cuboid(-3.0F, 6F, -1.5F, 6.0F, 10.0F, 3.0F), // Cubo centralizado
                ModelTransform.pivot(0.0F, 11.0F, 0.0F)); // Igual ao Blockbench

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder
                        .create()
                        .uv(18, 16)
                        .cuboid(-1.0F, 4.0F, -1.0F, 2.0F, 10.0F, 2.0F),
                ModelTransform.pivot(-4.0F, 5.0F, 0.0F)); // Braço direito

        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder
                        .create()
                        .uv(26, 16)
                        .cuboid(-1.0F, 4.0F, -1.0F, 2.0F, 10.0F, 2.0F),
                ModelTransform.pivot(4.0F, 5.0F, 0.0F)); // Braço esquerdo

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder
                        .create()
                        .uv(18, 28)
                        .cuboid(-1.0F, 4.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                ModelTransform.pivot(-2.0F, 16.0F, 0.0F)); // Perna direita

        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder
                        .create()
                        .uv(26, 28)
                        .cuboid(-1.0F, 4.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                ModelTransform.pivot(2.0F, 16.0F, 0.0F)); // Perna esquerda

        return TexturedModelData.of(modelData, 64, 64);
    }


    @Override
    public void setAngles(AlienEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity instanceof AlienEntity alienEntity) { // Use pattern matching para cast seguro
            // Levanta a arma se atacando
            if (alienEntity.isAttacking() && alienEntity.getMainHandStack().isOf(ModItems.RAY_GUN)) {
                this.rightArm.pitch = (float) (-Math.PI / 2.0F);
                this.rightArm.yaw = 0.0F; // ou ajuste conforme necessário
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        super.render(matrices, vertices, light, overlay, color);
    }
}