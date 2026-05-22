package org.aussiebox.stormsoul.blockentity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.blockentity.custom.ArtificialCloudBlockEntity;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelBatteryBlockEntity;
import org.aussiebox.stormsoul.blockentity.custom.StormRodBlockEntity;
import org.aussiebox.stormsoul.blockentity.custom.WireConnectorBlockEntity;

public class ModBlockEntities {

    public static final BlockEntityType<ArtificialCloudBlockEntity> ARTIFICIAL_CLOUD_BLOCK_ENTITY =
            register("artificial_cloud", ArtificialCloudBlockEntity::new, ModBlocks.ARTIFICIAL_CLOUD);
    public static final BlockEntityType<StormRodBlockEntity> STORM_ROD_BLOCK_ENTITY =
            register("storm_rod", StormRodBlockEntity::new, ModBlocks.STORM_ROD);
    public static final BlockEntityType<LabrasteelBatteryBlockEntity> LABRASTEEL_BATTERY_BLOCK_ENTITY =
            register("labrasteel_battery", LabrasteelBatteryBlockEntity::new,
                    ModBlocks.COPPER_LABRASTEEL_BATTERY,
                    ModBlocks.IRON_LABRASTEEL_BATTERY
            );
    public static final BlockEntityType<WireConnectorBlockEntity> WIRE_CONNECTOR_BLOCK_ENTITY =
            register("wire", WireConnectorBlockEntity::new, ModBlocks.WIRE_CONNECTOR);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Stormsoul.id(name), FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void init() {

    }
}