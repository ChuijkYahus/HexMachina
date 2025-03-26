package com.hakimen.hex_machina.common.registry;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import com.hakimen.hex_machina.HexMachina;
import com.hakimen.hex_machina.common.actions.ExposeMindAction;
import com.hakimen.hex_machina.common.actions.capsule.ContainAction;
import com.hakimen.hex_machina.common.actions.capsule.HasEntityAction;
import com.hakimen.hex_machina.common.actions.capsule.ReleaseAction;
import com.hakimen.hex_machina.common.actions.golem.GolemMoveAction;
import com.hakimen.hex_machina.common.actions.golem.GolemIsInRangeOfTarget;
import com.hakimen.hex_machina.common.actions.gun.BulletReflectionAction;
import com.hakimen.hex_machina.common.actions.gun.CurrentBulletAction;
import com.hakimen.hex_machina.common.actions.gun.CycleBulletAction;
import com.hakimen.hex_machina.common.actions.gun.CycleBulletWithArgsAction;
import com.hakimen.hex_machina.common.utils.registration.IRegistry;
import com.hakimen.hex_machina.common.utils.registration.Recorder;

import java.util.function.Supplier;

public class PatternRegister implements IRegistry {
    public static final PatternRegister PATTERN_REGISTER = new PatternRegister();
    public static final Recorder<ActionRegistryEntry> PATTERNS = new Recorder<>(HexActions.REGISTRY, HexMachina.MODID);

    //Gun

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

    // Entity Capsule

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


    // Great Spells
    public static final Supplier<ActionRegistryEntry> EXPOSE_MIND = PATTERNS.register("expose_mind", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qwqwqwqwqeqqqaedwqqqqqw",HexDir.WEST),
            ExposeMindAction.INSTANCE
    ));


    // Golem
    public static final Supplier<ActionRegistryEntry> MOVE_GOLEM = PATTERNS.register("move_golem", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("qweqweqweqweqweqweqweqweqweqwe",HexDir.WEST),
            GolemMoveAction.INSTANCE
    ));
    public static final Supplier<ActionRegistryEntry> IS_GOLEM_IN_RANGE_OF_TARGET = PATTERNS.register("wait_for_move_and_run", () -> new ActionRegistryEntry(
            HexPattern.fromAngles("ewqewqewqewqewq",HexDir.WEST),
            GolemIsInRangeOfTarget.INSTANCE
    ));

    @Override
    public void register() {

    }
}
