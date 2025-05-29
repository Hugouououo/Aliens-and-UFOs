package com.hugouououo.alienmod.datagen;

import com.hugouououo.alienmod.block.ModBlocks;
import com.hugouououo.alienmod.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ALIEN_GOO_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ALIEN_STEEL);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(ModItems.ALIEN_DEVICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ALIEN_GOO, Models.GENERATED);
        itemModelGenerator.register(ModItems.ALIEN_STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAY_GUN, Models.HANDHELD);
    }
}
