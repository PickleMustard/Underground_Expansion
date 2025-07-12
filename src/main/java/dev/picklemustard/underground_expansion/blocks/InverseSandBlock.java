package dev.picklemustard.underground_expansion.blocks;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class InverseSandBlock extends RisingBlock{

    public static final MapCodec<InverseSandBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ColorRGBA.CODEC.optionalFieldOf("falling_dust_color", new ColorRGBA(00000000)).forGetter(InverseSandBlock -> InverseSandBlock.dustColor), propertiesCodec()).apply(instance, InverseSandBlock::new));
    public static final BooleanProperty FALLING = BlockStateProperties.FALLING;

    private final ColorRGBA dustColor;

    @Override
    public MapCodec<InverseSandBlock> codec() {
        return CODEC;
    }

    public InverseSandBlock(ColorRGBA dustColor, BlockBehaviour.Properties properties) {
        super(properties);
        this.dustColor = dustColor;
    }

    public ColorRGBA getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
        return this.dustColor;
    }


}




