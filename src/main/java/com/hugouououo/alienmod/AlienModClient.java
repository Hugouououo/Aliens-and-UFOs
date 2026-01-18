package com.hugouououo.alienmod;

import com.hugouououo.alienmod.block.ModBlocks;
//import com.hugouououo.alienmod.block.entity.ModBlockEntities;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.entity.client.*;
import com.hugouououo.alienmod.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
//import net.fabricmc.fabric.api.blockrenderlayer.v1.Map;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
//import net.minecraft.client.data.ModelProvider;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
//import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
//import com.hugouououo.alienmod.block.entity.client.AlienChestModelRenderer;

public class AlienModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        //Transparência nos blocos
        BlockRenderLayerMap.putBlock(ModBlocks.ALIEN_GOO_BLOCK, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModBlocks.ALIEN_GLASS, BlockRenderLayer.TRANSLUCENT);

        // Alien
        EntityModelLayerRegistry.registerModelLayer(AlienModel.ALIEN, AlienModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ALIEN, AlienRenderer::new);

        // Laser
        EntityModelLayerRegistry.registerModelLayer(LaserProjectileModel.LASER_PROJECTILE, LaserProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LASER_PROJECTILE, LaserProjectileRenderer::new);
        // Laser azul
        EntityModelLayerRegistry.registerModelLayer(BlueLaserProjectileModel.BLUE_LASER_PROJECTILE, BlueLaserProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BLUE_LASER_PROJECTILE, BlueLaserProjectileRenderer::new);

        // BlockEntityRendererFactories.register(ModBlockEntities.ALIEN_CHEST, AlienChestModelRenderer::new);

//        ModelLoadingPlugin.register(pluginContext -> {
//            pluginContext.addModels(
//                    Identifier.of(AlienMod.MOD_ID, "item/blaster_2d"),
//                    Identifier.of(AlienMod.MOD_ID, "item/ray_gun_2d")
//            );
//        });
    }

}
