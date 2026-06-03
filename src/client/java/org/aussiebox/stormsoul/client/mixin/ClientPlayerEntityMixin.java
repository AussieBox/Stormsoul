package org.aussiebox.stormsoul.client.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.stormsoul.cca.PlayerComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow
    public abstract void tick();

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement", at = @At(value = "HEAD"), cancellable = true)
    private void stormsoul$tickMovement(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity)(Object) this;
        if (player == null) return;

        PlayerComponent component = PlayerComponent.KEY.get(player);
        if (component.getIlluminosSurgeTicks() == 0) return;

        Vec3d lookDir = player.getRotationVector();
        Vec3d motion = new Vec3d(lookDir.x, 0, lookDir.z).normalize().multiply(0.8);
        player.setVelocity(motion.x, player.getVelocity().y, motion.z);
        super.tickMovement();
        ci.cancel();
    }
}
