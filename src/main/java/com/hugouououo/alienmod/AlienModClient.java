package com.hugouououo.alienmod;

import com.hugouououo.alienmod.block.ModBlocks;
import com.hugouououo.alienmod.block.entity.ModBlockEntities;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.entity.client.AlienModel;
import com.hugouououo.alienmod.entity.client.AlienRenderer;
import com.hugouououo.alienmod.entity.client.LaserProjectileModel;
import com.hugouououo.alienmod.entity.client.LaserProjectileRenderer;
import com.hugouououo.alienmod.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.data.ModelProvider;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import com.hugouououo.alienmod.block.entity.client.AlienChestModelRenderer;
import net.minecraft.util.Identifier;

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

        // erro aqui: BlockEntityRendererFactories.register(ModBlockEntities.ALIEN_CHEST, AlienChestModelRenderer::new);

    }

}
