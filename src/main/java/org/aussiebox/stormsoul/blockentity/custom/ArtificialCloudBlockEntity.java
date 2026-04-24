package org.aussiebox.stormsoul.blockentity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.util.StormsoulUtil;

public class ArtificialCloudBlockEntity extends BlockEntity {
    public static float lastLargeCloudOffset;
    public static float largeCloudOffset;
    public static float largeCloudMovementTime = 0.5F;
    public static float largeCloudMovementChange = 0.02F;

    public ArtificialCloudBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARTIFICIAL_CLOUD_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ArtificialCloudBlockEntity entity) {

    }

    public static void serverTick(MinecraftServer server) {
        lastLargeCloudOffset = largeCloudOffset;

        if (largeCloudMovementTime >= 1) largeCloudMovementChange = (float) -0.02;
        else if (largeCloudMovementTime <= 0) largeCloudMovementChange = 0.02F;

        largeCloudMovementTime += largeCloudMovementChange;
        largeCloudOffset = (float) StormsoulUtil.smoothInterpolate(-0.7, 0.7, largeCloudMovementTime, true);
    }
}
