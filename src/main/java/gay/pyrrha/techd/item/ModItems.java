/*
 * Copyright 2023 Pyrrha Wills
 *
 * All Rights Reserved.
 */

package gay.pyrrha.techd.item;

import gay.pyrrha.techd.Techd;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final Item DATA_PAD = register("data_pad", new Item(new Item.Settings()));

    private static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM, Techd.id(path), item);
    }

    public static void init() {}
}
