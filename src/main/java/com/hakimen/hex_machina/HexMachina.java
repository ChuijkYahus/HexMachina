package com.hakimen.hex_machina;

import com.hakimen.hex_machina.common.registry.Registration;
import net.fabricmc.api.ModInitializer;

public class HexMachina implements ModInitializer {

    public static final String MODID = "hex_machina";

    @Override
    public void onInitialize() {
        Registration.register();
    }
}
