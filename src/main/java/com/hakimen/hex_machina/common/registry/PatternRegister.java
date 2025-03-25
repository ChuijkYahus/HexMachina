package com.hakimen.hex_machina.common.registry;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.common.casting.actions.spells.OpPotionEffect;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import com.hakimen.hex_machina.HexMachina;
import com.hakimen.hex_machina.common.actions.ExposeMindAction;
import com.hakimen.hex_machina.common.actions.capsule.ContainAction;
import com.hakimen.hex_machina.common.actions.capsule.HasEntityAction;
import com.hakimen.hex_machina.common.actions.capsule.ReleaseAction;
import com.hakimen.hex_machina.common.actions.gun.BulletReflectionAction;
import com.hakimen.hex_machina.common.actions.gun.CurrentBulletAction;
import com.hakimen.hex_machina.common.actions.gun.CycleBulletAction;
import com.hakimen.hex_machina.common.actions.gun.CycleBulletWithArgsAction;
import com.hakimen.hex_machina.common.utils.registration.IRegistry;
import com.hakimen.hex_machina.common.utils.registration.Recorder;
import net.minecraft.world.effect.MobEffects;

import java.util.function.Supplier;

public class PatternRegister implements IRegistry {
    public static final PatternRegister PATTERN_REGISTER = new PatternRegister();
    public static final Recorder<ActionRegistryEntry> PATTERNS = new Recorder<>(HexActions.REGISTRY, HexMachina.MODID);

    public static final Supplier<ActionRegistryEntry> CURRENT_BULLET = PATTERNS.register("current_bullet", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("awqqqwaqweaqaaw",HexDir.SOUTH_WEST),
            CurrentBulletAction.INSTANCE
    ));

    public static final Supplier<ActionRegistryEntry> NEXT_BULLET = PATTERNS.register("next_bullet", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("eeewaa",HexDir.EAST),
            new CycleBulletAction(1)
    ));

    public static final Supplier<ActionRegistryEntry> PREVIOUS_BULLET = PATTERNS.register("previous_bullet", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qqqwdd",HexDir.WEST),
            new CycleBulletAction(-1)
    ));

    public static final Supplier<ActionRegistryEntry> ROULETTE = PATTERNS.register("roulette", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qqqeaqaa",HexDir.WEST),
            CycleBulletWithArgsAction.INSTANCE
    ));

    public static final Supplier<ActionRegistryEntry> GET_BULLET = PATTERNS.register("get_bullet", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qaqqqeqaww",HexDir.NORTH_WEST),
            BulletReflectionAction.INSTANCE
    ));

    public static final Supplier<ActionRegistryEntry> CONTAIN = PATTERNS.register("contain", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qqqqqeawa",HexDir.WEST),
            ContainAction.INSTANCE
    ));

    public static final Supplier<ActionRegistryEntry> RELEASE = PATTERNS.register("release", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qqqqqwaddwd",HexDir.WEST),
            ReleaseAction.INSTANCE
    ));

    public static final Supplier<ActionRegistryEntry> HAS_ENTITY = PATTERNS.register("capsule/has_entity", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qqqqqwaddwdww",HexDir.WEST),
            HasEntityAction.INSTANCE
    ));


    public static final Supplier<ActionRegistryEntry> EXPOSE_MIND = PATTERNS.register("expose_mind", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qwqwqwqwqeqqqaedwqqqqqw",HexDir.WEST),
            ExposeMindAction.INSTANCE
    ));


    @Override
    public void register() {

    }
}
