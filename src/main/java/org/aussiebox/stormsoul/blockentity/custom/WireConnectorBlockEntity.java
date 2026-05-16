package org.aussiebox.stormsoul.blockentity.custom;

import com.mojang.serialization.Codec;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.util.WireType;

import java.util.*;

public class WireConnectorBlockEntity extends AbstractStormsoulBlockEntity {
    @Getter private Optional<Vec3d> connectedTo = Optional.empty();
    @Getter private Optional<PlayerEntity> editor = Optional.empty();
    @Getter private Optional<WireType> wireType = Optional.empty();
    @Getter private List<Float> powerProgresses = new ArrayList<>();
    @Getter private List<Float> lastPowerProgresses = new ArrayList<>();
    @Getter private int ticksPerTransferStart = 100;
    @Getter private float transferDurationPerSegment = 1f;
    @Getter private int currentTicks = 0;
    @Getter private double stormsoulInTransfer = 0;

    public WireConnectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIRE_CONNECTOR_BLOCK_ENTITY, pos, state);
        setMaxStoredStormsoul(100);
        setTransferPerTick(1);
        setTransferMethod(TransferMethod.NONE);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof WireConnectorBlockEntity connectorEntity)) return;
        AbstractStormsoulBlockEntity.tick(world, pos, state, entity);
        connectorEntity.addStored(10);

        connectorEntity.currentTicks++;
        if (connectorEntity.currentTicks >= connectorEntity.ticksPerTransferStart) connectorEntity.currentTicks = 0;

        Optional<Vec3d> connectedTo = connectorEntity.getConnectedTo();
        if (connectedTo.isPresent()) {
            if (!(world.getBlockEntity(BlockPos.ofFloored(connectedTo.get())) instanceof WireConnectorBlockEntity connectedToEntity)) {
                connectorEntity.setConnectedTo(Optional.empty());
                world.playSound(null, BlockPos.ofFloored(connectedTo.get()), SoundEvent.of(Stormsoul.id("block.wire_connector.violent_disconnect")), SoundCategory.BLOCKS, 0.25F, 1.0F);
            } else {
                double distance = Vec3d.of(connectorEntity.getPos()).distanceTo(connectedTo.get());
                int segments = (int) (distance*8);

                List<Float> powerProgresses = new ArrayList<>(connectorEntity.powerProgresses);

                ListIterator<Float> iterator = powerProgresses.listIterator();
                while (iterator.hasNext()) {
                    float progress = iterator.next();
                    progress += connectorEntity.getTransferDurationPerSegment() / segments;
                    if (progress < 1f) iterator.set(progress);
                    else {
                        iterator.remove();
                        connectorEntity.stormsoulInTransfer -= connectedToEntity.addStored(Math.min(connectorEntity.stormsoulInTransfer, connectorEntity.getTransferPerTick()));
                    }
                }

                if (connectorEntity.getStoredStormsoul() > 0 && connectorEntity.currentTicks == 0) {
                    if (connectorEntity.wireType.isPresent()) {
                        connectorEntity.ticksPerTransferStart = connectorEntity.wireType.get().getTicksPerTransferStart();
                        connectorEntity.transferDurationPerSegment = connectorEntity.wireType.get().getTransferDurationPerSegment();
                        connectorEntity.setTransferPerTick(connectorEntity.wireType.get().getAmountPerTransfer());
                    }

                    powerProgresses.add(0f);
                    connectorEntity.stormsoulInTransfer += Math.min(connectorEntity.getTransferPerTick(), connectorEntity.getStoredStormsoul());
                }

                connectorEntity.lastPowerProgresses = connectorEntity.powerProgresses;
                connectorEntity.powerProgresses = powerProgresses;
                connectorEntity.markDirty();
            }
        }
    }

    public void setConnectedTo(Optional<Vec3d> pos) {
        connectedTo = pos;
        markDirty();
    }

    public void setEditor(Optional<PlayerEntity> player) {
        editor = player;
        markDirty();
    }

    public void setWireType(Optional<WireType> type) {
        wireType = type;
        if (wireType.isPresent()) {
            ticksPerTransferStart = wireType.get().getTicksPerTransferStart();
            transferDurationPerSegment = wireType.get().getTransferDurationPerSegment();
            setTransferPerTick(wireType.get().getAmountPerTransfer());
        } else {
            ticksPerTransferStart = Integer.MAX_VALUE;
            transferDurationPerSegment = Float.MAX_VALUE;
            setTransferPerTick(Float.MIN_VALUE);
        }
        markDirty();
    }

    @Override
    protected void readData(ReadView tag) {
        connectedTo = tag.read("connectedTo", Vec3d.CODEC);
        if (world != null && tag.contains("editor")) {
            Optional<UUID> optionalUUID = tag.read("editor", Uuids.CODEC);
            if (optionalUUID.isPresent()) {
                PlayerEntity player = world.getPlayerByUuid(optionalUUID.get());
                editor = player != null ? Optional.of(player) : Optional.empty();
            } else editor = Optional.empty();
        }
        wireType = tag.read("wireType", StringIdentifiable.createCodec(WireType::values));
        powerProgresses = new ArrayList<>(tag.read("powerProgresses", Codec.list(Codec.FLOAT)).orElse(new ArrayList<>()));
        lastPowerProgresses = new ArrayList<>(tag.read("lastPowerProgresses", Codec.list(Codec.FLOAT)).orElse(new ArrayList<>()));
        ticksPerTransferStart = tag.getInt("ticksPerTransferStart", 1);
        transferDurationPerSegment = tag.getFloat("transferDurationPerSegment", 1f);
        currentTicks = tag.getInt("currentTicks", 0);
        stormsoulInTransfer = tag.getDouble("stormsoulInTransfer", 0);
    }

    @Override
    protected void writeData(WriteView tag) {
        connectedTo.ifPresent(vec3d -> tag.put("connectedTo", Vec3d.CODEC, vec3d));
        editor.ifPresent(playerEntity -> tag.put("editor", Uuids.CODEC, editor.get().getUuid()));
        wireType.ifPresent(type -> tag.put("wireType", StringIdentifiable.createCodec(WireType::values), type));
        tag.put("powerProgresses", Codec.list(Codec.FLOAT), powerProgresses);
        tag.put("lastPowerProgresses", Codec.list(Codec.FLOAT), lastPowerProgresses);
        tag.putInt("ticksPerTransferStart", ticksPerTransferStart);
        tag.putFloat("transferDurationPerSegment", transferDurationPerSegment);
        tag.putInt("currentTicks", currentTicks);
        tag.putDouble("stormsoulInTransfer", stormsoulInTransfer);
    }
}
