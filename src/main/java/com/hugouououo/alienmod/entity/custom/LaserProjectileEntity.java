package com.hugouououo.alienmod.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.hit.HitResult;

public class LaserProjectileEntity extends ProjectileEntity {

    public LaserProjectileEntity(EntityType<? extends LaserProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }
    public LaserProjectileEntity(EntityType<? extends LaserProjectileEntity> entityType, LivingEntity shooter, World world) {
        super(entityType, world);
        this.setOwner(shooter);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d currentPos = this.getPos();
        Vec3d velocity = this.getVelocity();
        Vec3d nextPos = currentPos.add(velocity);
        HitResult hitResult = ProjectileUtil.raycast(
                this,
                currentPos.subtract(velocity), // Início do raio
                nextPos,    // Fim do raio
                this.getBoundingBox().stretch(velocity).expand(1.0),
                this::canHit,
                0.0F
        );


        if (hitResult != null) {
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            }
        }
        this.setPosition(nextPos.x, nextPos.y, nextPos.z);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (this.getOwner() instanceof LivingEntity owner && this.getWorld() instanceof ServerWorld serverWorld) {
            DamageSource source = serverWorld.getDamageSources().mobProjectile(this, owner);
            entity.damage(serverWorld, source, 8.0F);

            // partículas
            if (!this.getWorld().isClient()) {
                Vec3d hitPos = entityHitResult.getPos();
                serverWorld.spawnParticles(
                        ParticleTypes.LAVA,
                        hitPos.x, hitPos.y, hitPos.z,
                        5, 0.05, 0.05, 0.05, 0.0
                );
            }
        }
        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        // partículas
//        if (this.getWorld() instanceof ServerWorld serverWorld && !this.getWorld().isClient()) {
//            //ServerWorld serverWorld = (ServerWorld) this.getWorld();
//            Vec3d hitPos = blockHitResult.getPos();
//            serverWorld.spawnParticles(
//                    ParticleTypes.LAVA,
//                    hitPos.x, hitPos.y, hitPos.z,
//                    5, 0.05, 0.05, 0.05, 0.0
//            );
//        }
        this.discard();
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}
}