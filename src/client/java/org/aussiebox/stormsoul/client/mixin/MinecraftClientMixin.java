package org.aussiebox.stormsoul.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.aussiebox.stormsoul.cca.PlayerComponent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void stormsoul$cancelOpenInventory(Screen screen, CallbackInfo ci) {
        if (player == null) return;
        PlayerComponent component = PlayerComponent.KEY.get(player);
        if (component.getIlluminosSurgeTicks() > 0) ci.cancel();
    }
}
