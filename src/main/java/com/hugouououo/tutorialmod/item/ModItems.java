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

    // AQUI DECLARA O ITEM

    //public static Item ALIEN_GOO = registerItem("alien_goo", new Item(new Item.Settings()));          AGORA É:
    public static final Item ALIEN_GOO =registerItem("alien_goo",new Item(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TutorialMod.MOD_ID,"alien_goo")))));
    public static final Item ALIEN_DEVICE =registerItem("alien_device",new Item(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TutorialMod.MOD_ID,"alien_device")))));


    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(TutorialMod.MOD_ID, name), item);   //APRENDER O Q É ISSO DPS
    }

     public static void registerModItems() {
         TutorialMod.LOGGER.info("Registrando itens do Mod para " + TutorialMod.MOD_ID);

         ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
             entries.add(ALIEN_GOO);
             entries.add(ALIEN_DEVICE);
         });

     }
}
