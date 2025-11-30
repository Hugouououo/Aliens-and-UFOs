package com.hugouououo.alienmod.mixin;

import com.hugouououo.alienmod.item.custom.BlasterItem;
import com.hugouououo.alienmod.item.custom.RayGunItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    // Injetamos no métoodo que PREPARA o estado, onde ainda temos acesso ao Player e ao Item
    @Inject(method = "updateHandState", at = @At("TAIL"))
    private void alienmod$injectHandState(AbstractClientPlayerEntity player, PlayerEntityRenderState.HandState handState, Hand hand, CallbackInfo ci) {
        ItemStack stack = player.getStackInHand(hand);

        // Verificamos se é o seu item
        if (stack.getItem() instanceof RayGunItem || stack.getItem() instanceof BlasterItem) {
            // Forçamos o estado 'hasChargedCrossbow' para verdadeiro.
            // O méetodo getArmPose original vai ler isso e retornar ArmPose.CROSSBOW_HOLD automaticamente.
            handState.hasChargedCrossbow = true;
        }
    }
}

//@Mixin(PlayerEntityRenderer.class)
//public class PlayerEntityRendererMixin {
//
//    @Inject(method = "getArmPose", at=@At("HEAD"), cancellable = true)
//
//    private static void getCustomArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
//
//        ItemStack stack = player.getStackInHand(hand);
//        if (stack.getItem() instanceof RayGunItem || stack.getItem() instanceof BlasterItem) {
//            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
//        }
//    }
//}

//@Mixin(PlayerEntityRenderer.class)
//@Environment(EnvType.CLIENT)
//public abstract class PlayerEntityRendererMixin {
//
//    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
//    private void alienmod$injectRayGunPose(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
//        // Obter o modelo do jogador (PlayerEntityModel)
//        // Isso é seguro porque `this` é uma instância de PlayerEntityRenderer.
//        PlayerEntityModel<AbstractClientPlayerEntity> model = ((PlayerEntityRenderer)(Object)this).getModel();
//
//        ItemStack mainHandStack = player.getMainHandStack();
//        ItemStack offHandStack = player.getOffHandStack();
//
//        // Define a pose padrão que você deseja
//        BipedEntityModel.ArmPose rayGunArmPose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
//
//        // Verifica se a RayGun está na mão principal ou na secundária e aplica a pose
//        if (player.getMainArm() == Arm.RIGHT) { // Se a mão principal do jogador é a direita
//            if (mainHandStack.getItem() instanceof RayGunItem) {
//                model.rightArmPose = rayGunArmPose;
//            } else {
//                // Se a RayGun não está na mão principal, mas está na secundária,
//                // e você quer que a outra mão também adote a pose de arco (como no vanilla),
//                // você pode ajustar aqui.
//                // No entanto, para ser mais preciso:
//                if (offHandStack.getItem() instanceof RayGunItem) {
//                    model.leftArmPose = rayGunArmPose;
//                    // Se você quiser que a mão principal também se ajuste ao segurar algo na off-hand (como o arco),
//                    // você pode adicionar lógica para model.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
//                    // ou alguma outra pose de 'prontidão'.
//                }
//            }
//        } else { // Se a mão principal do jogador é a esquerda
//            if (mainHandStack.getItem() instanceof RayGunItem) {
//                model.leftArmPose = rayGunArmPose;
//            } else {
//                if (offHandStack.getItem() instanceof RayGunItem) {
//                    model.rightArmPose = rayGunArmPose;
//                    // Similarmente para a mão direita se a RayGun estiver na mão esquerda
//                }
//            }
//        }
//    }
//}