package org.aussiebox.stormsoul.client.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getName()Lnet/minecraft/text/Text;"))
    private Text stormsoul$fixRepeatedItemTooltip(ItemStack stack) {
        if ((stack.getCustomName() != null && stack.getName() != stack.getCustomName()) || stack.getName() != stack.getItemName()) {
            return Text.literal("Replaced by Stormsoul :3c");
        }
        return stack.getName();
    }
}
