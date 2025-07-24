package dev.picklemustard.underground_expansion.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.world.level.biome.ModBiomeData;
import dev.picklemustard.underground_expansion.world.level.carvers.ModCarverRegistration;
import dev.picklemustard.underground_expansion.world.structure.ModStructureProvider;
import dev.picklemustard.underground_expansion.world.structure.ModStructureSetProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_CARVER, ModCarverRegistration::bootstrap)
            .add(Registries.STRUCTURE, ModStructureProvider::bootstrap)
            .add(Registries.STRUCTURE_SET, ModStructureSetProvider::bootstrap)
            .add(Registries.BIOME, ModBiomeData::bootstrap);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(UndergroundExpansion.MODID));
    }

}
