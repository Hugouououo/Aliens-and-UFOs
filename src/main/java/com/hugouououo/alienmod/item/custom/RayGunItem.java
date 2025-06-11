package com.hugouououo.alienmod.item.custom;

import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class RayGunItem extends RangedWeaponItem {

    public static final Predicate<ItemStack> LASER_PROJECTILES = (stack) -> stack.isOf(Items.AIR);
    public RayGunItem(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return null;
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {

        if (!shooter.getWorld().isClient()) {
            LaserProjectileEntity laser = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE, shooter.getWorld());
            Vec3d direction = shooter.getRotationVec(1.0F).normalize();
            Vec3d spawnPos = shooter.getEyePos().add(direction.multiply(1.0));
            laser.setPosition(spawnPos.x, spawnPos.y, spawnPos.z);
            laser.setVelocity(direction.multiply(3.5));
            laser.setPitch(shooter.getPitch());
            laser.setYaw(shooter.getYaw());
            laser.setOwner(shooter);
            shooter.getWorld().spawnEntity(laser);

            // som
            float pitch = shooter.getWorld().random.nextFloat() * 0.4F + 1.0F;
            float volume = 0.75F;
            BlockPos soundPos = shooter.getBlockPos();

            shooter.getWorld().playSound(
                    null,
                    soundPos,
                    ModSounds.LASER_SHOOT,
                    SoundCategory.PLAYERS,
                    volume,
                    pitch
            );
        }
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!user.getItemCooldownManager().isCoolingDown(itemStack)) {
            user.getItemCooldownManager().set(itemStack, 20); // Cooldown
            ProjectileEntity dummyProjectile = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE, world);
            shoot(user, dummyProjectile, 0, 3.5f, 0f, user.getYaw(), null);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL; //se em cooldown
        }
    }
}