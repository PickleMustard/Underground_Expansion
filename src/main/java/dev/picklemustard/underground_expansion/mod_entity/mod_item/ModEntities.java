package dev.picklemustard.underground_expansion.mod_entity.mod_item;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, UndergroundExpansion.MODID);

    ENTITY_TYPES.register(, () -> new RisingBlockEntity());

    //public static <T extends EntityType> DeferredRegister<T> registerEntityType(String key, )
    //EntityType.
    /*
    public static final DeferredBlock<Block> INVERSE_SAND = registerBlock(
        "inverse_sand",
        () -> new InverseSand(
            new ColorRGBA(16780060),
            BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND)
        )<t_€ý>hlua require"cmp.utils.feedkeys".run(34)
        Ã½
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
    */
}

