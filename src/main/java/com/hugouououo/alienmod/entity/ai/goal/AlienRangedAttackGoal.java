package com.hugouououo.alienmod.entity.ai.goal;

import java.util.EnumSet;

import com.hugouououo.alienmod.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;

public class AlienRangedAttackGoal<T extends HostileEntity & RangedAttackMob> extends Goal {
    private final T actor;
    private final double speed;
    private int attackInterval;
    private final float squaredRange;
    private int cooldown = -1;
    private int targetSeeingTicker;
//    private boolean movingToLeft;
//    private boolean backward;
//    private int combatTicks = -1;
    private int chargeTicks;

    public AlienRangedAttackGoal(T actor, double speed, int attackInterval, float range) {
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

//    public void setAttackInterval(int attackInterval) {
//        this.attackInterval = attackInterval;
//    }

    @Override
    public boolean canStart() {
        return this.actor.getTarget() != null && this.isHoldingRayGun();
    }

    protected boolean isHoldingRayGun() {
        return this.actor.getMainHandStack().isOf(ModItems.RAY_GUN);
    }

    @Override
    public boolean shouldContinue() {
        return (this.canStart() || !this.actor.getNavigation().isIdle()) && this.isHoldingRayGun();
    }

    @Override
    public void start() {
        super.start();
        this.actor.setAttacking(true);
        this.chargeTicks = 0;
    }
    @Override
    public void stop() {
        super.stop();
        this.actor.setAttacking(false);
        // ...
        this.actor.clearActiveItem();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.actor.getTarget();
        if (target != null) {
            double distance = this.actor.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            boolean canSee = this.actor.getVisibilityCache().canSee(target);

            if (canSee) {
                this.targetSeeingTicker = Math.min(this.targetSeeingTicker + 1, 20);
            } else {
                this.targetSeeingTicker = Math.max(this.targetSeeingTicker - 1, -60);
            }

            if (distance <= this.squaredRange /*&& this.targetSeeingTicker >= 10*/) {
                this.actor.getNavigation().stop();
            } else {
                this.actor.getNavigation().startMovingTo(target, this.speed);
            }

            this.actor.lookAtEntity(target, 30.0F, 30.0F);

            this.cooldown--;

            if (this.cooldown <= 0) { // Se o cooldown terminou
                if (canSee) { // E o alvo está visível
                    if (this.chargeTicks < 20) { // Continua carregando
                        this.chargeTicks++;
                    } else { // Se carregou, atira
                        this.actor.shootAt(target, 1.0f);
                        this.cooldown = this.attackInterval; // Reseta cooldown
                        this.chargeTicks = 0; // Reseta carregamento
                    }
                } else { // Se o alvo não está visível, mas o cooldown já terminou, reseta o carregamento para esperar nova visão
                    this.chargeTicks = 0;
                }
            } else { // Se o cooldown ainda está ativo, reseta o carregamento para evitar que ele comece a carregar antes do tempo
                this.chargeTicks = 0;
            }
        }
    }
}