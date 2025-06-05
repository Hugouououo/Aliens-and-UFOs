package com.hugouououo.alienmod.world.gen;

import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {
    public static void addSpawns(){

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS),
                SpawnGroup.MONSTER, ModEntities.ALIEN, 10, 1, 5); //weight=how often
        SpawnRestriction.register(
                ModEntities.ALIEN,
                SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                PathAwareEntity::canMobSpawn);
    }
}
