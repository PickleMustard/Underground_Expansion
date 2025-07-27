package dev.picklemustard.underground_expansion.world.structure.pieces;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;


/**
 * The set of pieces that make up a Deserted Quma Nest.
 */

/*
 * Start piece is a Quma Despot Chamber that branches off into hallways
 * Hallways can branch into more hallways, multiple hallways, or different rooms
 *
 * Hallways will be rounded cylinders that will extend for different lengths
 * The shape of the hallway will determine the method it is generated, between linear, spline,
 * A linear hallway
 * Hallways can have a shape, length, radius
 *
 */
public class DesertedQumaNestStructurePieces {

    abstract static class DesertedQumaNestPiece extends StructurePiece {

        public DesertedQumaNestPiece(BlockPos chunkCorner, BlockPos holeCenter, int height, int radius,
                int surfaceHeight, RandomState randState) {
            super(ModPieces.DESERTED_QUMA_NEST_SP.get(), 0,
                    createBoundingBox(chunkCorner, chunkCorner.getY() - 16, chunkCorner.getY() + 16));
            // this.chunkCorner = chunkCorner;
            // this.holeCenter = holeCenter;
            // this.height = height;
            // this.radius = radius;
            // this.surfaceHeight = surfaceHeight;
        }

        private static BoundingBox createBoundingBox(BlockPos center, int minY, int maxY) {
            ChunkPos corner = new ChunkPos(center);
            return new BoundingBox(corner.getMinBlockX(), minY, corner.getMinBlockZ(), corner.getMaxBlockX(), maxY,
                    corner.getMaxBlockZ());
        }

        public DesertedQumaNestPiece(CompoundTag tag) {
            super(ModPieces.DESERTED_QUMA_NEST_SP.get(), tag);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addAdditionalSaveData'");
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator,
                RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'postProcess'");

        }
    }

}
