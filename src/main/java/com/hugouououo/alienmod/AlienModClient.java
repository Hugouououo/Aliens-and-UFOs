package com.hugouououo.alienmod;

import com.hugouououo.alienmod.block.ModBlocks;
import com.hugouououo.alienmod.block.entity.ModBlockEntities;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.entity.client.AlienModel;
import com.hugouououo.alienmod.entity.client.AlienRenderer;
import com.hugouououo.alienmod.entity.client.LaserProjectileModel;
import com.hugouououo.alienmod.entity.client.LaserProjectileRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import com.hugouououo.alienmod.block.entity.client.AlienChestModelRenderer;

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

        // erro aqui: BlockEntityRendererFactories.register(ModBlockEntities.ALIEN_CHEST, AlienChestModelRenderer::new);


    }
}
