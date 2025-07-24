package dev.picklemustard.underground_expansion.world.level.biome;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {
    public static final TagKey<Biome> IS_DEATH_PIT = register("is_death_pit");

    private static TagKey<Biome> register(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(UndergroundExpansion.MODID, name));

    }
}
