package dev.picklemustard.underground_expansion.blocks;

import dev.picklemustard.underground_expansion.entity.custom.RisingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface Risable {
    default void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, RisingBlockEntity risingBlock) {

    }
    default void onBrokenAfterFall(Level level, BlockPos pos, RisingBlockEntity risingBlock) {

    }
    default DamageSource getFallDamageSource(Entity entity){
        return entity.damageSources().fallingBlock(entity);
    }
}
