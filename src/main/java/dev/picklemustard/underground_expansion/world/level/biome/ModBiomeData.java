package dev.picklemustard.underground_expansion.world.level.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;


/**
 * <h2>ModbiomeData</h2>
 * Generates JSON definitions for a biome when creating data
 */
public class ModBiomeData {
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatureHG = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> worldCarverHG = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(ModBiomes.DEATH_PIT, ModBiomes.deathPit(placedFeatureHG, worldCarverHG));
        context.register(ModBiomes.DESERTED_NEST, ModBiomes.desertedNest(placedFeatureHG, worldCarverHG));
    }
}
