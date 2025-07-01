package com.hugouououo.alienmod.client.util;

import net.minecraft.entity.player.PlayerEntity;

public class RayGunRenderState {
    private static final ThreadLocal<PlayerEntity> CURRENT_PLAYER = new ThreadLocal<>();

    public static void setPlayer(PlayerEntity player) {
        CURRENT_PLAYER.set(player);
    }

    public static PlayerEntity getPlayer() {
        return CURRENT_PLAYER.get();
    }

    public static void clear() {
        CURRENT_PLAYER.remove();
    }
}
