/*
 * Copyright 2023 Pyrrha Wills
 *
 * All Rights Reserved.
 */

package gay.pyrrha.techd.data.provider;

import gay.pyrrha.techd.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(ModItems.DATA_PAD, Models.GENERATED);
    }
}
