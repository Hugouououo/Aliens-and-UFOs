package com.hugouououo.tutorialmod.block;

import com.hugouououo.tutorialmod.TutorialMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

//    public static Block registerBlock(String name, Block block){
//        registerBlockItem(name, block);
//        return Registry.register(Registries.BLOCK, Identifier.of(TutorialMod.MOD_ID, name), block);
//    }
    //If you get a NullPointerException "item id not found" when creating a block, replace the registerBlock and registerBlockItem functions in ModBlocks with
    private static Block registerBlock(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TutorialMod.MOD_ID, name));
        Block block = new Block(blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

//    private static void registerBlockItem(String name, Block block){
//        Registry.register(Registries.ITEM, Identifier.of(TutorialMod.MOD_ID, name),
//                new BlockItem(block, new Item.Settings()));
//    }
    private static void registerBlockItem(String name, Block block) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TutorialMod.MOD_ID, name));
        BlockItem item = new BlockItem(block, new Item.Settings().registryKey(key));
        Registry.register(Registries.ITEM, key, item);
    }


    public static void registerModBlocks(){
        TutorialMod.LOGGER.info("Registrando blocos do mod para " + TutorialMod.MOD_ID);

        // "Adicionando" os blocos
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModBlocks.ALIEN_GOO_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.ALIEN_STEEL);
        });
    }

    // SEÇÃO DOS BLOCOS

    // ALIEN_GOO !!!!!!!!!!!!!!!!!!!!!!!!!!
    public static final Block ALIEN_GOO_BLOCK = registerBlock("alien_goo_block",
            AbstractBlock.Settings.create()
                    //.strength(4f)
                    //.requiresTool()
                    .sounds(BlockSoundGroup.SLIME)
                    .breakInstantly()
                    .burnable()
                    .slipperiness(0.8F)
                    .nonOpaque()
    );

    public static final Block ALIEN_STEEL = registerBlock("alien_steel",
            AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.NETHERITE)
    );

}
