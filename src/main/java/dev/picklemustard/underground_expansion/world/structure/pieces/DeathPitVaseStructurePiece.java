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
        super(ModPieces.DEATH_PIT_VASE_SP.get(), 0, createBoundingBox(chunkCorner, chunkCorner.getY()-50, chunkCorner.getY() + 50));
    }

    private static BoundingBox createBoundingBox(BlockPos center, int minY, int maxY) {
        ChunkPos corner = new ChunkPos(center);
        return new BoundingBox(corner.getMinBlockX(), minY, corner.getMinBlockZ(), corner.getMaxBlockX(), maxY, corner.getMaxBlockZ());
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
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 200; y++) {
                for(int z = 0; z < 16; z++ ) {
                    if((x % 3) + (y % 3) + (z % 3) < 5) {
                        level.setBlock(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z), Blocks.AIR.defaultBlockState(), 128);
                        //checkedSetBlock(level, new BlockPos(x, y, z), Blocks.CAVE_AIR.defaultBlockState());
                    } else {
                        level.setBlock(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z), ModBlocks.SEPUCHRAL_STONE_BLOCK.get().defaultBlockState(), 128);
                        //checkedSetBlock(level, new BlockPos(x, y, z), ModBlocks.SEPUCHRAL_STONE_BLOCK.get().defaultBlockState());
                    }
                }
            }
        }

    }

    public void checkedSetBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
        if(this.getBoundingBox().isInside(pos)){
            level.setBlock(pos, state, 128);
        }
    }


}
