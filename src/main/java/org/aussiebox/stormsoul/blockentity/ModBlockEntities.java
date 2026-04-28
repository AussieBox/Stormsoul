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
import org.aussiebox.stormsoul.blockentity.custom.LabradoriteBatteryBlockEntity;
import org.aussiebox.stormsoul.blockentity.custom.StormRodBlockEntity;

public class ModBlockEntities {

    public static final BlockEntityType<ArtificialCloudBlockEntity> ARTIFICIAL_CLOUD_BLOCK_ENTITY =
            register("artificial_cloud", ArtificialCloudBlockEntity::new, ModBlocks.ARTIFICIAL_CLOUD);
    public static final BlockEntityType<StormRodBlockEntity> STORM_ROD_BLOCK_ENTITY =
            register("storm_rod", StormRodBlockEntity::new, ModBlocks.STORM_ROD);
    public static final BlockEntityType<LabradoriteBatteryBlockEntity> LABRADORITE_BATTERY_BLOCK_ENTITY =
            register("labradorite_battery", LabradoriteBatteryBlockEntity::new, ModBlocks.COPPER_LABRADORITE_BATTERY);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Stormsoul.id(name), FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void init() {

    }
}