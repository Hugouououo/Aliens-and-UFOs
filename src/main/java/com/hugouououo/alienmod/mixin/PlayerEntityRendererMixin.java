package com.hugouououo.alienmod.mixin;

import com.hugouououo.alienmod.item.custom.RayGunItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class PlayerEntityRendererMixin {

    @Inject(method = "setModelPose", at = @At("HEAD"))
    private void injectRayGunPose(AbstractClientPlayerEntity player, CallbackInfo ci) {
        ItemStack mainStack = player.getMainHandStack();
        ItemStack offStack = player.getOffHandStack();

        if (mainStack.getItem() instanceof RayGunItem || offStack.getItem() instanceof RayGunItem) {
            PlayerEntityModel<AbstractClientPlayerEntity> model = ((PlayerEntityRenderer)(Object)this).getModel();

            BipedEntityModel.ArmPose rayPose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;

            if (player.getMainArm() == Arm.RIGHT) {
                model.rightArmPose = mainStack.getItem() instanceof RayGunItem ? rayPose : model.rightArmPose;
                model.leftArmPose = offStack.getItem() instanceof RayGunItem ? rayPose : model.leftArmPose;
            } else {
                model.leftArmPose = mainStack.getItem() instanceof RayGunItem ? rayPose : model.leftArmPose;
                model.rightArmPose = offStack.getItem() instanceof RayGunItem ? rayPose : model.rightArmPose;
            }
        }
    }
}
