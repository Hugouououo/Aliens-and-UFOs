package com.hugouououo.alienmod.item;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static void registerItemGroups(){

        //AlienMod.LOGGER.info("Registrando grupos de itens para " + AlienMod.MOD_ID);

        Registry.register(Registries.ITEM_GROUP,
                Identifier.of(AlienMod.MOD_ID, "alien"),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(ModItems.ALIEN_GOO))
                        .displayName(Text.translatable("itemgroup.alienmod.alien"))
                        .entries((displayContext, entries) -> {

                            entries.add(ModItems.ALIEN_SPAWN_EGG);
                            entries.add(ModItems.ALIEN_DEVICE);
                            entries.add(ModItems.RAY_GUN);
                            entries.add(ModItems.ALIEN_GOO);
                            entries.add(ModBlocks.ALIEN_GOO_BLOCK);
                            entries.add(ModBlocks.ALIEN_STEEL);
                            entries.add(ModBlocks.ALIEN_STEEL_SLAB);
                            entries.add(ModBlocks.ALIEN_COPPER);
                            entries.add(ModBlocks.ALIEN_GLASS);
                            //entries.add(ModBlocks.ALIEN_CHEST);

                        })
                        .build()
        );

        // ta desatualizado para 1.21.4 mas ta funcionando
    }
}
