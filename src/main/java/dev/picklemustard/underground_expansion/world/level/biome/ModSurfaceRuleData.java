package dev.picklemustard.underground_expansion.world.level.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRuleData {
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource PUMPKIN = makeStateRule(Blocks.PUMPKIN);
    private static final SurfaceRules.RuleSource MELON = makeStateRule(Blocks.MELON);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface = SurfaceRules
                .sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, PUMPKIN), MELON);

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DEATH_PIT), MUD),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
