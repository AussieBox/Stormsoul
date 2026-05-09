package org.aussiebox.stormsoul.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.WireConnectorBlockEntity;
import org.jetbrains.annotations.Nullable;

public class WireConnectorBlock extends BlockWithEntity {
    public static final MapCodec<WireConnectorBlock> CODEC = createCodec(WireConnectorBlock::new);

    public WireConnectorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);

        if (blockEntity instanceof WireConnectorBlockEntity wireEntity) {
            if (wireEntity.getConnectedTo().isPresent()) world.playSound(null, pos, SoundEvent.of(Stormsoul.id("block.wire_connector.violent_disconnect")), SoundCategory.BLOCKS, 0.25F, 1.0F);
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WireConnectorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.WIRE_CONNECTOR_BLOCK_ENTITY, WireConnectorBlockEntity::tick);
    }
}
