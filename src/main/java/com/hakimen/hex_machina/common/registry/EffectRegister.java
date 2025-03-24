package com.hakimen.hex_machina.common.registry;

import com.hakimen.hex_machina.HexMachina;
import com.hakimen.hex_machina.common.effect.ExposeMindEffect;
import com.hakimen.hex_machina.common.utils.registration.IRegistry;
import com.hakimen.hex_machina.common.utils.registration.Recorder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.function.Supplier;

public class EffectRegister implements IRegistry {
    public static final EffectRegister EFFECT_REGISTER = new EffectRegister();

    public static final Recorder<MobEffect> MOB_EFFECTS = new Recorder<>(BuiltInRegistries.MOB_EFFECT, HexMachina.MODID);

    public static final Supplier<ExposeMindEffect> EXPOSE_MIND = MOB_EFFECTS.register("expose_mind", ExposeMindEffect::new);

    @Override
    public void register() {

    }
}
