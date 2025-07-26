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

//    public static final EntityType<AlienEntity> ALIEN = Registry.register(
//            Registries.ENTITY_TYPE,
//            RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(AlienMod.MOD_ID, "alien")),
//            FabricEntityType.Builder.createMob(AlienEntity::new, SpawnGroup.CREATURE, builder -> {
//                        builder.defaultAttributes(AlienEntity::createAttributes);
//                        return builder;
//                    }).dimensions(0.6F, 1.5F)
//                    .build(RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(AlienMod.MOD_ID, "alien")))
//    );
//    private static final RegistryKey<EntityType<?>> LASER_KEY =
//            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(AlienMod.MOD_ID, "laser"));
//
//    public static final EntityType<LaserProjectileEntity> LASER_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
//            Identifier.of(AlienMod.MOD_ID, "laser"),
//            EntityType.Builder.<LaserProjectileEntity>create(LaserProjectileEntity::new, SpawnGroup.MISC)
//                    .dimensions(0.5f, 0.5f).build(LASER_KEY));

    public static final EntityType<AlienEntity> ALIEN = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(AlienMod.MOD_ID, "alien"),
            EntityType.Builder.create(AlienEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6F, 1.5F)
                    .build()
    );

    public static final EntityType<LaserProjectileEntity> LASER_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(AlienMod.MOD_ID, "laser"),
            EntityType.Builder.create(LaserProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5F, 0.5F)
                    .build()
    );

}