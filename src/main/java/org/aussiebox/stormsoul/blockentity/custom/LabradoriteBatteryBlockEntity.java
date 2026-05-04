package org.aussiebox.stormsoul.blockentity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.block.custom.LabradoriteBatteryBlock;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;

public class LabradoriteBatteryBlockEntity extends AbstractStormsoulBlockEntity {
    public LabradoriteBatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LABRADORITE_BATTERY_BLOCK_ENTITY, pos, state);
    }

    public LabradoriteBatteryBlockEntity(double maxStored, BlockPos pos, BlockState state) {
        super(ModBlockEntities.LABRADORITE_BATTERY_BLOCK_ENTITY, pos, state);
        setMaxStoredStormsoul(maxStored);
        setInputDirections(Direction.UP);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof LabradoriteBatteryBlockEntity batteryEntity)) return;
        AbstractStormsoulBlockEntity.tick(world, pos, state, entity);

        float progress = (float) (batteryEntity.getStoredStormsoul() / batteryEntity.getMaxStoredStormsoul());
        world.setBlockState(pos, state.with(LabradoriteBatteryBlock.STORED_DISPLAY, (int) (progress * 8)));
    }
}
