package org.aussiebox.stormsoul.client.mixin.accessor;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SoundSystem.class)
public interface SoundSystemAccessor {
    @Accessor("sources")
    Map<SoundInstance, ?> getSources();
}