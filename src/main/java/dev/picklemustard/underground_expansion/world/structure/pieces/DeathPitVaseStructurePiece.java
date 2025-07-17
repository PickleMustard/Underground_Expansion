package dev.picklemustard.underground_expansion.world.structure.pieces;

import com.mojang.logging.LogUtils;

import dev.picklemustard.underground_expansion.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.phys.Vec2;

public class DeathPitVaseStructurePiece extends StructurePiece {

    private BlockPos chunkCorner;
    private BlockPos holeCenter;
    private int height;
    private int radius;

    public DeathPitVaseStructurePiece(BlockPos chunkCorner, BlockPos holeCenter, int height, int radius) {
        super(ModPieces.DEATH_PIT_VASE_SP.get(), 0,
                createBoundingBox(chunkCorner, chunkCorner.getY() - 16, chunkCorner.getY() + 16));
        LogUtils.getLogger().info("Created Piece | {}", chunkCorner);
        this.chunkCorner = chunkCorner;
        this.holeCenter = holeCenter;
        this.height = height;
        this.radius = radius;
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
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator,
            RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        // LogUtils.getLogger().info("BlockPos (x,y,z): ({}, {}, {})", pos.getX(),
        // pos.getY(), pos.getZ());
        float maxDistance = (float) radius * (float) radius;
        Vec2 p1 = new Vec2(maxDistance, holeCenter.getY());
        Vec2 p2 = new Vec2(maxDistance - (maxDistance * .75f), holeCenter.getY() - 15);
        Vec2 p3 = new Vec2(maxDistance - (maxDistance * .75f), holeCenter.getY() - 35);
        Vec2 p4 = new Vec2(maxDistance + (maxDistance * .25f), holeCenter.getY() - 50);
        int cornerX = this.chunkCorner.getX();
        int cornerY = this.holeCenter.getY();
        int cornerZ = this.chunkCorner.getZ();
        BlockPos.MutableBlockPos carve = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos carveAbove = new BlockPos.MutableBlockPos();
        carve.set(cornerX, cornerY, cornerZ);
        carveAbove.set(cornerX, cornerY, cornerZ);
        for (int y = 50; y > -5; y--) {
            float position = (float)y / 50f;
            Vec2 splinePosition = p1
                    .scale(-(position * position * position) + (3 * position * position) - (3 * position) + 1)
                    .add(p2.scale((3 * position * position * position) - (6 * position * position) + (3 * position)))
                    .add(p3.scale(-(3 * position * position * position) + (3 * position * position)))
                    .add(p4.scale(position * position * position));
            LogUtils.getLogger().info("Spline Position at Y:{} | ({}, {})", y, splinePosition.x , splinePosition.y);
            for (int x = 0; x <= 16; x++) {
                for (int z = 0; z <= 16; z++) {
                    carve.set(cornerX + x, Mth.clamp(cornerY - y, level.getMinBuildHeight(), level.getMaxBuildHeight()),
                            cornerZ + z);
                    carveAbove.set(carve.getX(), carve.getY() + 1, carve.getZ());
                    double distToCenter = carve.distToCenterSqr(this.holeCenter.getX(), carve.getY(),
                            this.holeCenter.getZ());
                    //(radius * radius)
                    if (distToCenter < splinePosition.x) {
                        if (distToCenter <= 1 && carve.getY() <= cornerY)
                            level.setBlock(carve,
                                    ModBlocks.SEPUCHRAL_STONE_BLOCK.get().defaultBlockState(), 128);
                        else
                            level.setBlock(carve,
                                    Blocks.AIR.defaultBlockState(), 128);
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

}
