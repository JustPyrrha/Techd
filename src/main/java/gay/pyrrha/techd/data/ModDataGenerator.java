package gay.pyrrha.techd.data;

import gay.pyrrha.techd.data.provider.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        var pack = gen.createPack();
        pack.addProvider(ModModelProvider::new);
    }
}
