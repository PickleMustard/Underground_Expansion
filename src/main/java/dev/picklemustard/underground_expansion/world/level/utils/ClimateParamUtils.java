package dev.picklemustard.underground_expansion.world.level.utils;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.world.level.biome.Climate;

public class ClimateParamUtils {
    public static Climate.Parameter span(float min, float max) {
        return Climate.Parameter.span(min, max);
    }

    public static class ParameterPointListBuilder {
        private List<Climate.Parameter> Temperatures = Lists.newArrayList();
        private List<Climate.Parameter> Humidities = Lists.newArrayList();
        private List<Climate.Parameter> Continentalnesses = Lists.newArrayList();
        private List<Climate.Parameter> Erosions = Lists.newArrayList();
        private List<Climate.Parameter> Depths = Lists.newArrayList();
        private List<Climate.Parameter> Weirdnesses = Lists.newArrayList();
        private List<Long> Offsets = Lists.newArrayList();

        /**
         * Adds temperature parameters to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder temperature(Climate.Parameter... values) {
            this.Temperatures.addAll(Arrays.asList(values));
            return this;
        }

        /**
         * Adds temperature values to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder temperature(Temperature... values) {
            this.Temperatures.addAll(Arrays.asList(values).stream().map(Temperature::parameter).toList());
            return this;
        }

        /**
         * Adds humidity parameters to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder humidity(Climate.Parameter... values) {
            this.Humidities.addAll(Arrays.asList(values));
            return this;
        }

        /**
         * Adds humidity values to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder humidity(Humidity... values) {
            this.Humidities.addAll(Arrays.asList(values).stream().map(Humidity::parameter).toList());
            return this;
        }

        /**
         * Adds continentalness parameters to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder continentalness(Climate.Parameter... values) {
            this.Continentalnesses.addAll(Arrays.asList(values));
            return this;
        }

        /**
         * Adds continentalness values to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder continentalness(Continentalness... values) {
            this.Continentalnesses.addAll(Arrays.asList(values).stream().map(Continentalness::parameter).toList());
            return this;
        }

        /**
         * Adds erosion parameters to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder erosion(Climate.Parameter... values) {
            this.Erosions.addAll(Arrays.asList(values));
            return this;
        }

        /**
         * Adds erosion values to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder erosion(Erosion... values) {
            this.Erosions.addAll(Arrays.asList(values).stream().map(Erosion::parameter).toList());
            return this;
        }

        /**
         * Adds depth parameters to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder depth(Climate.Parameter... values) {
            this.Depths.addAll(Arrays.asList(values));
            return this;
        }

        /**
         * Adds depth values to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder depth(Depth... values) {
            this.Depths.addAll(Arrays.asList(values).stream().map(Depth::parameter).toList());
            return this;
        }

        /**
         * Adds weirdness parameters to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder weirdness(Climate.Parameter... values) {
            this.Weirdnesses.addAll(Arrays.asList(values));
            return this;
        }

        /**
         * Adds weirdness values to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder weirdness(Weirdness... values) {
            this.Weirdnesses.addAll(Arrays.asList(values).stream().map(Weirdness::parameter).toList());
            return this;
        }

        /**
         * Adds offset values to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder offset(Float... values) {
            this.Offsets.addAll(Arrays.asList(values).stream().map(Climate::quantizeCoord).toList());
            return this;
        }

        /**
         * Adds offset values to the list.
         *
         * @param values values to be added.
         */
        public ParameterPointListBuilder offset(Long... values) {
            this.Offsets.addAll(Arrays.asList(values));
            return this;
        }

        /**
         * Builds a list of {@link Climate.ParameterPoint}
         *
         * @return the parameter point list.
         */
        public List<Climate.ParameterPoint> build() {
            this.populateIfEmpty();
            ImmutableList.Builder<Climate.ParameterPoint> builder = new ImmutableList.Builder<>();
            this.Temperatures.forEach(temperature -> this.Humidities.forEach(humidity -> this.Continentalnesses
                    .forEach(continentalness -> this.Erosions.forEach(erosion -> this.Depths
                            .forEach(depth -> this.Weirdnesses.forEach(weirdness -> this.Offsets.forEach(offset -> {
                                builder.add(new Climate.ParameterPoint(temperature, humidity, continentalness, erosion,
                                        depth, weirdness, offset));
                            })))))));
            return builder.build();
        }

