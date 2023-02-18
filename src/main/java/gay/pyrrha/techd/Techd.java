/*
 * Copyright 2023 Pyrrha Wills
 *
 * All Rights Reserved.
 */

package gay.pyrrha.techd;

import gay.pyrrha.techd.item.ModItems;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Techd implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(Techd.class);

    @Override
    public void onInitialize(ModContainer mod) {
        var startMs = System.currentTimeMillis();
        LOGGER.info("[{}] Initializing...", Const.MOD_NAME);
        LOGGER.info("[{}] Trans Rights are Human Rights!", Const.MOD_NAME);

        ModItems.init();

        LOGGER.info("[{}] Initialized. (Took {}ms)", Const.MOD_NAME, System.currentTimeMillis() - startMs);
    }

    public static Identifier id(String path) {
        return new Identifier(Const.MOD_ID, path);
    }
}
