package com.hugouououo.alienmod.sound;

import com.hugouououo.alienmod.AlienMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    // Log
    public static void registerSounds(){
        AlienMod.LOGGER.info("Registando sons");
    }

    //Registrador
    private static SoundEvent registerSoundEvent(String name){
        Identifier id = Identifier.of(AlienMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    // Sons:

    // tiro c a ray gun
    public static final SoundEvent LASER_SHOOT = registerSoundEvent("laser_shoot");

    // public static final BlockSoundGroup ALIEN_BLOCK_SOUNDS = new BlockSoundGroup(1f,1f,
    //      NOME1, NOME2, NOME3);
}
