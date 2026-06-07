package org.aussiebox.stormsoul;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface StormsoulConstants {
    RegistryKey<DamageType> CHARGER_INTERFERENCE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Stormsoul.id("charger_interference"));
}
