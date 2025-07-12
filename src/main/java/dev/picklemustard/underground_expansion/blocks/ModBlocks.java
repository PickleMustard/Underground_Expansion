package dev.picklemustard.underground_expansion.blocks;


import java.util.function.Supplier;

import com.mojang.logging.LogUtils;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UndergroundExpansion.MODID);

    public static final DeferredBlock<Block> INVERSE_SAND_BLOCK = registerBlock(
        "inverse_sand",
        () -> new InverseSandBlock(
            new ColorRGBA(14406560),
            BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.SAND)
        )
    );

    public static final DeferredBlock<Block> SEPUCHRAL_STONE_BLOCK = registerBlock("sepuchral_stone", () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static <T extends Block> DeferredBlock<T> registerBlock(String key, Supplier<T> block) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(key, block);
        registerBlockItem(key, registeredBlock);
        return registeredBlock;
    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        DeferredItem<BlockItem> item = ModItems.ITEMS.registerSimpleBlockItem(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        LogUtils.getLogger().info("Namespace: {}", BLOCKS.getNamespace());
    }
}

