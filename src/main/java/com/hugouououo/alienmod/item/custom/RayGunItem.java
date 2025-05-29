package com.hugouououo.alienmod.item.custom;

import com.hugouououo.alienmod.AlienMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.util.hit.BlockHitResult; // importei manualmente
import net.minecraft.util.math.Box; // importei manualmente
import net.minecraft.entity.Entity; // importei manualmente
import net.minecraft.util.hit.EntityHitResult; //  manualmente
import java.util.List; //  manualmente
import net.minecraft.particle.ParticleTypes; //manualmente

public class RayGunItem extends Item {

    public RayGunItem(Settings settings){
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {

        AlienMod.LOGGER.info("Laser Fired!");
        ItemStack stack = user.getStackInHand(hand);

        if(!user.getItemCooldownManager().isCoolingDown(stack)) {
            user.getItemCooldownManager().set(stack, 60);

            this.fireLaser(world,user,stack);

            user.swingHand(hand, true);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        AlienMod.LOGGER.info("Laser Fired on Entity!");

        if(!user.getItemCooldownManager().isCoolingDown(stack)) {
            user.getItemCooldownManager().set(stack, 60);

            this.fireLaser(user.getWorld(), user, stack);

            user.swingHand(hand, true);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    private void fireLaser(World world, LivingEntity shooter, ItemStack weaponStack) {

        AlienMod.LOGGER.info("fireLaser called!");

        if (!world.isClient()) {
            weaponStack.damage(1, shooter, null);


            // Ponto de partida
            Vec3d startRayPos = shooter.getEyePos();
            Vec3d lookVec = shooter.getRotationVec(1.0F);
            // Alcance
            double laserEffectRange = 200.0;
            Vec3d endRayPos = startRayPos.add(lookVec.x * laserEffectRange, lookVec.y * laserEffectRange, lookVec.z * laserEffectRange);
            // Detecta se atingiu um bloco
            BlockHitResult blockHit = world.raycast(new RaycastContext(
                    startRayPos, endRayPos,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    shooter
            ));
            Vec3d actualEndHitPos = blockHit.getType() == HitResult.Type.BLOCK ? blockHit.getPos() : endRayPos;
            Box searchBox = new Box(startRayPos, actualEndHitPos).expand(1.0);
            List<Entity> entitiesInPath = world.getOtherEntities(shooter, searchBox, (entityIter) ->
                    entityIter instanceof LivingEntity && entityIter != shooter && entityIter.isAttackable());

            EntityHitResult entityHit = null;
            double closestDistanceSq = Double.MAX_VALUE;

            for (Entity entityIter : entitiesInPath) {
                double currentDistanceSq = shooter.squaredDistanceTo(entityIter);
                if (currentDistanceSq < closestDistanceSq) {
                    entityHit = new EntityHitResult(entityIter);
                    closestDistanceSq = currentDistanceSq;
                }
            }

            HitResult finalHit = null;
            if (entityHit != null) {
                finalHit = entityHit;
            } else if (blockHit.getType() != HitResult.Type.MISS) {
                finalHit = blockHit;
            }
        }
        if (world.isClient()) {

            world.playSound(
                    shooter,
                    shooter.getX(),
                    shooter.getY(),
                    shooter.getZ(),
                    AlienMod.LASER_SHOOT_SOUND_EVENT,
                    SoundCategory.PLAYERS,
                    1.0F,
                    0.8F + world.getRandom().nextFloat() * 0.4F
            );

            Vec3d particleStart = shooter.getEyePos();
            Vec3d lookVecClient = shooter.getRotationVec(1.0F);
            double laserEffectRangeClient = 200.0;
            Vec3d endRayPosClient = particleStart.add(lookVecClient.x * laserEffectRangeClient, lookVecClient.y * laserEffectRangeClient, lookVecClient.z * laserEffectRangeClient);

            // Re-perform client-side raycast for visual accuracy
            BlockHitResult blockHitClient = world.raycast(new RaycastContext(
                    particleStart, endRayPosClient,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    shooter
            ));

            // For particle generation, we also need to check for entities on the client.
            // This is purely for visual consistency, the server does the actual hit detection.
            Box searchBoxClient = new Box(particleStart, blockHitClient.getType() == HitResult.Type.BLOCK ? blockHitClient.getPos() : endRayPosClient).expand(1.0);
            List<Entity> entitiesInPathClient = world.getOtherEntities(shooter, searchBoxClient, (entityIter) ->
                    entityIter instanceof LivingEntity && entityIter != shooter); // No need for isAttackable client-side

            EntityHitResult entityHitClient = null;
            double closestDistanceSqClient = Double.MAX_VALUE;

            for (Entity entityIter : entitiesInPathClient) {
                double currentDistanceSqClient = shooter.squaredDistanceTo(entityIter);
                if (currentDistanceSqClient < closestDistanceSqClient) {
                    entityHitClient = new EntityHitResult(entityIter);
                    closestDistanceSqClient = currentDistanceSqClient;
                }
            }

            HitResult finalVisualHit = null;
            if (entityHitClient != null) {
                finalVisualHit = entityHitClient;
            } else if (blockHitClient.getType() != HitResult.Type.MISS) {
                finalVisualHit = blockHitClient;
            }

            Vec3d particleEnd = finalVisualHit != null ? finalVisualHit.getPos() : endRayPosClient;
            double distance = particleStart.distanceTo(particleEnd);
            int numParticles = (int) (distance / 0.5);

            for (int i = 0; i < numParticles; i++) {
                double progress = (double) i / numParticles;
                Vec3d particlePos = particleStart.lerp(particleEnd, progress);
                world.addParticleClient(
                        ParticleTypes.ELECTRIC_SPARK,
                        particlePos.x, particlePos.y, particlePos.z,
                        0.0, 0.0, 0.0
                );
            }

            if (finalVisualHit != null) {
                world.addParticleClient(
                        ParticleTypes.LAVA,
                        finalVisualHit.getPos().x, finalVisualHit.getPos().y, finalVisualHit.getPos().z, 0.0, 0.0, 0.0);
            }
        }
    }
}