package dev.picklemustard.underground_expansion.world.structure;

import dev.picklemustard.underground_expansion.datagen.ModBiomeTagProvider;
import dev.picklemustard.underground_expansion.world.level.biome.ModBiomes;
import dev.picklemustard.underground_expansion.world.structure.structures.DeathPitVase;
import dev.picklemustard.underground_expansion.world.structure.structures.DesertedQumaNest;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public class ModStructureProvider {
    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomeHolder = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePoolHolder = context.lookup(Registries.TEMPLATE_POOL);

        context.register(ModStructures.DEATH_PIT_VASE_KEY,
                new DeathPitVase(
                        new Structure.StructureSettings.Builder(
                                biomeHolder.getOrThrow(ModBiomeTagProvider.IS_DEATH_PIT))
                                .generationStep(GenerationStep.Decoration.RAW_GENERATION).build(),
                        templatePoolHolder.getOrThrow(DeathPitStructurePools.START), 12,
                        ConstantHeight.of(VerticalAnchor.absolute(0)), 128));

        context.register(ModStructures.DESERTED_QUMA_NEST_KEY, new DesertedQumaNest(
                new Structure.StructureSettings.Builder(biomeHolder.getOrThrow(ModBiomeTagProvider.IS_DESERTED_NEST))
                        .generationStep(GenerationStep.Decoration.RAW_GENERATION).build(),
                templatePoolHolder.getOrThrow(QumaNestStructurePools.START), 12,
                ConstantHeight.of(VerticalAnchor.absolute(0)), 128));
    }

}
