package dev.picklemustard.underground_expansion.item;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.entity.ModEntities;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UndergroundExpansion.MODID);

    public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerSimpleItem("test_item",
            new Item.Properties().food(new FoodProperties.Builder()
                    .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    public static final DeferredItem<Item> SECOND_TEST_ITEM = ITEMS.registerSimpleItem("second_test_item",
            new Item.Properties().stacksTo(49));

    //Deserted Nest Items
    //public static final DeferredItem<place_holder> DESICCATED_LARVA
    //public static final DeferredItem<place_holder> OLD_NECTAR
    //public static final DeferredItem<place_holder> ROYAL_NECTAR
    //public static final DeferredItem<place_holder> WINGED_NECTAR
    //public static final DeferredItem<place_holder> REINFORCED_NECTAR
    //public static final DeferredItem<place_holder> SHARPENED_NECTAR

    // Mob Spawn Eggs
    public static final DeferredItem<SpawnEggItem> PIT_VIPER_SPAWN_EGG = ITEMS.registerItem("pit_viper_spawn_egg",
            properties -> new DeferredSpawnEggItem(ModEntities.PIT_VIPER, 25, 30, properties));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
