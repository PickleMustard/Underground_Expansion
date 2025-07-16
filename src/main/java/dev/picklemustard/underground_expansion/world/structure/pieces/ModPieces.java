package dev.picklemustard.underground_expansion.world.structure.pieces;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPieces {
    public static final DeferredRegister<StructurePieceType> MOD_STRUCTURE_PIECES = DeferredRegister.create(Registries.STRUCTURE_PIECE, UndergroundExpansion.MODID);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> DEATH_PIT_VASE_SP = MOD_STRUCTURE_PIECES.register("death_pit_vase", () -> DeathPitVaseStructurePiece::new);

    public static void register(IEventBus eventBus) {
        MOD_STRUCTURE_PIECES.register(eventBus);
    }


}
