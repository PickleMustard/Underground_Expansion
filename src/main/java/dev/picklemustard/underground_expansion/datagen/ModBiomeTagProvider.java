package dev.picklemustard.underground_expansion.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.world.level.biome.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBiomeTagProvider extends BiomeTagsProvider {

    public ModBiomeTagProvider(PackOutput output, CompletableFuture<Provider> provider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, UndergroundExpansion.MODID, existingFileHelper);
    }

    public static final TagKey<Biome> IS_DEATH_PIT = register("is_death_pit");
    public static final TagKey<Biome> IS_DESERTED_NEST = register("is_deserted_nest");

    private static TagKey<Biome> register(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(UndergroundExpansion.MODID, name));
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        HolderGetter<Biome> biomeHolder = lookupProvider.lookupOrThrow(Registries.BIOME);
        this.tag(IS_DEATH_PIT).add(ModBiomes.DEATH_PIT);
        this.tag(IS_DESERTED_NEST).add(ModBiomes.DESERTED_NEST);
    }
}
