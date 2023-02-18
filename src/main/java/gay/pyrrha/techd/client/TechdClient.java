/*
 * Copyright 2023 Pyrrha Wills
 *
 * All Rights Reserved.
 */

package gay.pyrrha.techd.client;

import gay.pyrrha.techd.Const;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import static gay.pyrrha.techd.Techd.LOGGER;

@ClientOnly
public class TechdClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        var startMs = System.currentTimeMillis();
        LOGGER.info("[{}|Client] Initializing...", Const.MOD_NAME);

        LOGGER.info("[{}|Client] Initialized. (Took {}ms)", Const.MOD_NAME, System.currentTimeMillis() - startMs);
    }
}
