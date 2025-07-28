package dev.picklemustard.underground_expansion.world.structure.pieces;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

/**
 * The set of pieces that make up a Deserted Quma Nest.
 */

/*
 * Start piece is a Quma Despot Chamber that branches off into hallways
 * Hallways can branch into more hallways, multiple hallways, or different rooms
 *
 * Hallways will be rounded cylinders that will extend for different lengths
 * The shape of the hallway will determine the method it is generated, between
 * linear, spline,
 * A linear hallway
 * Hallways can have a shape, length, radius
 *
 */
public class DesertedQumaNestStructurePieces {

    public abstract static class DesertedQumaNestPiece extends StructurePiece {
        protected BlockPos chunkCorner;
        protected BlockPos structureCenter;

        public DesertedQumaNestPiece(BlockPos chunkCorner, BlockPos structureCenter, int height, int radius,
                int surfaceHeight, RandomState randState) {
            super(ModPieces.DESERTED_QUMA_NEST_SP.get(), 0,
                    createBoundingBox(chunkCorner, chunkCorner.getY() - 16, chunkCorner.getY() + 16));
             this.chunkCorner = chunkCorner;
             this.structureCenter = structureCenter;
        }

        public DesertedQumaNestPiece(StructurePieceType piece, BoundingBox box) {
            super(piece, 0, box);
        }

        protected static BoundingBox createBoundingBox(BlockPos center, int minY, int maxY) {
            ChunkPos corner = new ChunkPos(center);
            return new BoundingBox(corner.getMinBlockX(), minY, corner.getMinBlockZ(), corner.getMaxBlockX(), maxY,
                    corner.getMaxBlockZ());
        }

        public DesertedQumaNestPiece(CompoundTag tag) {
            super(ModPieces.DESERTED_QUMA_NEST_SP.get(), tag);
        }
    }

    static class ChamberWeight{
        public final Class<? extends DesertedQumaNestPiece> pieceClass;
        public final int weight;
        public int placeCount;
        public final int maxPlaceCount;

        public ChamberWeight(Class<? extends DesertedQumaNestPiece> chamberType, int weight, int maxPlaceCount) {
            this.pieceClass = chamberType;
            this.weight = weight;
            this.maxPlaceCount = maxPlaceCount;
        }

        public boolean doPlace(int genDepth) {
            return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
        }

        public boolean isValid() {
            return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
        }

    }

    public static class DespotsChamber extends DesertedQumaNestPiece {
        public final List<StructurePiece> pendingChildren = Lists.newArrayList();

        private final int radius;
        private final int height;
        private final int surfaceHeight;

        public DespotsChamber(BlockPos chunkCorner, BlockPos structureCenter, int height, int radius, int surfaceHeight) {
            super(ModPieces.DESERTED_QUMA_NEST_SP.get(),
                    createBoundingBox(chunkCorner, chunkCorner.getY() - 16, chunkCorner.getY() + 16));
            this.radius = radius;
            this.height = height;
            this.surfaceHeight = surfaceHeight;
            this.chunkCorner = chunkCorner;
            this.structureCenter = structureCenter;
        }

        public DespotsChamber(StructurePieceSerializationContext context, CompoundTag tag) {
            this(tag);
        }

        public DespotsChamber(CompoundTag tag) {
            super(tag);
            this.structureCenter = new BlockPos(tag.getInt("HCX"), tag.getInt("HCY"), tag.getInt("HCZ"));
            this.chunkCorner = new BlockPos(tag.getInt("TPX"), tag.getInt("TPY"), tag.getInt("TPZ"));
            this.height = tag.getInt("Height");
            this.radius = tag.getInt("Radius");
            this.surfaceHeight = tag.getInt("SurfaceHeight");
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            tag.putInt("TPX", this.chunkCorner.getX());
            tag.putInt("TPY", this.chunkCorner.getY());
            tag.putInt("TPZ", this.chunkCorner.getZ());
            tag.putInt("HCX", this.structureCenter.getX());
            tag.putInt("HCY", this.structureCenter.getY());
            tag.putInt("HCZ", this.structureCenter.getZ());
            tag.putInt("Height", this.height);
            tag.putInt("Radius", this.radius);
            tag.putInt("SurfaceHeight", this.surfaceHeight);
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator,
                RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            int cornerX = this.chunkCorner.getX();
            int cornerZ = this.chunkCorner.getZ();
            int cornerY = this.structureCenter.getY();
            BlockPos.MutableBlockPos carve = new BlockPos.MutableBlockPos();
            carve.set(cornerX, cornerY, cornerZ);
            for(int y = height; y > 0; ++y) {
                for(int x = 0; x <= 16; ++x) {
                    for(int z = 0; z <= 16; ++z) {
                        carve.set(cornerX + x, cornerY - y, cornerZ + z);
                        double distToCenter = carve.distToCenterSqr(this.structureCenter.getX(), carve.getY(), this.structureCenter.getZ());
                        if(distToCenter <= radius) {
                            level.setBlock(carve, Blocks.CAVE_AIR.defaultBlockState(), 128);
                        }
                    }
                }

            }
        }

    }

}
