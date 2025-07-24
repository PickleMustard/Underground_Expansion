package dev.picklemustard.underground_expansion.entity;

import java.util.function.Supplier;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import dev.picklemustard.underground_expansion.entity.custom.PitViper;
import dev.picklemustard.underground_expansion.entity.custom.RisingBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> MOD_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, UndergroundExpansion.MODID);

    //Block Entities
    public static final Supplier<EntityType<RisingBlockEntity>> RISING_BLOCK = MOD_ENTITY_TYPES.register("rising_block",() -> EntityType.Builder.of(RisingBlockEntity::new, MobCategory.MISC).sized(.98f,.98f).build("rising_block"));

    //Hostile Mob Entities
    public static final Supplier<EntityType<PitViper>> PIT_VIPER = MOD_ENTITY_TYPES.register("pit_viper",() -> EntityType.Builder.of(PitViper::new, MobCategory.MISC).sized(1.0f,1.0f).build("pit_viper"));

    //Neutral Mob Entities

    //Passive Mob Entities

    public static void register(IEventBus eventBus) {
        MOD_ENTITY_TYPES.register(eventBus);
    }

}

