package com.hugouououo.tutorialmod.item;

import com.hugouououo.tutorialmod.TutorialMod;
import com.hugouououo.tutorialmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static void registerItemGroups(){
        TutorialMod.LOGGER.info("Registrando grupos de itens para " + TutorialMod.MOD_ID);
    }

    public static final ItemGroup ALIEN_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TutorialMod.MOD_ID, "alien"),
            FabricItemGroup.builder()
                    .icon(()-> new ItemStack(ModItems.ALIEN_GOO))
                    .displayName(Text.translatable("itemgroup.tutorialmod.alien"))
                    .entries((displayContext, entries) -> {

                        // ADICIONAR TODOS NA ORDEM DE  > REGISTRO <
                        entries.add(ModItems.ALIEN_DEVICE);
                        entries.add(ModItems.ALIEN_GOO);
                        entries.add(ModBlocks.ALIEN_STEEL);
                        entries.add(ModBlocks.ALIEN_GOO_BLOCK);

                    })
                    .build()
    );

//    public static final ItemGroup ALIEN_ITEMS_BLOCKS = Registry.register(Registries.ITEM_GROUP,
//            Identifier.of(TutorialMod.MOD_ID, "Alien Blocks"),
//            FabricItemGroup.builder()
//                    .icon(()-> new ItemStack(ModBlocks.ALIEN_GOO_BLOCK))
//                    .displayName(Text.translatable("itemgroup.tutorialmod.alien_blocks"))
//                    .entries((displayContext, entries) -> {
//
//                        entries.add(ModBlocks.ALIEN_STEEL);
//                        entries.add(ModBlocks.ALIEN_GOO_BLOCK);
//
//                    }).build());
}
