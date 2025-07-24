package dev.picklemustard.underground_expansion.world.level.carvers;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class ModCarverRegistration {
    public static final ResourceKey<ConfiguredWorldCarver<?>> LAVA_TUBE = createKey("lava_tube");

    private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER,
                ResourceLocation.fromNamespaceAndPath(UndergroundExpansion.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
        HolderGetter<Block> holdergetter = context.lookup(Registries.BLOCK);
        context.register(LAVA_TUBE,
                ModCarvers.LAVA_TUBE.get().configured(new LavaTubeConfiguration(.15f,
                        UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)),
                        UniformFloat.of(.1f, .9f), VerticalAnchor.aboveBottom(8),
                        CarverDebugSettings.of(true, Blocks.BIRCH_BUTTON.defaultBlockState()),
                        holdergetter.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES), UniformFloat.of(.7f, 1.4f),
                        UniformFloat.of(.8f, 1.4f))));
    }

}
