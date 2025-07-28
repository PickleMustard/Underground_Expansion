package dev.picklemustard.underground_expansion.world.level.biome;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {

    public static ResourceKey<BiomeModifier> ADD_DEATH_PIT_TAG = createKey("add_death_pit_tag");
    public static ResourceKey<BiomeModifier> ADD_DESERTED_NEST_TAG = createKey("add_deserted_nest_tag");

   public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomeHolder = context.lookup(Registries.BIOME);

        //context.register(ADD_DEATH_PIT_TAG, new BiomeModifiers.AddFeaturesBiomeModifier(biomes, features, step))
    }

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(UndergroundExpansion.MODID, name));
    }
}
