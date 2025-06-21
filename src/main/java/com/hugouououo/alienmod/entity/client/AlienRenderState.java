package com.hugouououo.alienmod.entity.client;

import com.hugouououo.alienmod.entity.custom.AlienEntity;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

public class AlienRenderState extends BipedEntityRenderState {

    private AlienEntity alienEntity;
    public float tickDelta;
    public float handSwingProgress;

    public AlienRenderState() {
        //this.alienEntity = alienEntity;
    }

    public AlienEntity getAlienEntity() {
        return alienEntity;
    }

    public void setAlienEntity(AlienEntity alienEntity) {
        this.alienEntity = alienEntity;
    }

    public void setAttacking(boolean attacking) {

    }
}