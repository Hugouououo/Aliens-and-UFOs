package com.hugouououo.alienmod.datagen;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.block.ModBlocks;
import com.hugouououo.alienmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {

                // STEEL INGOT -> STEEL BLOCK
                createShaped(RecipeCategory.MISC, ModBlocks.ALIEN_STEEL)
                        .pattern("II")
                        .pattern("II")
                        .input('I', ModItems.ALIEN_STEEL_INGOT)
                        .criterion(hasItem(ModItems.ALIEN_STEEL_INGOT), conditionsFromItem(ModItems.ALIEN_STEEL_INGOT))
                        .offerTo(exporter);

                // STEEL BLOCK -> STEEL INGOT
                createShapeless(RecipeCategory.MISC, ModItems.ALIEN_STEEL_INGOT,4)
                        .input(ModBlocks.ALIEN_STEEL)
                        .criterion(hasItem(ModBlocks.ALIEN_STEEL), conditionsFromItem(ModBlocks.ALIEN_STEEL))
                        .offerTo(exporter);

                // GOO -> GOO BLOCK
                createShaped(RecipeCategory.MISC, ModBlocks.ALIEN_GOO_BLOCK)
                        .pattern("GG")
                        .pattern("GG")
                        .input('G', ModItems.ALIEN_GOO)
                        .criterion(hasItem(ModItems.ALIEN_GOO), conditionsFromItem(ModItems.ALIEN_GOO))
                        .offerTo(exporter);

                // GOO BLOCK -> GOO
                createShapeless(RecipeCategory.MISC, ModItems.ALIEN_GOO,4)
                        .input(ModBlocks.ALIEN_GOO_BLOCK)
                        .criterion(hasItem(ModBlocks.ALIEN_GOO_BLOCK), conditionsFromItem(ModBlocks.ALIEN_GOO_BLOCK))
                        //.offerTo(exporter, Identifier.of(AlienMod.MOD_ID, "alien_goo_from_alien_goo_block"));
                        .offerTo(exporter);

            }
        };
    }

    @Override
    public String getName() {
        return "AlienMod Recipes";
    }
}
