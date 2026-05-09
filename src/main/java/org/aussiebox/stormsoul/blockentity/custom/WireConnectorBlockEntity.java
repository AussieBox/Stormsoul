package org.aussiebox.stormsoul.blockentity.custom;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;

import java.util.Optional;
import java.util.UUID;

public class WireConnectorBlockEntity extends AbstractStormsoulBlockEntity {
    @Getter private Optional<Vec3d> connectedTo = Optional.empty();
    @Getter private Optional<PlayerEntity> editor = Optional.empty();

    public WireConnectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIRE_CONNECTOR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof WireConnectorBlockEntity connectorEntity)) return;
        AbstractStormsoulBlockEntity.tick(world, pos, state, entity);

        Optional<Vec3d> connectedTo = connectorEntity.getConnectedTo();
        if (connectedTo.isPresent()) {
            if (!(world.getBlockEntity(BlockPos.ofFloored(connectedTo.get())) instanceof WireConnectorBlockEntity)) {
                connectorEntity.setConnectedTo(Optional.empty());
                world.playSound(null, BlockPos.ofFloored(connectedTo.get()), SoundEvent.of(Stormsoul.id("block.wire_connector.violent_disconnect")), SoundCategory.BLOCKS, 0.25F, 1.0F);
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
    }

    @Override
    protected void writeData(WriteView tag) {
        connectedTo.ifPresent(vec3d -> tag.put("connectedTo", Vec3d.CODEC, vec3d));
        editor.ifPresent(playerEntity -> tag.put("editor", Uuids.CODEC, editor.get().getUuid()));
    }
}
