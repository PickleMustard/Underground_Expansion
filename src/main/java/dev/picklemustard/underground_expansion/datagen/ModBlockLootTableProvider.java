package dev.picklemustard.underground_expansion.datagen;

import java.util.Set;

import com.mojang.logging.LogUtils;

import dev.picklemustard.underground_expansion.blocks.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class ModBlockLootTableProvider extends BlockLootSubProvider{

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        LogUtils.getLogger().info("Get registries {}", registries.listRegistries());
        var test = registries.listRegistries();
        var test_iter = test.iterator();
        while(test_iter.hasNext()){
            LogUtils.getLogger().info("Next Item: {}", test_iter.next());
        }

        dropSelf(ModBlocks.INVERSE_SAND_BLOCK.get());
        dropSelf(ModBlocks.SEPUCHRAL_STONE_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

}
