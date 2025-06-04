package com.hugouououo.alienmod.entity.custom;

import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.item.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;

public class LaserProjectileEntity extends ProjectileEntity {

    public LaserProjectileEntity(EntityType<? extends LaserProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public LaserProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.LASER_PROJECTILE, world);
        this.setOwner(owner);
        // Set initial position: a bit in front of the owner's eyes
        Vec3d eyePos = owner.getEyePos(); // owner.getEyePos() gets the position of the owner's eyes
        Vec3d lookVec = owner.getRotationVector(); // owner.getRotationVector() gets the direction the owner is looking
        // Offset the starting position slightly in front of the owner's eyes
        // Adjust these values as needed to get the desired starting point
        double offsetX = lookVec.x * 0.1;
        double offsetY = lookVec.y * 0.1;
        double offsetZ = lookVec.z * 0.1;
        this.setPosition(eyePos.x + offsetX, eyePos.y + offsetY, eyePos.z + offsetZ);
        // Set velocity based on owner's look direction
        float velocityMagnitude = 1.5F; // Adjust this value for desired speed
        this.setVelocity(lookVec.x * velocityMagnitude, lookVec.y * velocityMagnitude, lookVec.z * velocityMagnitude);
        // Set rotation for rendering (pitch and yaw)
        // Yaw (rotation around Y-axis)
        this.setYaw(owner.getYaw() + 90.0F); // Add 90 if your model faces sideways by default
        // *** CORREÇÃO AQUI: USAR lastYaw ***
        this.lastYaw = this.getYaw();

        // Pitch (rotation around X-axis)
        this.setPitch(owner.getPitch());

        // *** CORREÇÃO AQUI: USAR lastPitch ***
        this.lastPitch = this.getPitch();
    }


    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(0.1F, 0.1F); // tamanho correto da hitbox
    }


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();

        this.setRotation(this.getYaw(), this.getPitch()); // Isso recalcula a rotação com base na velocidade

        // Calculate potential next position
        Vec3d velocity = this.getVelocity();
        Vec3d currentPos = this.getPos();
        Vec3d nextPos = currentPos.add(velocity);

        // Update the projectile's position (do this before collision checks for accuracy)
        this.setPos(nextPos.x, nextPos.y, nextPos.z);

        // Define the search box for entities.
        // It should cover the current position, the next position, and the projectile's dimensions.
        Box currentAndNextBox = this.getBoundingBox().stretch(velocity).expand(0.5, 0.5, 0.5); // Increased expansion for safety

        // Predicate to exclude the owner and self from collision
        Predicate<Entity> targetPredicate = (entity) -> !entity.isSpectator() && entity.isAlive() && entity.canHit() && (this.getOwner() == null || !this.getOwner().equals(entity));

        // Use the updated getEntityCollision
        EntityHitResult entityHitResult = ProjectileUtil.getEntityCollision(this.getWorld(), this, currentPos, nextPos, currentAndNextBox, targetPredicate);

        if (entityHitResult != null) {
            this.onEntityHit(entityHitResult);
            // If the laser should disappear after hitting an entity, uncomment the next line
            // this.discard();
            return; // Important: return after handling entity hit to prevent further movement/block collision
        }

        // Verifica colisão com blocos using the updated position
        HitResult blockHit = this.getWorld().raycast(new RaycastContext(
                currentPos, // Start from the previous position
                nextPos,    // Raycast to the new position
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                this));

        // If a block is hit, discard the projectile
        if (blockHit.getType() == HitResult.Type.BLOCK) {
            this.onCollision(blockHit); // Call onCollision to handle the discard
            return;
        }

        // Update hitbox based on new position (already done by setPos but ensuring it's explicit)
        this.setBoundingBox(new Box(
                this.getX() - 0.05, this.getY() - 0.05, this.getZ() - 0.05,
                this.getX() + 0.05, this.getY() + 0.05, this.getZ() + 0.05)
        );
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (this.getOwner() instanceof LivingEntity owner && this.getWorld() instanceof ServerWorld serverWorld) {
            //ServerWorld serverWorld = (ServerWorld) this.getWorld();                                ^^^^^^^
            DamageSource source = serverWorld.getDamageSources().mobProjectile(this, owner);
            entity.damage(serverWorld, source, 4.0F);
        }
        this.discard();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        this.discard();
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }
}