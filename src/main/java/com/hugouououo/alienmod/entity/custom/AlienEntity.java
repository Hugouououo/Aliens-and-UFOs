package com.hugouououo.alienmod.entity.custom;

import com.hugouououo.alienmod.entity.ModEntities;
import com.hugouououo.alienmod.entity.ai.goal.AlienRangedAttackGoal;
import com.hugouououo.alienmod.item.ModItems;
import com.hugouououo.alienmod.item.custom.RayGunItem;
import com.hugouououo.alienmod.sound.ModSounds;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AlienEntity extends HostileEntity implements Angerable, RangedAttackMob {

    // Variaveis
    private int ageWhenTargetSet;
    private int angerTime;
    @Nullable
    private UUID angryAt;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(AlienEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private static final UniformIntProvider ANGRY_SOUND_DELAY_RANGE = TimeHelper.betweenSeconds(0, 1); // Intervalo para o atraso do som
    private int angrySoundDelay;

    // Construtor
    public AlienEntity(EntityType<? extends AlienEntity> entityType, World world) {
        super(entityType, world);
        this.updateAttackType();
    }

    // Objetivos
    private final AlienRangedAttackGoal<AlienEntity> alienRangedAttackGoal = new AlienRangedAttackGoal<>(this, 1.0, 20, 5.0F);

    @Override
    protected void initGoals() {

        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 15.0F));
        this.goalSelector.add(1, new LookAroundGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0, 0.0F));
        this.targetSelector.add(0, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(2, new UniversalAngerGoal<>(this, true));

        this.updateAttackType();
    }

    @Override
    public boolean isAngryAt(ServerWorld world, PlayerEntity player) {
        return this.shouldAngerAt(player, world);
    }

    public void updateAttackType() {
        if (this.getWorld() != null && !this.getWorld().isClient()) {
            this.goalSelector.remove(this.alienRangedAttackGoal);

            ItemStack itemStack = this.getMainHandStack();
            if (itemStack.isOf(ModItems.RAY_GUN)) {
                this.goalSelector.add(0, this.alienRangedAttackGoal);
            }
        }
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        if (target == null || !target.isAlive()) {
            return;
        }

        if (!this.getWorld().isClient()) {
            LaserProjectileEntity laser = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE, this.getWorld());
            Vec3d direction = target.getPos().subtract(this.getPos()).normalize();
            Vec3d spawnPos = this.getEyePos().add(direction.multiply(1.0));
            laser.setPosition(spawnPos.x, spawnPos.y, spawnPos.z);
            laser.setVelocity(direction.multiply(3.5));
            laser.setPitch(this.getPitch());
            laser.setYaw(this.getYaw());
            laser.setOwner(this);
            this.getWorld().spawnEntity(laser);

            // som
            float pitch = this.getWorld().random.nextFloat() * 0.4F + 1.0F;
            float volume = 0.50F;
            BlockPos soundPos = this.getBlockPos();
            this.getWorld().playSound(
                    null,
                    soundPos,
                    ModSounds.LASER_SHOOT,
                    SoundCategory.HOSTILE,
                    volume,
                    pitch
            );
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if(source.getAttacker() instanceof AlienEntity){
            return false;  // aliens NAO tomam DANO de outros aliens
        }
        return super.damage(world, source, amount);
    }

    @Override
    public boolean canUseRangedWeapon(net.minecraft.item.RangedWeaponItem weapon) {
        return weapon instanceof RayGunItem;
    }

    @Override
    protected void initEquipment(net.minecraft.util.math.random.Random random, net.minecraft.world.LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.RAY_GUN));
    }

    @Override
    public void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
        super.onEquipStack(slot, oldStack, newStack);
        if (!this.getWorld().isClient()) {
            this.updateAttackType();
        }
    }

    // Atributos
    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 8.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3F)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.STEP_HEIGHT, 1.0);
    }


    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.getTarget() == null && target != null) {
            this.angrySoundDelay = ANGRY_SOUND_DELAY_RANGE.get(this.random);
        }
        super.setTarget(target);
        if (target == null) {
            this.ageWhenTargetSet = 0;
            this.dataTracker.set(ANGRY, false);
        } else {
            this.ageWhenTargetSet = this.age;
            this.dataTracker.set(ANGRY, true);
        }
    }


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ANGRY, false);
    }

    // atacando?
    @Override
    public void setAttacking(boolean attacking) {
        super.setAttacking(attacking);
    }
    @Override
    public boolean isAttacking() {
        return super.isAttacking();
    }

    // Metodos do Angerable
    @Override
    public int getAngerTime() {
        return this.angerTime;
    }
    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }
    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }
    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }
    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    // Sons
    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.ALIEN_IDLE; }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.ALIEN_HURT;}
    @Override
    protected SoundEvent getDeathSound() { return ModSounds.ALIEN_DEATH; }

    private void playAngrySound() {
        this.getWorld().playSound(
                null,
                this.getBlockPos(),
                ModSounds.RAY_GUN_LOAD,
                SoundCategory.HOSTILE,
                this.getSoundVolume() * 2.0F,
                this.getSoundPitch() * 1.8F
        );
    }

    // IDLE
    private void setupAnimationState(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            if (!this.getMainHandStack().isOf(ModItems.RAY_GUN)) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.RAY_GUN));
                this.updateAttackType();
            }
        }
        if (this.getWorld().isClient()) {
            setupAnimationState();
        }
        // Logica p som de raiva
        if (this.angrySoundDelay > 0) { //
            this.angrySoundDelay--; //
            if (this.angrySoundDelay == 0) { //
                this.playAngrySound(); //
            }
        }

        if (this.getTarget() != null && !this.getTarget().isAlive()) {
            this.setTarget(null);
        }
    }
}

