package dev.picklemustard.underground_expansion.world.level.biome;

import javax.annotation.Nullable;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.world.level.carvers.ModCarverRegistration;
import dev.picklemustard.underground_expansion.world.level.carvers.ModCarvers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {
    //Grounded Biomes
    public static final ResourceKey<Biome> DEATH_PIT = register("death_pit");
    public static final ResourceKey<Biome> GLACIAL_REMANENT = register("glacial_remanent");
    public static final ResourceKey<Biome> DORMANT_VOLCANO = register("dormant_volcano");

    //Inbetween Biomes
    public static final ResourceKey<Biome> AQUIFER_OASIS = register("aquifer_oasis");
    public static final ResourceKey<Biome> DESERTED_NEST = register("deserted_nest");
    public static final ResourceKey<Biome> SULPHUR_POOLS = register("sulphur_pools");

    //Fantasy Biomes
    public static final ResourceKey<Biome> BURIED_METROPOLIS = register("buried_metropolis");
    public static final ResourceKey<Biome> INVERSE_CAVE = register("inverse_cave");
    public static final ResourceKey<Biome> FLESHY_MASS = register("fleshy_mass");
    public static final ResourceKey<Biome> HEAVENLY_INTRUSION = register("heavenly_intrusion");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(UndergroundExpansion.MODID, name));
    }

    private static Biome biome(boolean hasPrecipitation, float temp, float downfall, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music bgm) {
        return biome(hasPrecipitation, temp, downfall, 4159204, 329011, null, null, mobSpawnSettings, generationSettings, bgm);
    }

    private static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int waterColor,
            int waterFogColor, @Nullable Integer grassColorOverride, @Nullable Integer foliageColorOverride,
            MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings,
            @Nullable Music backgroundMusic) {
        BiomeSpecialEffects.Builder biomespecialeffects$builder = new BiomeSpecialEffects.Builder()
                .waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(12638463)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(backgroundMusic);
        if (grassColorOverride != null)
            biomespecialeffects$builder.grassColorOverride(grassColorOverride);
        if (foliageColorOverride != null)
            biomespecialeffects$builder.foliageColorOverride(foliageColorOverride);

        return new Biome.BiomeBuilder().hasPrecipitation(hasPrecipitation).temperature(temperature).downfall(downfall)
                .specialEffects(biomespecialeffects$builder.build()).mobSpawnSettings(mobSpawnSettings.build())
                .generationSettings(generationSettings.build()).build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder generationSettings) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
    }

    public static Biome deathPit(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        MobSpawnSettings.Builder mbs$builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mbs$builder);
        BiomeGenerationSettings.Builder bgs$builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        bgs$builder.addCarver(GenerationStep.Carving.AIR, ModCarverRegistration.LAVA_TUBE);
        BiomeDefaultFeatures.addDefaultOres(bgs$builder);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(bgs$builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(bgs$builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(bgs$builder);
        Music bgm = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS);

        return biome(false, 0.5f, 0.0f, mbs$builder, bgs$builder, bgm);
    }

    public static Biome desertedNest(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        MobSpawnSettings.Builder mbs$builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mbs$builder);
        BiomeGenerationSettings.Builder bgs$builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        BiomeDefaultFeatures.addDefaultOres(bgs$builder);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(bgs$builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(bgs$builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(bgs$builder);
        Music bgm = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS);

        return biome(false, 0.65f, 0.0f, mbs$builder, bgs$builder, bgm);

    }

}
