package org.aussiebox.stormsoul.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.aussiebox.stormsoul.Stormsoul;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<Double> STORED_STORMSOUL =
            register("stored_stormsoul", builder -> builder.codec(Codec.DOUBLE));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Stormsoul.id(name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void init() {}
}