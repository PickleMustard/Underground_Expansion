package dev.picklemustard.underground_expansion.world.level.biome;

import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import dev.picklemustard.underground_expansion.world.level.utils.ClimateParamUtils;
import dev.picklemustard.underground_expansion.world.level.utils.ClimateParamUtils.Continentalness;
import dev.picklemustard.underground_expansion.world.level.utils.ClimateParamUtils.Depth;
import dev.picklemustard.underground_expansion.world.level.utils.ClimateParamUtils.Erosion;
import dev.picklemustard.underground_expansion.world.level.utils.ClimateParamUtils.Humidity;
import dev.picklemustard.underground_expansion.world.level.utils.ClimateParamUtils.ParameterPointListBuilder;
import dev.picklemustard.underground_expansion.world.level.utils.ClimateParamUtils.Temperature;
import dev.picklemustard.underground_expansion.world.level.utils.ClimateParamUtils.Weirdness;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

public class ModRegionsProvider extends Region {

    public ModRegionsProvider(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        new ParameterPointListBuilder().temperature(Temperature.span(Temperature.WARM, Temperature.HOT))
                .humidity(ClimateParamUtils.span(-0.25f, -0.1f)).continentalness(Continentalness.INLAND)
                .erosion(Erosion.EROSION_1, Erosion.EROSION_2).depth(Depth.MID_DEPTH)
                .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING).build()
                .forEach(point -> builder.add(point, ModBiomes.DEATH_PIT));

        new ParameterPointListBuilder().temperature(Temperature.WARM)
                .humidity(Humidity.span(Humidity.NEUTRAL, Humidity.HUMID))
                .continentalness(Continentalness.FAR_INLAND)
                .erosion(Erosion.EROSION_2).depth(Depth.MID_DEPTH).weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, ModBiomes.DESERTED_NEST));

        builder.build().forEach(mapper);

    }

}
