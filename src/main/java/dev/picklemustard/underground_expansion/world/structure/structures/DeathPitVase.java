package dev.picklemustard.underground_expansion.world.structure.structures;

import java.util.Optional;
import java.util.function.Consumer;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;

import dev.picklemustard.underground_expansion.world.structure.ModStructures;
import dev.picklemustard.underground_expansion.world.structure.pieces.DeathPitVaseStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class DeathPitVase extends Structure {

    private final int size;
    private final Holder<StructureTemplatePool> startPool;
    private final HeightProvider startHeight;
    private final int maxDistanceFromCenter;

    // public static final MapCodec<StructureSettings> CODEC =
    // RecordCodecBuilder.mapCodec(instance -> instance.group(HolderSet.codec(),
    // Codec.simpleMap(MobCategory.CODEC, StructureSpawnOverride.CODEC,
    // StringRepresentable.keys(MobCategory.values())).fieldOf("spawn_overrides").forGetter(StructureSettings::spawnOverrides)).apply(instance,
    // StructureSettings::new));
    //
    //
    //// public static final MapCodec<DeathPitVase> CODEC =
    // RecordCodecBuilder.mapCodec(instance -> instance.group(
    // StructureSettings.CODEC.fieldOf("settings").forGetter(structure ->
    // structure.settings),
    // StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure ->
    // structure.startPool),
    // Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
    // HeightProvider.CODEC.fieldOf("start_height").forGetter(structure ->
    // structure.startHeight),
    // Codec.intRange(1,
    // 128).fieldOf("max_distance_from_center").forGetter(structure ->
    // structure.maxDistanceFromCenter)
    // ).apply(instance, DeathPitVase::new));
    public static final MapCodec<DeathPitVase> CODEC = simpleCodec(DeathPitVase::new);

    public DeathPitVase(StructureSettings settings, Holder<StructureTemplatePool> startPool, int size,
            HeightProvider startHeight, int maxDistanceFromCenter) {
        super(settings);
        this.size = size;
        this.startPool = startPool;
        this.startHeight = startHeight;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    protected DeathPitVase(StructureSettings settings) {
        super(settings);
        size = 0;
        startPool = null;
        startHeight = null;
        maxDistanceFromCenter = 0;
    }

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
        int yPos = 60;
        return Optional.of(new Structure.GenerationStub(new BlockPos(xPos, yPos, zPos), builderConsumer));
    }

    public void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
        ChunkPos centerPiece = context.chunkPos();
        int radius = context.random().nextInt(5, 10);
        int heightBlocks = 5;
        BlockPos center = new BlockPos(centerPiece.getMinBlockX(),
                context.chunkGenerator().getBaseHeight(centerPiece.getMinBlockX(),
                        centerPiece.getMinBlockZ(), Heightmap.Types.WORLD_SURFACE_WG,
                        context.heightAccessor(), context.randomState()),
                // */ context.chunkGenerator().getSeaLevel() - context.random().nextInt(0, 20),
                centerPiece.getMinBlockZ());
        for (int xOffset = -1; xOffset <= 0; xOffset++) {
            for (int zOffset = -1; zOffset <= 0; zOffset++) {
                StructurePiece piece = new DeathPitVaseStructurePiece(
                        center.offset(new BlockPos(16 * xOffset, 0, 16 * zOffset)), center, heightBlocks,
                        radius);
                builder.addPiece(piece);
            }
        }
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.DEATH_PIT_VASE.get();
    }

}
