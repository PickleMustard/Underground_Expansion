package dev.picklemustard.underground_expansion.world.level.carvers;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCarvers {
    public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(Registries.CARVER,
            UndergroundExpansion.MODID);

    public static final DeferredHolder<WorldCarver<?>, WorldCarver<LavaTubeConfiguration>> LAVA_TUBE = WORLD_CARVERS
            .register("lava_tube", () -> new LavaTube(LavaTubeConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        WORLD_CARVERS.register(eventBus);
    }

}
