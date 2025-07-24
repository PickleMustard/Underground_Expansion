package dev.picklemustard.underground_expansion.entity.custom;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PitViper extends PathfinderMob implements GeoEntity, NeutralMob{

    private AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    public PitViper(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_SPEED, 0.3);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 5, true, true, this::isAngryAt));
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();
        super.aiStep();
    }

    //@Override
    //public boolean isFood(ItemStack stack) {
    //    return true;
    //}

    //@Nullable
    //@Override
    //public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
    //    return ModEntities.PIT_VIPER.get().create(level);
    //}

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
       controllers.add(new AnimationController<>(this, "WALK/IDLE", 0, this::walkIdleController), new AnimationController<>(this, "PitViperAttack", 0, this::attackController) ,DefaultAnimations.genericLivingController(this));

    }


    private <T extends PitViper> PlayState attackController(final AnimationState<T> tAnimationState) {
        if(this.swinging) {
            return tAnimationState.setAndContinue(DefaultAnimations.ATTACK_BITE);
        }

        tAnimationState.getController().forceAnimationReset();

        return PlayState.STOP;

    }

    private <T extends PitViper> PlayState walkIdleController(final AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            return tAnimationState.setAndContinue(DefaultAnimations.WALK);
        }
        return tAnimationState.setAndContinue(DefaultAnimations.IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        this.remainingPersistentAngerTime = remainingPersistentAngerTime;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(20);
    }

    @Override
    public boolean isAngryAt(LivingEntity target) {
        return true;
    }

    @Override
    public boolean isAngry(){
        return true;
    }

}
