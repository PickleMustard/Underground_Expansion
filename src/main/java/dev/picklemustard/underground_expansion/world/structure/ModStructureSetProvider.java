package dev.picklemustard.underground_expansion.world.structure;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ModStructureSetProvider {
    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structureHolder = context.lookup(Registries.STRUCTURE);
        HolderGetter<Biome> biomeHolder = context.lookup(Registries.BIOME);

        context.register(ModStructures.DEATH_PIT_VASE_STRUCTURE_SET_KEY,
                new StructureSet(structureHolder.getOrThrow(ModStructures.DEATH_PIT_VASE_KEY),
                        new RandomSpreadStructurePlacement(6, 3, RandomSpreadType.LINEAR, 29485)));

        context.register(ModStructures.DESERTED_QUMA_NEST_SET_KEY,
                new StructureSet(structureHolder.getOrThrow(ModStructures.DESERTED_QUMA_NEST_KEY),
                        new RandomSpreadStructurePlacement(6, 3, RandomSpreadType.LINEAR, 1092875)));
    }

}
