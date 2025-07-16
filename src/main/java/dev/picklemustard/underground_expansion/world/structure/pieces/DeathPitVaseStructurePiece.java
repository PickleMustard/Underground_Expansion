package dev.picklemustard.underground_expansion.world.structure.pieces;

import com.mojang.logging.LogUtils;

import dev.picklemustard.underground_expansion.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

public class DeathPitVaseStructurePiece extends StructurePiece {

    public DeathPitVaseStructurePiece(BlockPos chunkCorner, BlockPos holeCenter, int height, int radius) {
        super(ModPieces.DEATH_PIT_VASE_SP.get(), 0,
                createBoundingBox(chunkCorner, chunkCorner.getY() - 50, chunkCorner.getY() + 50));
    }

    private static BoundingBox createBoundingBox(BlockPos center, int minY, int maxY) {
        ChunkPos corner = new ChunkPos(center);
        return new BoundingBox(corner.getMinBlockX(), minY, corner.getMinBlockZ(), corner.getMaxBlockX(), maxY,
                corner.getMaxBlockZ());
    }

    public DeathPitVaseStructurePiece(CompoundTag tag) {
        super(ModPieces.DEATH_PIT_VASE_SP.get(), tag);
    }

    public DeathPitVaseStructurePiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(tag);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator,
            RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        LogUtils.getLogger().info("BlockPos (x,y,z): ({}, {}, {})", pos.getX(), pos.getY(), pos.getZ());
        int radius = random.nextInt(5, 12);
        for (int y = 16; y > 0; y--) {
            for (int x = -radius / 2; x <= radius / 2; x++) {
                for (int z = -radius / 2; z <= radius / 2; z++) {
                    int location = (x * x) + (z * z);
                    if (location <= (radius * radius)) {
                        if (location <= 1)
                            level.setBlock(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z),
                                    ModBlocks.SEPUCHRAL_STONE_BLOCK.get().defaultBlockState(), 128);
                        else
                            level.setBlock(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z),
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
