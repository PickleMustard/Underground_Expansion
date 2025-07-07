package dev.picklemustard.underground_expansion.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class InverseSand extends FallingBlock{

    public static final MapCodec<InverseSand> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ColorRGBA.CODEC.fieldOf("falling_dust_color").forGetter(InverseSand -> InverseSand.dustColor), propertiesCodec()).apply(instance, InverseSand::new));

    private final ColorRGBA dustColor;
    @Override
    public MapCodec<InverseSand> codec() {
        return CODEC;
    }

    public InverseSand(ColorRGBA dustColor, BlockBehaviour.Properties properties) {
        super(properties);
        this.dustColor = dustColor;
    }

    @Override
    public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
        return this.dustColor.rgba();
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(isFree(level.getBlockState(pos.above())) && pos.getY() <= 80) {
            FallingBlockEntity fbe = FallingBlockEntity.fall(level, pos, state);
            this.falling(fbe);
        }
    }

    public static boolean isFree(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
    }

}




