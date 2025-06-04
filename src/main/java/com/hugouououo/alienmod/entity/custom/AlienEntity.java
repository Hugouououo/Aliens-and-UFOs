package com.hugouououo.alienmod.entity.custom;

import com.hugouououo.alienmod.AlienMod;
import com.hugouououo.alienmod.item.ModItems;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static net.minecraft.entity.mob.Angriness.ANGRY;

public class AlienEntity extends PathAwareEntity implements Angerable {
    public AlienEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int angerTime;
    @Nullable
    private UUID angryAt;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int lastAngrySoundAge = Integer.MIN_VALUE;



    @Override // Add @Override annotation for clarity and good practice
    protected void initGoals(){

        this.goalSelector.add(0, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 10.0f));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new WanderAroundFarGoal(this,1.00));

        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        //this.targetSelector.add(3, new UniversalAngerGoal<>(this, true));
    }

    public static DefaultAttributeContainer.Builder createAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 7.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.30)
                .add(EntityAttributes.ATTACK_DAMAGE, 1.0)
                //.add(EntityAttributes.ATTACK_SPEED, 1.0)
                .add(EntityAttributes.FOLLOW_RANGE, 16.0)
                .add(EntityAttributes.STEP_HEIGHT, 1.0);
    }

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
        // It's common to call setupAnimationState in the tick method to manage animations
        if (this.getWorld().isClient()) { // Only run animations on the client side
            setupAnimationState();
        }
    }

    //Sons do mob
    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }
    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }
    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
        // ModSounds.
    }

    //  Raiva  ///////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }
    @Override
    public int getAngerTime() {
        return this.angerTime;
    }
    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }
    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }
    public void playAngrySound() {
        if (this.age >= this.lastAngrySoundAge + 400) {
            this.lastAngrySoundAge = this.age;
            if (!this.isSilent()) {
                this.getWorld().playSoundClient(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_ANGRY, this.getSoundCategory(), 2.5F, 1.0F, false);
            }
        }
    }
    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ANGRY.equals(data) && this.getWorld().isClient) {
            this.playAngrySound();
        }
        super.onTrackedDataSet(data);
    }
    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.getAngryAt() == null && target != null) {
            // Quando um novo alvo é definido, definimos o UUID de raiva
            this.setAngryAt(target.getUuid());
        }
        super.setTarget(target);
    }
    // Métodos para salvar e carregar a raiva no NBT
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeAngerToNbt(nbt); // Salva o tempo de raiva e o UUID no NBT
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.getWorld(), nbt); // Carrega o tempo de raiva e o UUID do NBT
    }

}