        private void populateIfEmpty() {
            if (this.Temperatures.isEmpty())
                this.Temperatures.add(Temperature.FULL_RANGE.parameter());
            if (this.Humidities.isEmpty())
                this.Humidities.add(Humidity.FULL_RANGE.parameter());
            if (this.Continentalnesses.isEmpty())
                this.Continentalnesses.add(Continentalness.FULL_RANGE.parameter());
            if (this.Erosions.isEmpty())
                this.Erosions.add(Erosion.FULL_RANGE.parameter());
            if (this.Depths.isEmpty())
                this.Depths.add(Depth.FULL_RANGE.parameter());
            if (this.Weirdnesses.isEmpty())
                this.Weirdnesses.add(Weirdness.FULL_RANGE.parameter());
            if (this.Offsets.isEmpty())
                this.Offsets.add(Climate.quantizeCoord(0.0F));
        }
    }

    public enum Temperature {
        ICY(Climate.Parameter.span(-1.0F, -0.45F)),
        COOL(Climate.Parameter.span(-0.45F, -0.15F)),
        NEUTRAL(Climate.Parameter.span(-0.15F, 0.2F)),
        WARM(Climate.Parameter.span(0.2F, 0.55F)),
        HOT(Climate.Parameter.span(0.55F, 1.0F)),
        FROZEN(Climate.Parameter.span(-1.0F, -0.45F)),
        UNFROZEN(Climate.Parameter.span(-0.45F, 1.0F)),
        FULL_RANGE(Climate.Parameter.span(-1.0F, 1.0F));

        private final Climate.Parameter parameter;

        Temperature(Climate.Parameter parameter) {
            this.parameter = parameter;
        }

        public Climate.Parameter parameter() {
            return this.parameter;
        }

        public static Climate.Parameter span(Temperature min, Temperature max) {
            return Climate.Parameter.span(Climate.unquantizeCoord(min.parameter().min()),
                    Climate.unquantizeCoord(max.parameter().max()));
        }
    }

    /**
     * Preset values for humidity parameters.
     */
    public enum Humidity {
        ARID(Climate.Parameter.span(-1.0F, -0.35F)),
        DRY(Climate.Parameter.span(-0.35F, -0.1F)),
        NEUTRAL(Climate.Parameter.span(-0.1F, 0.1F)),
        WET(Climate.Parameter.span(0.1F, 0.3F)),
        HUMID(Climate.Parameter.span(0.3F, 1.0F)),
        FULL_RANGE(Climate.Parameter.span(-1.0F, 1.0F));

        private final Climate.Parameter parameter;

        Humidity(Climate.Parameter parameter) {
            this.parameter = parameter;
        }

        public Climate.Parameter parameter() {
            return this.parameter;
        }

        public static Climate.Parameter span(Humidity min, Humidity max) {
            return Climate.Parameter.span(Climate.unquantizeCoord(min.parameter().min()),
                    Climate.unquantizeCoord(max.parameter().max()));
        }

    }

    /**
     * Preset values for continentalness parameters.
     */
    public enum Continentalness {
        MUSHROOM_FIELDS(Climate.Parameter.span(-1.2F, -1.05F)),
        DEEP_OCEAN(Climate.Parameter.span(-1.05F, -0.455F)),
        OCEAN(Climate.Parameter.span(-0.455F, -0.19F)),
        COAST(Climate.Parameter.span(-0.19F, -0.11F)),
        NEAR_INLAND(Climate.Parameter.span(-0.11F, 0.03F)),
        MID_INLAND(Climate.Parameter.span(0.03F, 0.3F)),
        FAR_INLAND(Climate.Parameter.span(0.3F, 1.0F)),
        INLAND(Climate.Parameter.span(-0.11F, 0.55F)),
        FULL_RANGE(Climate.Parameter.span(-1.0F, 1.0F));

        private final Climate.Parameter parameter;

        Continentalness(Climate.Parameter parameter) {
            this.parameter = parameter;
        }

        public Climate.Parameter parameter() {
            return this.parameter;
        }

        public static Climate.Parameter span(Continentalness min, Continentalness max) {
            return Climate.Parameter.span(Climate.unquantizeCoord(min.parameter().min()),
                    Climate.unquantizeCoord(max.parameter().max()));
        }
    }

