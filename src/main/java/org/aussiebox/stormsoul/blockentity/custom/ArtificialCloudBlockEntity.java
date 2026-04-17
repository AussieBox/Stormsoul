package org.aussiebox.stormsoul.blockentity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.util.StormsoulUtil;

public class ArtificialCloudBlockEntity extends BlockEntity {
    public float lastLargeCloudOffset;
    public float largeCloudOffset;
    public float largeCloudMovementTime = 0.5F;
    public float largeCloudMovementChange = 0.02F;

    public ArtificialCloudBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARTIFICIAL_CLOUD_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ArtificialCloudBlockEntity entity) {
        entity.lastLargeCloudOffset = entity.largeCloudOffset;

        if (entity.largeCloudMovementTime >= 1) entity.largeCloudMovementChange = (float) -0.02;
        else if (entity.largeCloudMovementTime <= 0) entity.largeCloudMovementChange = 0.02F;

        entity.largeCloudMovementTime += entity.largeCloudMovementChange;
        entity.largeCloudOffset = (float) StormsoulUtil.smoothInterpolate(-0.7, 0.7, entity.largeCloudMovementTime, true);
    }
}
