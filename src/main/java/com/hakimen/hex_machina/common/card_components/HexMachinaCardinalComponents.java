package com.hakimen.hex_machina.common.card_components;

import at.petrak.hexcasting.api.addldata.ItemDelegatingEntityIotaHolder;
import com.hakimen.hex_machina.common.entity.golem.HexGolem;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;

import static at.petrak.hexcasting.fabric.cc.HexCardinalComponents.IOTA_HOLDER;

public class HexMachinaCardinalComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerFor(HexGolem.class, IOTA_HOLDER, HexGolemEntityIotaHolder::new);
    }
}
