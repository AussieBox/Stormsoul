package org.aussiebox.stormsoul.mixin;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.aussiebox.stormsoul.cca.PlayerComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onPlayerAction", at = @At("HEAD"), cancellable = true)
    private void stormsoul$stopPlayerAction(PlayerActionC2SPacket packet, CallbackInfo ci) {
        PlayerComponent component = PlayerComponent.KEY.get(player);
        if (component.getIlluminosSurgeTicks() > 0) {
            if (packet.getAction() == PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND) ci.cancel();
            if (packet.getAction() == PlayerActionC2SPacket.Action.DROP_ITEM) ci.cancel();
            if (packet.getAction() == PlayerActionC2SPacket.Action.DROP_ALL_ITEMS) ci.cancel();
            if (packet.getAction() == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) ci.cancel();
            if (packet.getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM) ci.cancel();
        }
    }
}
