package com.hugouououo.alienmod.item.custom;

import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.sound.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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

//        Vec3d direction = shooter.getRotationVec(1.0F).normalize();
//        projectile.setPosition(
//                shooter.getX() + direction.x,
//                shooter.getEyeY() - 0.1,  // Um pouco abaixo dos olhos pra ficar mais natural
//                shooter.getZ() + direction.z
//        );
//
//        projectile.setVelocity(direction.x, direction.y, direction.z, 3.5f, 0f);
//        projectile.setOwner(shooter);
//
        projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw(), 0.0F, speed, divergence);

        if (projectile instanceof LaserProjectileEntity laserProjectile) {
            laserProjectile.setInitialRotation(shooter.getPitch(), shooter.getYaw());
        }

        Vec3d eyePos = shooter.getEyePos();
        Vec3d lookVec = shooter.getRotationVec(1.0F).normalize();
        projectile.setPosition(eyePos.x + lookVec.x * 0.1, eyePos.y - 0.1, eyePos.z + lookVec.z * 0.1);

        projectile.setOwner(shooter);
        shooter.getWorld().spawnEntity(projectile);

        shooter.getWorld().playSound(
                null,
                shooter.getBlockPos(),
                ModSounds.LASER_SHOOT,
                SoundCategory.PLAYERS,
                0.5F,
                shooter.getWorld().random.nextFloat() * 0.2F + 1.2F
        );
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!user.getItemCooldownManager().isCoolingDown(itemStack)) {
            user.getItemCooldownManager().set(itemStack, 0);

            if (!world.isClient()) {
                itemStack.damage(5, ((ServerWorld) world), ((ServerPlayerEntity) user),
                        item -> user.sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                ProjectileEntity laser = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE, world);
                shoot(user, laser, 0, 3.5f, 0f, user.getYaw(), null);

            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }


    }


}