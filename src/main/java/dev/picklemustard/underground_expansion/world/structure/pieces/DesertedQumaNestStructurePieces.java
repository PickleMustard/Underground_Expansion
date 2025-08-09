package dev.picklemustard.underground_expansion.world.structure.pieces;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.phys.Vec2;

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

    private static final int MAX_DEPTH = 100;
    public static final int MAGIC_START_Y = 64;

    static final DesertedQumaNestStructurePieces.ChamberWeight[] CHAMBER_WEIGHTS = new ChamberWeight[] {
    };

    static final DesertedQumaNestStructurePieces.ChamberWeight[] TUNNEL_WEIGHTS = new ChamberWeight[] {
            new ChamberWeight(NestTunnel.class, 30, 0),
    };

    protected enum NestPieceType implements StringRepresentable {
        CHAMBER(0, "chamber"),
        TUNNEL(1, "tunnel");

        private final String name;
        private final int index;

        private NestPieceType(int index, String name) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public static DesertedQumaNestPiece pieceCreationFactory(ChamberWeight weight, StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction orientation, int genDepth) {
        if(weight.pieceClass == NestTunnel.class) {
            return NestTunnel.createPiece(pieces, x, y, z, orientation, genDepth, 0.5f, 0.5f);
        } else if(weight.pieceClass == DespotsChamber.class) {
            return DespotsChamber.startStructure(x, y, z, weight.maxPlaceCount, weight.weight, genDepth, orientation);
        }
        return null;
    }
    public abstract static class DesertedQumaNestPiece extends StructurePiece {
        protected NestPieceType pieceType;

        public DesertedQumaNestPiece(StructurePieceType type, int genDepth, BoundingBox box, NestPieceType pieceType) {
            super(type, genDepth, box);
            this.pieceType = pieceType;
        }

        public DesertedQumaNestPiece(StructurePieceType type, CompoundTag tag) {
            super(type, tag);
        }

        public DesertedQumaNestPiece(StructurePieceType piece, BoundingBox box) {
            super(piece, 0, box);
        }

        protected static BoundingBox createBoundingBox(BlockPos center, int minY, int maxY) {
            ChunkPos corner = new ChunkPos(center);
            return new BoundingBox(corner.getMinBlockX(), minY, corner.getMinBlockZ(), corner.getMaxBlockX(), maxY,
                    corner.getMaxBlockZ());
        }

        protected StructurePiece generateChild(StructurePieceAccessor pieces, RandomSource random,DesertedQumaNestStructurePieces.DespotsChamber startPiece,
                BlockPos startingCoord, Direction orientation, int genDepth, boolean isTunnel) {
            ChamberWeight chosenPiece = CHAMBER_WEIGHTS[random.nextInt(CHAMBER_WEIGHTS.length)];
            if (isTunnel) {
                chosenPiece = TUNNEL_WEIGHTS[random.nextInt(TUNNEL_WEIGHTS.length)];
            }

            StructurePiece piece = pieceCreationFactory(chosenPiece, pieces, random, startingCoord.getX(), startingCoord.getY(), startingCoord.getZ(), orientation, genDepth);
            return piece;

        }

        protected static boolean isValidBox(BoundingBox bb) {
            return bb != null && bb.minY() > -30;
        }

    }

    static class ChamberWeight {
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

    public static class NestTunnel extends DesertedQumaNestPiece {
        private final int radius;
        private final int length;
        private final int depth;
        private final BlockPos startingCoord;

        public NestTunnel(StructurePieceType type, int genDepth, BoundingBox boundingBox, int radius, int length,
                BlockPos startingCoord) {
            super(type, genDepth, boundingBox, NestPieceType.TUNNEL);
            this.length = length;
            this.radius = radius;
            this.depth = genDepth;
            this.startingCoord = startingCoord;
        }

        public NestTunnel(StructurePieceSerializationContext context, CompoundTag tag) {
            this(tag);
        }

        public NestTunnel(CompoundTag tag) {
            super(ModPieces.DESERTED_QUMA_NEST_TUNNEL_SP.get(), tag);
            this.length = tag.getInt("Length");
            this.depth = tag.getInt("Depth");
            this.radius = tag.getInt("Radius");
            this.startingCoord = new BlockPos(tag.getInt("SCX"), tag.getInt("SCY"), tag.getInt("SCZ"));
        }

        public static NestTunnel createPiece(StructurePieceAccessor pieces, int x, int y, int z, Direction orientation,
                int genDepth, float radiusModifier, float lengthModifier) {
            int radius = Mth.ceil(3.0f * Mth.log2(genDepth) * radiusModifier);
            int length = Mth.ceil(3.0f * Mth.log2(genDepth) * lengthModifier);

            BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, x - radius, y - radius, z - radius,
                    x + length + radius, y + radius, z + length + radius, orientation);
            if (isValidBox(boundingBox)) {
                return new NestTunnel(ModPieces.DESERTED_QUMA_NEST_TUNNEL_SP.get(), genDepth, boundingBox, radius,
                        length, new BlockPos(x, y, z));
            }
            return null;

        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            tag.putInt("Length", length);
            tag.putInt("Depth", depth);
            tag.putInt("Radius", radius);
            tag.putInt("SCX", startingCoord.getX());
            tag.putInt("SCY", startingCoord.getY());
            tag.putInt("SCZ", startingCoord.getZ());
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator,
                RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {

        }
    }

    public static class DespotsChamber extends DesertedQumaNestPiece {
        public final List<StructurePiece> pendingChildren = Lists.newArrayList();

        private final int radius;
        private final int chamberHeight;
        private final int generationDepth;
        private final Direction orientation;
        private final BlockPos structureCenter;

        public DespotsChamber(BlockPos structureCenter, int structureHeight, int radius, int generationDepth,
                Direction orientation, BoundingBox bb) {
            super(ModPieces.DESERTED_QUMA_NEST_DESPOT_CHAMBER_SP.get(), generationDepth, bb, NestPieceType.CHAMBER);
            this.radius = radius;
            this.chamberHeight = structureHeight;
            this.structureCenter = structureCenter;
            this.generationDepth = generationDepth;
            this.orientation = orientation;

        }

        public DespotsChamber(StructurePieceSerializationContext context, CompoundTag tag) {
            this(tag);
        }

        public DespotsChamber(CompoundTag tag) {
            super(ModPieces.DESERTED_QUMA_NEST_DESPOT_CHAMBER_SP.get(), tag);
            this.structureCenter = new BlockPos(tag.getInt("HCX"), tag.getInt("HCY"), tag.getInt("HCZ"));
            this.chamberHeight = tag.getInt("Height");
            this.radius = tag.getInt("Radius");
            this.generationDepth = tag.getInt("genDepth");
            this.orientation = Direction.byName(tag.getString("Orientation"));
        }

        public static DespotsChamber startStructure(int x, int y, int z, int structureHeight, int radius, int generationDepth,
                Direction orientation) {
            BoundingBox bb = BoundingBox.orientBox(x, y, z, x - radius, y - structureHeight, z - radius, x + radius,
                    y + structureHeight, z + radius, orientation);
            if (isValidBox(bb)) {
                return new DespotsChamber(new BlockPos(x, y, z), structureHeight, radius, generationDepth, orientation,
                        bb);
            }
            return null;
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            tag.putInt("HCX", this.structureCenter.getX());
            tag.putInt("HCY", this.structureCenter.getY());
            tag.putInt("HCZ", this.structureCenter.getZ());
            tag.putInt("Height", this.chamberHeight);
            tag.putInt("Radius", this.radius);
            tag.putInt("genDepth", this.generationDepth);
            tag.putString("Orientation", orientation.name());
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator,
                RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            int verticalSize = random.nextInt(50, 65);
            BlockPos.MutableBlockPos carve = new BlockPos.MutableBlockPos();
            carve.set(structureCenter);
            for (int y = chamberHeight; y > (chamberHeight - verticalSize); --y) {
                for (int x = 0; x <= 16; ++x) {
                    for (int z = 0; z <= 16; ++z) {
                        carve.set(structureCenter.getX() + x, structureCenter.getY() - y, structureCenter.getZ() + z);
                        double distToCenter = carve.distToCenterSqr(this.structureCenter.getX(), carve.getY(),
                                this.structureCenter.getZ());
                        if (distToCenter <= radius) {
                            level.setBlock(carve, Blocks.CAVE_AIR.defaultBlockState(), 128);
                        }
                    }
                }

            }
        }

        @Override
        public void addChildren(StructurePiece piece, StructurePieceAccessor pieces, RandomSource random) {
            /*
             * The initial chamber needs to generate a list of size
             * ceil(structureGenerationHeight / 15)
             *
             */
            int levels = Mth.ceil(chamberHeight / 15.0f);
            for (int level = 0; level < levels; ++level) {
                int corridors = random.nextInt(2, 5);
                for (int nextCorridor = 0; nextCorridor < corridors; ++nextCorridor) {
                    generateChild(pieces, random, (DespotsChamber)piece, structureCenter, orientation, generationDepth, false);

                }
            }

        }
    }
}
