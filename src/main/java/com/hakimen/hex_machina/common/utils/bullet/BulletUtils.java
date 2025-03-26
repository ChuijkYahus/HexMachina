package com.hakimen.hex_machina.common.utils.bullet;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import at.petrak.hexcasting.common.msgs.MsgNewSpiralPatternsS2C;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.hakimen.hex_machina.common.hex.envs.GunEnv;
import com.hakimen.hex_machina.common.items.bullets.AmethystBulletItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BulletUtils {
    public static InteractionResultHolder<ItemStack> prefixCast(ItemStack gun, ItemStack bullet, Player player, Level level, InteractionHand hand, List<Iota> extensions) {
        AmethystBulletItem itemHex = (AmethystBulletItem) bullet.getItem();

        if (!itemHex.hasData(bullet)) {
            return InteractionResultHolder.fail(gun);
        }

        if (level.isClientSide) {
            return InteractionResultHolder.success(gun);
        }

        List<Iota> instrs = itemHex.getAsListOfIotas(bullet, (ServerLevel) level);

        if (instrs == null) {
            return InteractionResultHolder.fail(gun);
        }


        for (int i = 0; i < extensions.size(); i++) {
            instrs.add(i, extensions.get(i));
        }


        var sPlayer = (ServerPlayer) player;
        var ctx = new GunEnv(sPlayer, hand, bullet);
        var vm = CastingVM.empty(ctx);
        var clientView = vm.queueExecuteAndWrapIotas(instrs, sPlayer.serverLevel());

        var patterns = instrs.stream()
                .filter(i -> i instanceof PatternIota)
                .map(i -> ((PatternIota) i).getPattern())
                .toList();

        var packet = new MsgNewSpiralPatternsS2C(sPlayer.getUUID(), patterns, 140);
        IXplatAbstractions.INSTANCE.sendPacketToPlayer(sPlayer, packet);
        IXplatAbstractions.INSTANCE.sendPacketTracking(sPlayer, packet);

        if (clientView.getResolutionType().getSuccess()) {
            // Somehow we lost spraying particles on each new pattern, so do it here
            // this also nicely prevents particle spam on trinkets
            new ParticleSpray(player.position(), new Vec3(0.0, 1.5, 0.0), 0.4, Math.PI / 3, 30)
                    .sprayParticles(sPlayer.serverLevel(), ctx.getPigment());
        }

        var sound = HexEvalSounds.NORMAL_EXECUTE.sound();
        if (sound != null) {
            var soundPos = sPlayer.position();
            sPlayer.level().playSound(null, soundPos.x, soundPos.y, soundPos.z,
                    sound, SoundSource.PLAYERS, 1f, 1f);
        }

        return null;
    }
}
