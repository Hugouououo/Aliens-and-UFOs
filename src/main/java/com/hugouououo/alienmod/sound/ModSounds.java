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

    // tiro c a ray gun
    public static final SoundEvent LASER_SHOOT = registerSoundEvent("laser_shoot");

    // Alien carregando a arma
    public static final SoundEvent RAY_GUN_LOAD = registerSoundEvent("ray_gun_load");

    // Alien normal, machucado, morto
    public static final SoundEvent ALIEN_HURT = registerSoundEvent("alien.hurt");
    public static final SoundEvent ALIEN_IDLE = registerSoundEvent("alien.idle");
    public static final SoundEvent ALIEN_DEATH = registerSoundEvent("alien.death");

}
