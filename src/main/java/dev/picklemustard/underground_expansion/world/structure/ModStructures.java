package dev.picklemustard.underground_expansion.world.structure;

import java.util.function.Supplier;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.world.structure.structures.DeathPitVase;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructures {

    public static DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UndergroundExpansion.MODID);

    public static DeferredHolder<StructureType<?>, StructureType<DeathPitVase>> DEATH_PIT_VASE = STRUCTURES.register("death_pit_vase", () -> () -> DeathPitVase.CODEC);
    //public static DeferredHolder<StructureType<?>, StructureType<>> SPEAK_EASY = STRUCTURES.register("speak_easy", );

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }
}
