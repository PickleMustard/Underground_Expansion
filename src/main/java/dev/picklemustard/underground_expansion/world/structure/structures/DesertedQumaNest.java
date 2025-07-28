package dev.picklemustard.underground_expansion.world.structure.structures;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.mojang.serialization.MapCodec;

import dev.picklemustard.underground_expansion.world.structure.ModStructures;
import dev.picklemustard.underground_expansion.world.structure.pieces.DesertedQumaNestStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class DesertedQumaNest extends Structure {

    public static final MapCodec<DesertedQumaNest> CODEC = simpleCodec(DesertedQumaNest::new);

    protected DesertedQumaNest(StructureSettings settings) {
        super(settings);
    }

    public DesertedQumaNest(StructureSettings settings, Holder<StructureTemplatePool> startPool, int size,
            HeightProvider startHeight, int maxDistanceFromCenter) {
        super(settings);
        // this.size = size;
        // this.startPool = startPool;
        // this.startHeight = startHeight;
        // this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    // Find a suitable generation position to generate the structure within
    //
    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return determineStructure(context, Heightmap.Types.OCEAN_FLOOR_WG, (builder) -> {
            this.generatePieces(builder, context);
        });
    }

    public Optional<Structure.GenerationStub> determineStructure(Structure.GenerationContext context,
            Heightmap.Types heightMap, Consumer<StructurePiecesBuilder> builderConsumer) {
        ChunkPos structurePosition = context.chunkPos();
        int xPos = structurePosition.getMiddleBlockX();
        int zPos = structurePosition.getMiddleBlockZ();
        LevelHeightAccessor levelHeight = context.heightAccessor();
        int yPos = context.chunkGenerator().getBaseHeight(xPos, zPos, Heightmap.Types.OCEAN_FLOOR_WG, levelHeight,
                context.randomState());
        if (yPos < context.chunkGenerator().getSeaLevel()) {
            return Optional.empty();
        }
        return Optional.of(new Structure.GenerationStub(new BlockPos(xPos, yPos, zPos), builderConsumer));
    }

    public void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
        ChunkPos centerPiece = context.chunkPos();
        int radius = context.random().nextInt(5, 12);
        int heightBlocks = context.random().nextInt(25, 50);
        int surfaceHeight = context.chunkGenerator().getBaseHeight(centerPiece.getMinBlockX(),
                centerPiece.getMinBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(),
                context.randomState());
        BlockPos center = new BlockPos(centerPiece.getMinBlockX(),
                surfaceHeight - context.random().nextInt(10, 20),
                centerPiece.getMinBlockZ());
        for (int xOffset = -1; xOffset <= 0; xOffset++) {
            for (int zOffset = -1; zOffset <= 0; zOffset++) {
                DesertedQumaNestStructurePieces.DespotsChamber qumanest$despotchamber = new DesertedQumaNestStructurePieces.DespotsChamber(
                        center.offset(new BlockPos(16 * xOffset, 0, 16 * zOffset)), center, heightBlocks,
                        radius, surfaceHeight);
                builder.addPiece(qumanest$despotchamber);
                qumanest$despotchamber.addChildren(qumanest$despotchamber, builder, context.random());
                List<StructurePiece> list = qumanest$despotchamber.pendingChildren;

            }
        }
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.DESERTED_QUMA_NEST.get();
    }

}
