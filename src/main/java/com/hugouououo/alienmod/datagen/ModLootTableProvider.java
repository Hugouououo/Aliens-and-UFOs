package com.hugouououo.alienmod.datagen;

import com.hugouououo.alienmod.block.ModBlocks;
import com.hugouououo.alienmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        addDrop(ModBlocks.ALIEN_GOO_BLOCK, oreDrops(ModBlocks.ALIEN_GOO_BLOCK, ModItems.ALIEN_GOO));
        addDrop(ModBlocks.ALIEN_STEEL);

    }
}
        //multipleOreDrops
