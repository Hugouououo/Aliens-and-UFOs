package com.hugouououo.alienmod.datagen;

import com.hugouououo.alienmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.ALIEN_STEEL)
                .add(ModBlocks.ALIEN_STEEL_SLAB);

        valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ALIEN_STEEL)
                .add(ModBlocks.ALIEN_STEEL_SLAB);

        valueLookupBuilder(BlockTags.SLABS)
                .add(ModBlocks.ALIEN_STEEL_SLAB);
    }
}
