package dev.picklemustard.underground_expansion.world.level.biome;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public class ModRegionsData {

    public static void register() {
        Regions.register(new ModRegionsProvider(ResourceLocation.fromNamespaceAndPath(UndergroundExpansion.MODID, "basic_underground"), 2));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, UndergroundExpansion.MODID, ModSurfaceRuleData.makeRules());
    }
}
