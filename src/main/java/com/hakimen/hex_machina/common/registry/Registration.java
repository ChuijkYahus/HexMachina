package com.hakimen.hex_machina.common.registry;


public class Registration {
    public static void register(){
        ItemRegister.ITEM_REGISTER.register();
        ContainerRegister.CONTAINER_REGISTER.register();
        PatternRegister.PATTERN_REGISTER.register();
        EffectRegister.EFFECT_REGISTER.register();
        EntityRegister.ENTITY_REGISTER.register();
    }
}
