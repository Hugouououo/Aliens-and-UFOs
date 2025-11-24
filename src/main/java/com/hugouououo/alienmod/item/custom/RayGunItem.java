package com.hugouououo.alienmod.item.custom;

import com.hugouououo.alienmod.entity.custom.LaserProjectileEntity;
import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.sound.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
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
        return LASER_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {

        projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw(), 0.0F, speed, divergence);

        if (projectile instanceof LaserProjectileEntity laserProjectile) {
            laserProjectile.setInitialRotation(shooter.getPitch(), shooter.getYaw());
        }

        Vec3d eyePos = shooter.getEyePos();
        Vec3d lookVec = shooter.getRotationVec(1.0F).normalize();
        projectile.setPosition(eyePos.x + lookVec.x * 0.1, eyePos.y - 0.2, eyePos.z + lookVec.z * 0.1);

        projectile.setOwner(shooter);
        shooter.getWorld().spawnEntity(projectile);

        shooter.getWorld().playSound(
                null,
                shooter.getBlockPos(),
                ModSounds.LASER_SHOOT,
                SoundCategory.PLAYERS,
                0.5F,
                shooter.getWorld().random.nextFloat() * 0.4F + 1.0F
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!user.getItemCooldownManager().isCoolingDown(itemStack.getItem())) {
            user.getItemCooldownManager().set(itemStack.getItem(), 15); // cooldown

            if (!world.isClient()) {
                itemStack.damage(5, ((ServerWorld) world), ((ServerPlayerEntity) user),
                        item -> user.sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                ProjectileEntity laser = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE, world);
                shoot(user, laser, 0, 3.5f, 0f, user.getYaw(), null);

            }
            return TypedActionResult.success(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }



}