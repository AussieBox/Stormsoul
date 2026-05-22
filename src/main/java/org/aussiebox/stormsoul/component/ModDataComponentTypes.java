package org.aussiebox.stormsoul.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.util.WireType;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<Double> STORED_STORMSOUL =
            register("stored_stormsoul", builder -> builder.codec(Codec.DOUBLE));

    public static final ComponentType<BlockPos> EDITING_WIRE_AT =
            register("editing_wire_at", builder -> builder.codec(BlockPos.CODEC));

    public static final ComponentType<WireType> WIRE_TYPE =
            register("wire_type", builder -> builder.codec(StringIdentifiable.createCodec(WireType::values)));

    public static final ComponentType<Integer> HITS_SINCE_SURGE =
            register("hits_since_surge", builder -> builder.codec(Codec.INT));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Stormsoul.id(name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void init() {}
}