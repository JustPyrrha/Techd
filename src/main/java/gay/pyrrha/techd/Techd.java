/*
 * Copyright 2023 Pyrrha Wills
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
