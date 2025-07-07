package dev.picklemustard.underground_expansion.blocks;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import dev.picklemustard.underground_expansion.UndergroundExpansion;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UndergroundExpansion.MODID);

    public static final DeferredBlock<Block> INVERSE_SAND = registerBlock(
        "inverse_sand",
        () -> new InverseSand(
            new ColorRGBA(16780060),
            BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND)
        )
    );

    public static <T extends Block> DeferredBlock<T> registerBlock(String key, Supplier<T> block) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(key, block);
        registerBlockItem(key, registeredBlock);
        return registeredBlock;
    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        DeferredItem<BlockItem> item = UndergroundExpansion.ITEMS.registerSimpleBlockItem(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

