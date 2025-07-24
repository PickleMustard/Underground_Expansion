package dev.picklemustard.underground_expansion.world.level.carvers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class LavaTubeConfiguration extends CarverConfiguration {

    public static final Codec<LavaTubeConfiguration> CODEC = RecordCodecBuilder.create(entry -> entry
            .group(CarverConfiguration.CODEC.forGetter(getter -> getter),
                    FloatProvider.CODEC.fieldOf("central_radius").forGetter(value -> value.centralRadius),
                    FloatProvider.CODEC.fieldOf("angle").forGetter(value -> value.angle))
            .apply(entry, LavaTubeConfiguration::new));

    public final FloatProvider centralRadius;
    public final FloatProvider angle;

    public LavaTubeConfiguration(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel,
            CarverDebugSettings debugSettings, HolderSet<Block> replaceable, FloatProvider centralRadius,
            FloatProvider angle) {
        super(probability, y, yScale, lavaLevel, debugSettings, replaceable);
        this.centralRadius = centralRadius;
        this.angle = angle;
    }

    public LavaTubeConfiguration(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel,
            HolderSet<Block> replaceable, FloatProvider centralRadius, FloatProvider angle) {
        super(probability, y, yScale, lavaLevel, CarverDebugSettings.DEFAULT, replaceable);
        this.centralRadius = centralRadius;
        this.angle = angle;
    }

    public LavaTubeConfiguration(CarverConfiguration config, FloatProvider centralRadius, FloatProvider angle) {
        this(config.probability, config.y, config.yScale, config.lavaLevel, config.debugSettings, config.replaceable,
                centralRadius, angle);
    }

}
