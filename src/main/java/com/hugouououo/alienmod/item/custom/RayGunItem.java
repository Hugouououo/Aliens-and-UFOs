package com.hugouououo.alienmod.item.custom;

import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RayGunItem extends Item {

    public RayGunItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!user.getItemCooldownManager().isCoolingDown(stack)) {
            user.getItemCooldownManager().set(stack,20); // 1 segundo

            fireLaser(world, user);

            user.swingHand(hand);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    private void fireLaser(World world, LivingEntity shooter) {
        if (!world.isClient()) {
            LaserProjectileEntity laser = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE, world);

            Vec3d direction = shooter.getRotationVec(1.0F).normalize();
            Vec3d eyePos = shooter.getEyePos().add(direction.multiply(0));
            laser.setPosition(eyePos.x, eyePos.y, eyePos.z);
            laser.setVelocity(direction.multiply(3.5)); // Velocidade do laser
            laser.setOwner(shooter);

            world.spawnEntity(laser);
        }
        world.playSound(
                null,
                shooter.getX(),
                shooter.getY(),
                shooter.getZ(),
                ModSounds.LASER_SHOOT,
                SoundCategory.PLAYERS
        );
    }
}
