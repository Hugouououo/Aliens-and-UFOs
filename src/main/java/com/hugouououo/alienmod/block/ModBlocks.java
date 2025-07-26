package com.hugouououo.alienmod.block;

import com.hugouououo.alienmod.AlienMod;
//import com.hugouououo.alienmod.block.custom.AlienChestBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    public static void initialize() {
    }

//    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
//        RegistryKey<Block> blockKey = keyOfBlock(name);
//        Block block = blockFactory.apply(settings.registryKey(blockKey));
//        if (shouldRegisterItem) {
//            RegistryKey<Item> itemKey = keyOfItem(name);
//            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
//            Registry.register(Registries.ITEM, itemKey, blockItem);
//        }
//        return Registry.register(Registries.BLOCK, blockKey, block);
//    }
//    private static RegistryKey<Block> keyOfBlock(String name) {
//        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AlienMod.MOD_ID, name));
//    }
//    private static RegistryKey<Item> keyOfItem(String name) {
//        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AlienMod.MOD_ID, name));
//    }

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(AlienMod.MOD_ID, name), block);
    }
    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(AlienMod.MOD_ID, name),
                new BlockItem(block, new  Item.Settings()));
    }

    public static final Block ALIEN_GOO_BLOCK = registerBlock(
            "alien_goo_block",
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.SLIME)
                    .breakInstantly()
                    .burnable()
                    .nonOpaque()
                    .slipperiness(1.25f))
    );

    public static final Block ALIEN_STEEL = registerBlock(
            "alien_steel",
            new Block(
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.NETHERITE))
    );

    public static final Block ALIEN_STEEL_SLAB = registerBlock(
            "alien_steel_slab",
            new SlabBlock(
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.NETHERITE))
    );

    public static final Block ALIEN_GLASS = registerBlock(
            "alien_glass",
            new Block(
            AbstractBlock.Settings.create()
                    .strength(1f)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GLASS))
    );

    public static final Block ALIEN_COPPER = registerBlock(
            "alien_copper",
            new Block(
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER))
    );

//    public static final Block ALIEN_CHEST = registerBlock(
//            "alien_chest",
//            AlienChestBlock::new,
//            AbstractBlock.Settings.create()
//            .strength(2.5f)
//            .nonOpaque()
//            .sounds(BlockSoundGroup.NETHER_WOOD),
//            true);
}
