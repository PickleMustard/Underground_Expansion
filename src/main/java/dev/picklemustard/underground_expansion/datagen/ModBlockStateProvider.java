package dev.picklemustard.underground_expansion.datagen;


import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void registerStatesAndModels() {
    }

}
