package com.hugouououo.alienmod.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlueLaserProjectileEntity extends ProjectileEntity {

    public BlueLaserProjectileEntity(EntityType<? extends BlueLaserProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public static final TrackedData<Float> INITIAL_YAW = DataTracker.registerData(BlueLaserProjectileEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> INITIAL_PITCH = DataTracker.registerData(BlueLaserProjectileEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public void setInitialRotation(float pitch, float yaw) {
        this.dataTracker.set(INITIAL_PITCH, pitch);
        this.dataTracker.set(INITIAL_YAW, yaw);
    }
    public float getInitialYaw() {
        return this.dataTracker.get(INITIAL_YAW);
    }
    public float getInitialPitch() {
        return this.dataTracker.get(INITIAL_PITCH);
    }

    @Override
    public void tick() {
        super.tick();

        Vec3d velocity = this.getVelocity();
        Vec3d currentPos = this.getPos();
        Vec3d nextPos = currentPos.add(velocity);

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onCollision(hitResult);
        } else {
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
            serverWorld.spawnParticles(ParticleTypes.LAVA, hitPos.x, hitPos.y, hitPos.z, 3, 0.025, 0.025, 0.025, 0.0);
        }
        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d hitPos = blockHitResult.getBlockPos().toCenterPos();
            serverWorld.spawnParticles(ParticleTypes.SMOKE, hitPos.x, hitPos.y, hitPos.z, 10, 0.1, 0.1, 0.1, 0.1);
        }
        this.discard();
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
        //return true;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(INITIAL_YAW, 0.0F);
        builder.add(INITIAL_PITCH, 0.0F);
    }
}
