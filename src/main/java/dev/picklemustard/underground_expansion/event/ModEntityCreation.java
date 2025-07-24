package dev.picklemustard.underground_expansion.event;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.entity.ModEntities;
import dev.picklemustard.underground_expansion.entity.custom.PitViper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = UndergroundExpansion.MODID)
public class ModEntityCreation {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PIT_VIPER.get(), PitViper.setAttributes().build());
    }


}
