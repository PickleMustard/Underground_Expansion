package dev.picklemustard.underground_expansion.world.level.carvers;

import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class LavaTube extends WorldCarver<LavaTubeConfiguration> {

    //public static final Codec<LavaTube> CODEC = RecordCodecBuilder.create(entry -> entry.group(LavaTubeConfiguration.CODEC.forGetter(value -> value)).apply(entry, LavaTube::new));

    public LavaTube(Codec<LavaTubeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean carve(CarvingContext context, LavaTubeConfiguration config, ChunkAccess chunk,
            Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random, Aquifer aquifer, ChunkPos chunkPos,
            CarvingMask carvingMask) {
        int tries = random.nextInt(15);
        for(int i = 0; i < tries; i++) {
            double xCoord = (double)chunkPos.getBlockX(random.nextInt(16));
            double yCoord = (double)config.y.sample(random, context);
            double zCoord = (double)chunkPos.getBlockZ(random.nextInt(16));
            double floorLevel = yCoord;

            WorldCarver.CarveSkipChecker skipChecker = (con, x, y, z, floor) -> ShouldSkip(x, y, z);
            this.carveEllipsoid(context, config, chunk, biomeAccessor, aquifer, xCoord, yCoord, zCoord, config.centralRadius.sample(random), config.centralRadius.sample(random), carvingMask, skipChecker);
        }
        return true;
    }

    @Override
    public boolean isStartChunk(LavaTubeConfiguration config, RandomSource random) {
        return random.nextFloat() <= config.probability;
    }

    private static boolean ShouldSkip(double relative, double relativeY, double relativeZ) {
        return (relative * relative + relativeY * relativeY + relativeZ * relativeZ >= 1.0);
    }


}
