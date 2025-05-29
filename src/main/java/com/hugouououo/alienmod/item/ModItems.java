package com.hugouououo.alienmod.item;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.item.custom.RayGunItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    public static void initialize() {
    }

    public static final Item ALIEN_GOO = registerItem("alien_goo", Item::new, new Item.Settings());
    public static final Item ALIEN_DEVICE = registerItem("alien_device", Item::new, new Item.Settings());
    public static final Item ALIEN_STEEL_INGOT = registerItem("alien_steel_ingot", Item::new, new Item.Settings());
    public static final Item RAY_GUN = registerItem("ray_gun", RayGunItem::new, new Item.Settings());

    private static Item registerItem(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("alienmod", path));
        return Items.register(registryKey, factory, settings);
    }

    public static void registerModItems() {
         AlienMod.LOGGER.info("Registrando itens do Mod para " + AlienMod.MOD_ID);

         ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
             fabricItemGroupEntries.add(ALIEN_GOO);
             fabricItemGroupEntries.add(ALIEN_DEVICE);
             fabricItemGroupEntries.add(ALIEN_STEEL_INGOT);
             fabricItemGroupEntries.add(RAY_GUN);
         });

     }
}
