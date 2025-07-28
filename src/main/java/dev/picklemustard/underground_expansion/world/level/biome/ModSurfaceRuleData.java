package dev.picklemustard.underground_expansion.world.level.biome;

import dev.picklemustard.underground_expansion.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import terrablender.api.SurfaceRuleManager;

public class ModSurfaceRuleData {
    private static final SurfaceRules.RuleSource GRASS = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource PACKED_MUD = makeStateRule(Blocks.PACKED_MUD);
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE);
    }

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource modSurfaceRules = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DEATH_PIT), makeDeathPitRules()),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DESERTED_NEST), makeDesertedNestRules()));
        return modSurfaceRules;
    }

    public static SurfaceRules.RuleSource makeDeathPitRules() {
        SurfaceRules.RuleSource SEPUCHRAL_STONE = SurfaceRules
                .state(ModBlocks.SEPUCHRAL_STONE_BLOCK.get().defaultBlockState());
        SurfaceRules.ConditionSource isUnderwater = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.RuleSource deathPitSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(surfaceNoiseAbove(2.0f), GRAVEL),
                SurfaceRules.ifTrue(surfaceNoiseAbove(1.2f), PACKED_MUD),
                SurfaceRules.ifTrue(surfaceNoiseAbove(-0.4f), COARSE_DIRT), GRAVEL);
        SurfaceRules.RuleSource deathPitSurfaceWaterCheck = SurfaceRules
                .sequence(SurfaceRules.ifTrue(isUnderwater, deathPitSurface), MUD);
        SurfaceRules.RuleSource groundRules = SurfaceRules
                .sequence(SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(60), 0),
                        SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, deathPitSurfaceWaterCheck),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, deathPitSurfaceWaterCheck))));
        SurfaceRules.ConditionSource sepuchralStoneGradientCondition = SurfaceRules.verticalGradient("sepuchralstone",
                VerticalAnchor.absolute(20), VerticalAnchor.absolute(30));
        SurfaceRules.RuleSource deathPitSequence = SurfaceRules.sequence(makeBedrock(), groundRules,
                SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, makeStoneOrDeepslateRules()),
                SurfaceRules.ifTrue(sepuchralStoneGradientCondition, STONE), SEPUCHRAL_STONE);
        return deathPitSequence;
    }

    public static SurfaceRules.RuleSource makeDesertedNestRules() {
        SurfaceRules.RuleSource desertedNestSequence = SurfaceRules.sequence(makeBedrock(), PACKED_MUD);
        return desertedNestSequence;
    }

    public static SurfaceRules.RuleSource makeStoneOrDeepslateRules() {
        SurfaceRules.ConditionSource deepslateGradientCond = SurfaceRules.verticalGradient("deepslate",
                VerticalAnchor.absolute(0), VerticalAnchor.absolute(8));
        SurfaceRules.RuleSource stoneOrDeepslate = SurfaceRules
                .sequence(SurfaceRules.ifTrue(deepslateGradientCond, DEEPSLATE), STONE);
        return stoneOrDeepslate;
    }

    public static SurfaceRules.RuleSource makeBedrock() {
        SurfaceRules.RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
        SurfaceRules.ConditionSource bedrockGradientCond = SurfaceRules.verticalGradient("bedrock",
                VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5));
        return SurfaceRules.ifTrue(bedrockGradientCond, BEDROCK);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
