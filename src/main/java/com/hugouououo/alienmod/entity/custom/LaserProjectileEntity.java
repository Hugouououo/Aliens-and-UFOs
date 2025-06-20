package com.hugouououo.alienmod.entity.custom;

import com.hugouououo.alienmod.AlienMod;
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
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LaserProjectileEntity extends ProjectileEntity {

    public LaserProjectileEntity(EntityType<? extends LaserProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();

        Vec3d velocity = this.getVelocity();
        if (!velocity.equals(Vec3d.ZERO)) {
            float yaw = (float)(Math.toDegrees(Math.atan2(velocity.z, velocity.x)) - 90.0);
            float pitch = (float)(-Math.toDegrees(Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z))));
            this.setYaw(yaw);
            this.setPitch(pitch);
        }

        double stepSize = 0.5;
        double distance = velocity.length();
        int steps = (int) Math.ceil(distance / stepSize);

        Vec3d step = velocity.multiply(1.0 / steps);

        for (int i = 0; i < steps; i++) {
            Vec3d currentPos = this.getPos();
            Vec3d nextPos = currentPos.add(step);

            HitResult hitResult = ProjectileUtil.raycast(
                    this,
                    currentPos,
                    nextPos,
                    this.getBoundingBox().stretch(step).expand(0.2),
                    this::canHit,
                    1.0
            );

            if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
                return; // encerra o tick após colisão
            }

            this.setPosition(nextPos.x, nextPos.y, nextPos.z);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (this.getOwner() instanceof LivingEntity owner && this.getWorld() instanceof ServerWorld serverWorld) {
            DamageSource source = serverWorld.getDamageSources().mobProjectile(this, owner);
            entity.damage(serverWorld, source, 6.0F);

            Vec3d hitPos = entityHitResult.getPos();
            serverWorld.spawnParticles(ParticleTypes.LAVA, hitPos.x, hitPos.y, hitPos.z, 3, 0.05, 0.05, 0.05, 0.0);
        }
        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d hitPos = blockHitResult.getBlockPos().toCenterPos().normalize();
            serverWorld.spawnParticles(ParticleTypes.LAVA, hitPos.x, hitPos.y, hitPos.z, 15, 0.1, 0.1, 0.1, 0.01);
        }
        this.discard();
    }


    @Override
    public boolean canHit() {
        //return !this.isRemoved();
        return true;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}
}
