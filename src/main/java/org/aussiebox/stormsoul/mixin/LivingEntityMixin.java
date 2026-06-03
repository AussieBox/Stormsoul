package org.aussiebox.stormsoul.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.cca.PlayerComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getStepHeight", at = @At("RETURN"), cancellable = true)
    private void stormsoul$getStepHeight(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity)(Object) this;
        if (!(entity instanceof PlayerEntity player)) return;
        PlayerComponent component = PlayerComponent.KEY.get(player);
        if (component.getIlluminosSurgeTicks() > 0 && cir.getReturnValue() <= 1.0F) cir.setReturnValue(1.0F);
    }
}
