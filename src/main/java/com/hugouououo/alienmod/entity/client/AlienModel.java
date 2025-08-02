package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import com.hugouououo.alienmod.item.ModItems;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AlienModel extends BipedEntityModel<AlienEntity> {

    public static final EntityModelLayer ALIEN = new EntityModelLayer(Identifier.of(AlienMod.MOD_ID, "alien"), "main");

    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart right_arm;
    public final ModelPart left_arm;
    public final ModelPart right_leg;
    public final ModelPart left_leg;
    public final ModelPart hat;

    public AlienModel(ModelPart root) {
        super(root);
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
        this.right_arm = root.getChild("right_arm");
        this.left_arm = root.getChild("left_arm");
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.hat = root.getChild("hat");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(18, 28).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 16.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(26, 28).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 16.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(18, 16).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 7.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(26, 16).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 7.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-3.0F, -5.0F, -1.5F, 6.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 11.0F, 0.0F));

        ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create(), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        right_leg.render(matrices, vertices, light, overlay, color);
        left_leg.render(matrices, vertices, light, overlay, color);
        right_arm.render(matrices, vertices, light, overlay, color);
        left_arm.render(matrices, vertices, light, overlay, color);
        head.render(matrices, vertices, light, overlay, color);
        body.render(matrices, vertices, light, overlay, color);
        hat.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public void setAngles(AlienEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        // Resetar as rotações para o estado padrão (T-pose ou pose original)
        // Usando pitch, yaw, roll diretamente
        this.head.pitch = 0.0F;
        this.head.yaw = 0.0F;
        this.head.roll = 0.0F; // Geralmente roll não é necessário para resetar, mas é uma opção

        this.body.pitch = 0.0F;
        this.body.yaw = 0.0F;
        this.body.roll = 0.0F;

        this.right_arm.pitch = 0.0F;
        this.right_arm.yaw = 0.0F;
        this.right_arm.roll = 0.0F;

        this.left_arm.pitch = 0.0F;
        this.left_arm.yaw = 0.0F;
        this.left_arm.roll = 0.0F;

        this.right_leg.pitch = 0.0F;
        this.right_leg.yaw = 0.0F;
        this.right_leg.roll = 0.0F;

        this.left_leg.pitch = 0.0F;
        this.left_leg.yaw = 0.0F;
        this.left_leg.roll = 0.0F;

        this.hat.pitch = 0.0F; // Se o hat precisar de rotações
        this.hat.yaw = 0.0F;
        this.hat.roll = 0.0F;


        // 2. Animação da cabeça (o mob "olhando" para onde o jogador olha)
        this.head.yaw = headYaw * ((float)Math.PI / 180F); // Converter graus para radianos
        this.head.pitch = headPitch * ((float)Math.PI / 180F);

        // 3. Animação de caminhar (pernas e braços)
        this.right_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.left_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + (float)Math.PI) * 1.4F * limbDistance;

        this.right_arm.pitch = MathHelper.cos(limbAngle * 0.6662F + (float)Math.PI) * 1.4F * limbDistance;
        this.left_arm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        // 4. Animação de ataque (sua lógica existente)
        if (entity.isAttacking() && entity.getMainHandStack().isOf(ModItems.RAY_GUN)) {
            this.right_arm.pitch = (float) (-Math.PI / 2.0F);
            this.right_arm.yaw = 0.0F;
        }
    }

}


//    @Override
//    public void setAngles(AlienEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
//        super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
//
//        // Animação de ataque
//        if (entity.isAttacking() && entity.getMainHandStack().isOf(ModItems.RAY_GUN)) {
//            this.rightArm.pitch = (float) (-Math.PI / 2.0F);
//            this.rightArm.yaw = 0.0F;
//        }
//    }

