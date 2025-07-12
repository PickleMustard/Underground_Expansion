package dev.picklemustard.underground_expansion.datagen;

import com.mojang.logging.LogUtils;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.blocks.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, UndergroundExpansion.MODID, exFileHelper);
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    @Override
    protected void registerStatesAndModels() {
        System.out.println("Testing?");
        LogUtils.getLogger().debug("Is this working?");
        simpleBlockWithItem(ModBlocks.INVERSE_SAND_BLOCK.get(),
         cubeAll(ModBlocks.INVERSE_SAND_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SEPUCHRAL_STONE_BLOCK.get(), cubeAll(ModBlocks.SEPUCHRAL_STONE_BLOCK.get()));


        //VariantBlockStateBuilder vb = getVariantBuilder(ModBlocks.INVERSE_SAND_BLOCK.get());

        // LogUtils.getLogger().info(blockTexture(ModBlocks.INVERSE_SAND_BLOCK.get()).toString());
        LogUtils.getLogger().debug("Registry: {}", ModBlocks.BLOCKS.getRegistry());
        LogUtils.getLogger().debug("Entries: {}", ModBlocks.BLOCKS.getEntries());
        // LogUtils.getLogger().info(ResourceLocation.fromNamespaceAndPath(name.getNamespace(),
        // ModelProvider.BLOCK_FOLDER + "/" + name.getPath()));
        // models().cubeAll(name(ModBlocks.INVERSE_SAND_BLOCK.get()),
        // blockTexture(ModBlocks.INVERSE_SAND_BLOCK.get()));

    }

}