    /**
     * Preset values for erosion parameters.
     */
    public enum Erosion {
        EROSION_0(Climate.Parameter.span(-1.0F, -0.78F)),
        EROSION_1(Climate.Parameter.span(-0.78F, -0.375F)),
        EROSION_2(Climate.Parameter.span(-0.375F, -0.2225F)),
        EROSION_3(Climate.Parameter.span(-0.2225F, 0.05F)),
        EROSION_4(Climate.Parameter.span(0.05F, 0.45F)),
        EROSION_5(Climate.Parameter.span(0.45F, 0.55F)),
        EROSION_6(Climate.Parameter.span(0.55F, 1.0F)),
        FULL_RANGE(Climate.Parameter.span(-1.0F, 1.0F));

        private final Climate.Parameter parameter;

        Erosion(Climate.Parameter parameter) {
            this.parameter = parameter;
        }

        public Climate.Parameter parameter() {
            return this.parameter;
        }

        public static Climate.Parameter span(Erosion min, Erosion max) {
            return Climate.Parameter.span(Climate.unquantizeCoord(min.parameter().min()),
                    Climate.unquantizeCoord(max.parameter().max()));
        }
    }

    public enum Depth {
        HIGH_SKY(Climate.Parameter.span(-1.0F, -0.8F)),
        SKY(Climate.Parameter.span(-0.7F, -0.1F)),
        SURFACE(Climate.Parameter.point(0.0F)),
        SHALLOW_DEPTH(Climate.Parameter.span(0.1F, 0.3F)),
        MID_DEPTH(Climate.Parameter.span(0.4F, 0.7F)),
        MAX_DEPTH(Climate.Parameter.span(0.8F, 0.9F)),
        FULL_RANGE(Climate.Parameter.span(-1.0F, 1.0F));

        public Climate.Parameter parameter() {
            return parameter;
        }

        private final Climate.Parameter parameter;

        Depth(Climate.Parameter parameter) {
            this.parameter = parameter;
        }

        public static Climate.Parameter span(Depth min, Depth max) {
            return Climate.Parameter.span(Climate.unquantizeCoord(min.parameter().min()),
                    Climate.unquantizeCoord(max.parameter().max()));
        }

    }

    /**
     * Preset values for weirdness parameters.
     */
    public enum Weirdness {
        MID_SLICE_NORMAL_ASCENDING(Climate.Parameter.span(-1.0F, -0.93333334F)),
        HIGH_SLICE_NORMAL_ASCENDING(Climate.Parameter.span(-0.93333334F, -0.7666667F)),
        PEAK_NORMAL(Climate.Parameter.span(-0.7666667F, -0.56666666F)),
        HIGH_SLICE_NORMAL_DESCENDING(Climate.Parameter.span(-0.56666666F, -0.4F)),
        MID_SLICE_NORMAL_DESCENDING(Climate.Parameter.span(-0.4F, -0.26666668F)),
        LOW_SLICE_NORMAL_DESCENDING(Climate.Parameter.span(-0.26666668F, -0.05F)),
        VALLEY(Climate.Parameter.span(-0.05F, 0.05F)),
        LOW_SLICE_VARIANT_ASCENDING(Climate.Parameter.span(0.05F, 0.26666668F)),
        MID_SLICE_VARIANT_ASCENDING(Climate.Parameter.span(0.26666668F, 0.4F)),
        HIGH_SLICE_VARIANT_ASCENDING(Climate.Parameter.span(0.4F, 0.56666666F)),
        PEAK_VARIANT(Climate.Parameter.span(0.56666666F, 0.7666667F)),
        HIGH_SLICE_VARIANT_DESCENDING(Climate.Parameter.span(0.7666667F, 0.93333334F)),
        MID_SLICE_VARIANT_DESCENDING(Climate.Parameter.span(0.93333334F, 1.0F)),
        FULL_RANGE(Climate.Parameter.span(-1.0F, 1.0F));

        private final Climate.Parameter parameter;

        Weirdness(Climate.Parameter parameter) {
            this.parameter = parameter;
        }

        public Climate.Parameter parameter() {
            return this.parameter;
        }

        public static Climate.Parameter span(Weirdness min, Weirdness max) {
            return Climate.Parameter.span(Climate.unquantizeCoord(min.parameter().min()),
                    Climate.unquantizeCoord(max.parameter().max()));
        }
    }
}
