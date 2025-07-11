package dev.picklemustard.underground_expansion.item;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UndergroundExpansion.MODID);

    public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerSimpleItem("test_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    public static final DeferredItem<Item> SECOND_TEST_ITEM = ITEMS.registerSimpleItem("second_test_item", new Item.Properties().stacksTo(49));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
