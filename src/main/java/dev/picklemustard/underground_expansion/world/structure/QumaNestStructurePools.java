package dev.picklemustard.underground_expansion.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class QumaNestStructurePools {
    public static final ResourceKey<StructureTemplatePool> START = Pools.createKey("deserted_quma_nest/dungeon/entry");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<PlacedFeature> placedFeatureHolder = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<StructureProcessorList> processorListHolder = context.lookup(Registries.PROCESSOR_LIST);
        HolderGetter<StructureTemplatePool> templatePoolHolder = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> emptyFallback = templatePoolHolder.getOrThrow(Pools.EMPTY);

        Pools.register(context, "deserted_quma_nest/dungeon", new StructureTemplatePool(emptyFallback,
                ImmutableList.of(Pair.of(StructurePoolElement.empty(), 1)), StructureTemplatePool.Projection.RIGID));

    }

}
