package dev.picklemustard.underground_expansion.world.level.biome;

import dev.picklemustard.underground_expansion.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRuleData {
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource SEPHUCHRAL_STONE = makeStateRule(ModBlocks.SEPUCHRAL_STONE_BLOCK.get());
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final SurfaceRules.RuleSource PUMPKIN = makeStateRule(Blocks.PUMPKIN);
    private static final SurfaceRules.RuleSource MELON = makeStateRule(Blocks.MELON);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource deathPitGradient = SurfaceRules.verticalGradient("Mud Gradient", VerticalAnchor.belowTop(15), VerticalAnchor.belowTop(10));
        SurfaceRules.RuleSource grassSurface = SurfaceRules
                .sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, PUMPKIN), MELON);

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DEATH_PIT), SurfaceRules.sequence(
                SurfaceRules.ifTrue(deathPitGradient, SEPHUCHRAL_STONE), COARSE_DIRT
            )),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
