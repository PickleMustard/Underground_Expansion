package dev.picklemustard.underground_expansion.world.structure.pieces;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.mutable.MutableInt;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.world.level.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.phys.Vec2;

public class DeathPitVaseStructurePiece extends StructurePiece {

    private static boolean replaceBiomesError = false;

    private BlockPos chunkCorner;
    private BlockPos holeCenter;
    private int height;
    private int radius;
    private int surfaceHeight;
    private static Sampler sample;

    public DeathPitVaseStructurePiece(BlockPos chunkCorner, BlockPos holeCenter, int height, int radius,
            int surfaceHeight, RandomState randState) {
        super(ModPieces.DEATH_PIT_VASE_SP.get(), 0,
                createBoundingBox(chunkCorner, chunkCorner.getY() - 16, chunkCorner.getY() + 16));
        this.chunkCorner = chunkCorner;
        this.holeCenter = holeCenter;
        this.height = height;
        this.radius = radius;
        this.surfaceHeight = surfaceHeight;
        sample = randState.sampler();
    }

    private static BoundingBox createBoundingBox(BlockPos center, int minY, int maxY) {
        ChunkPos corner = new ChunkPos(center);
        return new BoundingBox(corner.getMinBlockX(), minY, corner.getMinBlockZ(), corner.getMaxBlockX(), maxY,
                corner.getMaxBlockZ());
    }

    public DeathPitVaseStructurePiece(CompoundTag tag) {
        super(ModPieces.DEATH_PIT_VASE_SP.get(), tag);
        this.chunkCorner = new BlockPos(tag.getInt("TPX"), tag.getInt("TPY"), tag.getInt("TPZ"));
        this.holeCenter = new BlockPos(tag.getInt("HCX"), tag.getInt("HCY"), tag.getInt("HCZ"));
        this.height = tag.getInt("Height");
        this.radius = tag.getInt("Radius");
        this.surfaceHeight = tag.getInt("SurfaceHeight");
    }

    public DeathPitVaseStructurePiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(tag);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.putInt("TPX", this.chunkCorner.getX());
        tag.putInt("TPY", this.chunkCorner.getY());
        tag.putInt("TPZ", this.chunkCorner.getZ());
        tag.putInt("HCX", this.holeCenter.getX());
        tag.putInt("HCY", this.holeCenter.getY());
        tag.putInt("HCZ", this.holeCenter.getZ());
        tag.putInt("Height", this.height);
        tag.putInt("Radius", this.radius);
        tag.putInt("SurfaceHeight", this.surfaceHeight);
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator,
            RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        float maxDistance = (float) radius * (float) radius;
        float startRadiusPercent = random.nextInt(90, 120) / 100f;
        float interiorTurnPercent = random.nextInt(20, 30) / 100f;
        float exteriorTurnPercent = random.nextInt(115, 125) / 100f;
        float firstPositionModifier = random.nextInt(20, 30) / 100f;
        float secondPositionModifier = random.nextInt(45, 60) / 100f;
        float thirdPositionModifier = random.nextInt(70, 90) / 100f;
        Vec2 p1 = new Vec2(maxDistance * startRadiusPercent, holeCenter.getY());
        Vec2 p2 = new Vec2(maxDistance * interiorTurnPercent,
                holeCenter.getY() - (this.height * firstPositionModifier));
        Vec2 p3 = new Vec2(maxDistance * interiorTurnPercent,
                holeCenter.getY() - (this.height * secondPositionModifier));
        Vec2 p4 = new Vec2(maxDistance * exteriorTurnPercent,
                holeCenter.getY() - (this.height * thirdPositionModifier));
        int cornerX = this.chunkCorner.getX();
        int cornerY = this.holeCenter.getY();
        int cornerZ = this.chunkCorner.getZ();
        int diffCenterSurface = cornerY - this.surfaceHeight;
        diffCenterSurface = Mth.ceil(diffCenterSurface * 1.50f);
        BlockPos.MutableBlockPos carve = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos carveAbove = new BlockPos.MutableBlockPos();
        carve.set(cornerX, cornerY, cornerZ);
        carveAbove.set(cornerX, cornerY, cornerZ);
        replaceBiomes(level, ModBiomes.DEATH_PIT, surfaceHeight, cornerY - height);
        for (int y = height; y > diffCenterSurface; y--) {
            float position = (float) y / (float) height;
            Vec2 splinePosition = p1
                    .scale(-(position * position * position) + (3 * position * position) - (3 * position) + 1)
                    .add(p2.scale((3 * position * position * position) - (6 * position * position) + (3 * position)))
                    .add(p3.scale(-(3 * position * position * position) + (3 * position * position)))
                    .add(p4.scale(position * position * position));
            for (int x = 0; x <= 16; x++) {
                for (int z = 0; z <= 16; z++) {
                    carve.set(cornerX + x, Mth.clamp(cornerY - y, level.getMinBuildHeight(), level.getMaxBuildHeight()),
                            cornerZ + z);
                    carveAbove.set(carve.getX(), carve.getY() + 1, carve.getZ());
                    double distToCenter = carve.distToCenterSqr(this.holeCenter.getX(), carve.getY(),
                            this.holeCenter.getZ());
                    if (distToCenter <= splinePosition.x) {
                        level.setBlock(carve,
                                Blocks.CAVE_AIR.defaultBlockState(), 128);
                    }
                }
            }
        }
    }

    public void checkedSetBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (this.getBoundingBox().isInside(pos)) {
            level.setBlock(pos, state, 128);
        }
    }

    public void replaceBiomes(WorldGenLevel level, ResourceKey<Biome> replacementBiome, int topLevel, int bottomLevel) {
        if (replaceBiomesError) {
            return;
        }
        try {
            Holder<Biome> replacementBiomeInstance = level.registryAccess().registryOrThrow(Registries.BIOME)
                    .getHolderOrThrow(replacementBiome);
            ChunkAccess chunkAccess = level.getChunk(this.chunkCorner);
            BlockPos.MutableBlockPos replacementPosition = new BlockPos.MutableBlockPos(this.chunkCorner.getX(),
                    topLevel, this.chunkCorner.getZ());
            if (chunkAccess != null) {
                List<ChunkAccess> toReplace = new ArrayList<>();
                while (replacementPosition.getY() > bottomLevel) {
                    chunkAccess = level.getChunk(replacementPosition);
                    toReplace.add(chunkAccess);
                    replacementPosition.move(0, -8, 0);
                }
                MutableInt mutInt = new MutableInt(0);

                for (ChunkAccess chunkAccess2 : toReplace) {
                    chunkAccess.fillBiomesFromNoise((x, y, z, sampler) -> {
                        mutInt.increment();
                        return replacementBiomeInstance;
                    }, sample);
                    chunkAccess2.setUnsaved(true);
                }

            }

        } catch (Exception e) {
            replaceBiomesError = true;
            UndergroundExpansion.LOGGER.error(
                    "Could not replace biomes with {}. Check for conflicts with world generation mods.",
                    replacementBiome);
            e.printStackTrace();
        }
    }

}
