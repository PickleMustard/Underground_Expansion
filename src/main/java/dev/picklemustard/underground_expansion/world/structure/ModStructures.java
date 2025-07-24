package dev.picklemustard.underground_expansion.world.structure;

import java.util.function.Supplier;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.world.structure.structures.DeathPitVase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructures {

    public static ResourceKey<Structure> DEATH_PIT_VASE_KEY = createStructureKey("death_pit_vase");
    public static ResourceKey<StructureSet> DEATH_PIT_VASE_STRUCTURE_SET_KEY = createStructureSetKey("death_pit_vase");

    public static DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UndergroundExpansion.MODID);

    public static DeferredHolder<StructureType<?>, StructureType<DeathPitVase>> DEATH_PIT_VASE = STRUCTURES.register("death_pit_vase", () -> () -> DeathPitVase.CODEC);
    //public static DeferredHolder<StructureType<?>, StructureType<>> SPEAK_EASY = STRUCTURES.register("speak_easy", );

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }

    private static ResourceKey<Structure> createStructureKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(UndergroundExpansion.MODID, name));
    }

    private static ResourceKey<StructureSet> createStructureSetKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(UndergroundExpansion.MODID, name));
    }
}
