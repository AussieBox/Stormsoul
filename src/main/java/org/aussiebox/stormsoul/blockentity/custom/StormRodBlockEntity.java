package org.aussiebox.stormsoul.blockentity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.block.custom.StormRodBlock;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;

public class StormRodBlockEntity extends AbstractStormsoulBlockEntity {
    public StormRodBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORM_ROD_BLOCK_ENTITY, pos, state);
        setMaxStoredStormsoul(100);
        setTransferPerTick(10);
        setOutputDirections(state.get(StormRodBlock.FACING).getOpposite());
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof StormRodBlockEntity stormEntity)) return;
        AbstractStormsoulBlockEntity.tick(world, pos, state, entity);
        stormEntity.addStored(10);
    }
}
