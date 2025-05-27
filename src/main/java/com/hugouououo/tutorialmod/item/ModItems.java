package com.hugouououo.tutorialmod.item;

import com.hugouououo.tutorialmod.TutorialMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    //public static Item registerItem(String name, Item item){
    //  return Registry.register(Registries.ITEM, Identifier.of(TutorialMod.MOD_ID, name), item);
    //}
    //If you get a NullPointerException "item id not found" when creating an item, replace the registerItem function in ModItems with
    private static Item registerItem(String name, Item.Settings itemSettings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TutorialMod.MOD_ID, name));
        Item item = new Item(itemSettings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }
    //and when creating a new item, instead of passing "new Item(new Item.Settings())" with "new Item.Settings()"

    public static Item ALIEN_GOO = registerItem("alien_goo", new Item.Settings());
    public static Item ALIEN_DEVICE = registerItem("alien_device", new Item.Settings());


    public static void registerModItems() {
         TutorialMod.LOGGER.info("Registrando itens do Mod para " + TutorialMod.MOD_ID);

         ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
             fabricItemGroupEntries.add(ALIEN_GOO);
             fabricItemGroupEntries.add(ALIEN_DEVICE);
         });

     }
}
