package com.hugouououo.alienmod;

import com.hugouououo.alienmod.block.ModBlocks;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.entity.custom.AlienEntity;
import com.hugouououo.alienmod.item.ModItemGroups;
import com.hugouououo.alienmod.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent; // Importação manual


import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlienMod implements ModInitializer {


	public static final String MOD_ID = "alienmod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

//	private static SoundEvent registerSoundEvent() {
//		return Registry.register(Registries.SOUND_EVENT,
//				Identifier.of(MOD_ID, "laser_shoot"),
//				SoundEvent.of(Identifier.of(MOD_ID, "laser_shoot")));
//	}
//	public static final SoundEvent LASER_SHOOT_SOUND_EVENT = registerSoundEvent();

	@Override
	public void onInitialize() {

		ModItems.registerModItems();

		ModBlocks.initialize();

		ModItemGroups.registerItemGroups();

		ModEntities.registerModeEntities();
		FabricDefaultAttributeRegistry.register(ModEntities.ALIEN, AlienEntity.createAttributes());

	}
}