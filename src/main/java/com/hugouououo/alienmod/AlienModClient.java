package com.hugouououo.alienmod;

import com.hugouououo.alienmod.block.ModBlocks;
//import com.hugouououo.alienmod.block.entity.ModBlockEntities;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.entity.client.*;
import com.hugouououo.alienmod.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
//import net.minecraft.client.data.ModelProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
//import com.hugouououo.alienmod.block.entity.client.AlienChestModelRenderer;

public class AlienModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        //Transparência nos blocos
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ALIEN_GOO_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ALIEN_GLASS, RenderLayer.getTranslucent());

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

    }

}
