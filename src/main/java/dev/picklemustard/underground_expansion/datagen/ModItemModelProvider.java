package dev.picklemustard.underground_expansion.datagen;

import dev.picklemustard.underground_expansion.UndergroundExpansion;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider{

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UndergroundExpansion.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(Item)
    }

}
