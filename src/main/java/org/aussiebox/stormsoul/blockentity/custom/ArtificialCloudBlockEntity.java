package org.aussiebox.stormsoul.blockentity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.block.custom.ArtificialCloudBlock;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.cca.ArtificialStormComponent;
import org.aussiebox.stormsoul.util.StormsoulUtil;

import java.util.Random;

public class ArtificialCloudBlockEntity extends BlockEntity {
    public static Random random = new Random();

    public static float lastLargeCloudOffset;
    public static float largeCloudOffset;
    public static float largeCloudMovementTime = 0.5F;
    public static float largeCloudMovementChange = 0.02F;
    public int strikeTime = 0;

    public ArtificialCloudBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARTIFICIAL_CLOUD_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ArtificialCloudBlockEntity entity) {
        ArtificialStormComponent storm = ArtificialStormComponent.KEY.get(world);
        if (storm.storming && entity.strikeTime == 0 && random.nextInt(0, 500) == 0) {
            entity.strikeTime = random.nextInt(20, 120);
            world.setBlockState(pos, state.with(ArtificialCloudBlock.STRIKING, true));
            entity.markDirty();
        }
        if (entity.strikeTime > 0) {
            entity.strikeTime--;
            entity.markDirty();
        } else world.setBlockState(pos, state.with(ArtificialCloudBlock.STRIKING, false));
    }

    public static void serverTick(MinecraftServer server) {
        lastLargeCloudOffset = largeCloudOffset;

        if (largeCloudMovementTime >= 1) largeCloudMovementChange = (float) -0.02;
        else if (largeCloudMovementTime <= 0) largeCloudMovementChange = 0.02F;

        largeCloudMovementTime += largeCloudMovementChange;
        largeCloudOffset = (float) StormsoulUtil.smoothInterpolate(-0.7, 0.7, largeCloudMovementTime, true);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    protected void readData(ReadView tag) {
        strikeTime = tag.getInt("strikeTime", 0);
    }

    @Override
    protected void writeData(WriteView tag) {
        tag.putInt("strikeTime", strikeTime);
    }
}
