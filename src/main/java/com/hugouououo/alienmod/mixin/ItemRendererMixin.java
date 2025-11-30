package com.hugouououo.alienmod.mixin;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.item.ModItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    // Busca o modelo registrado lá no AlienModClient
    private BakedModel getExtraModel(String modelName) {
        // CORREÇÃO AQUI: Mudamos de "inventory" para "standalone"
        // Modelos carregados via addModels() são salvos como "standalone"
        return MinecraftClient.getInstance().getBakedModelManager()
                .getModel(new ModelIdentifier(Identifier.of(AlienMod.MOD_ID, "item/" + modelName), "standalone"));
    }

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    public BakedModel use2DModel(BakedModel originalModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {

        // Se for o BLASTER e estiver na mão ou GUI, usa o modelo 2D
        if (stack.getItem() == ModItems.BLASTER) {
            if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED) {
                return getExtraModel("blaster_2d");
            }
        }

        // Se for a RAY_GUN e estiver na mão ou GUI, usa o modelo 2D
        if (stack.getItem() == ModItems.RAY_GUN) {
            if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED) {
                return getExtraModel("ray_gun_2d");
            }
        }

        return originalModel;
    }
}