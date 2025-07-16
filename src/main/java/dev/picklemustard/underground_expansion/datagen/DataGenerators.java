package dev.picklemustard.underground_expansion.datagen;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.logging.LogUtils;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = UndergroundExpansion.MODID)
public class DataGenerators{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeServer(),
            new LootTableProvider(packOutput,
                Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new,
                    LootContextParamSets.BLOCK)),
                lookupProvider));

        gen.addProvider(event.includeServer(), new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModDataMapProvider(packOutput, lookupProvider));

        gen.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        gen.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));

        gen.addProvider(event.includeServer(), new ModDatapackProvider(packOutput, lookupProvider));


    }
}
