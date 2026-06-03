package org.aussiebox.stormsoul.client.mixin.accessor;

import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityTrackingSoundInstance.class)
public interface EntityTrackingSoundInstanceAccessor {
    @Accessor("entity")
    Entity getTrackedEntity();
}
