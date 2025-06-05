package com.hugouououo.alienmod.entity;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static void registerModeEntities(){
        AlienMod.LOGGER.info("registrando entidades");
    }

    // jeito 1
    public static final EntityType<AlienEntity> ALIEN = Registry.register(
            Registries.ENTITY_TYPE,
            RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(AlienMod.MOD_ID, "alien")),
            // O .build() agora recebe o RegistryKey
            FabricEntityType.Builder.createMob(AlienEntity::new, SpawnGroup.CREATURE, builder -> {
                        builder.defaultAttributes(AlienEntity::createAttributes);
                        return builder;
                    }).dimensions(1f,1.5f)
                    .build(RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(AlienMod.MOD_ID, "alien")))
    );

    // jeito 2 (kaupenjoe)
    private static final RegistryKey<EntityType<?>> LASER_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(AlienMod.MOD_ID, "alien"));

    public static final EntityType<LaserProjectileEntity> LASER_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(AlienMod.MOD_ID, "laser"),
            EntityType.Builder.<LaserProjectileEntity>create(LaserProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.15f, 0.1f).build(LASER_KEY));
}