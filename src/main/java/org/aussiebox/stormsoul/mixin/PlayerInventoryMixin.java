package org.aussiebox.stormsoul.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.aussiebox.stormsoul.cca.PlayerComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Shadow @Final public PlayerEntity player;

    @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void stormsoul$disableSlotSwapping(int slot, CallbackInfo ci) {
        PlayerComponent component = PlayerComponent.KEY.get(player);
        if (component.getIlluminosSurgeTicks() > 0) ci.cancel();
    }
}

