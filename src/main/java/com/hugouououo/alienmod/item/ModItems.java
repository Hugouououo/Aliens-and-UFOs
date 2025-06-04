package com.hugouououo.alienmod.item;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.item.custom.RayGunItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    // Ingredientes
    public static final Item ALIEN_GOO = registerItem("alien_goo", Item::new);
    public static final Item ALIEN_DEVICE = registerItem("alien_device", Item::new);
    public static final Item ALIEN_STEEL_INGOT = registerItem("alien_steel_ingot", Item::new);

    // Itens
    public static final Item RAY_GUN = registerItem("ray_gun",
        setting -> new RayGunItem(setting.maxDamage(500)));

    public static final Item ALIEN_SPAWN_EGG = registerItem("alien_spawn_egg",
            setting -> new SpawnEggItem(ModEntities.ALIEN, setting));


    // REGISTRADORES
    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(AlienMod.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AlienMod.MOD_ID, name)))));
    }
    public static void registerModItems() {
         AlienMod.LOGGER.info("Registrando itens do Mod para " + AlienMod.MOD_ID);

         ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
             fabricItemGroupEntries.add(ALIEN_GOO);
             fabricItemGroupEntries.add(ALIEN_DEVICE);
             fabricItemGroupEntries.add(ALIEN_STEEL_INGOT);
         });

    }
}
