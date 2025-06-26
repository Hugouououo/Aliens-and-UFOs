package com.hugouououo.alienmod.block.entity;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.block.ModBlocks;
import com.hugouououo.alienmod.block.entity.custom.AlienChestBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<AlienChestBlockEntity> ALIEN_CHEST =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(AlienMod.MOD_ID, "alien_chest"),
                    FabricBlockEntityTypeBuilder.create(AlienChestBlockEntity::new, ModBlocks.ALIEN_CHEST).build());

}
