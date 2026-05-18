package org.aussiebox.stormsoul.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.WireConnectorBlockEntity;
import org.jetbrains.annotations.Nullable;

public class WireConnectorBlock extends BlockWithEntity {
    public static final MapCodec<WireConnectorBlock> CODEC = createCodec(WireConnectorBlock::new);
    public static final EnumProperty<Direction> FACING = Properties.FACING;

    public static final VoxelShape UP_SHAPE = Block.createCuboidShape(5, 13, 5, 11, 16, 11);
    public static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 3, 11);
    public static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(5, 5, 0, 11, 11, 3);
    public static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13, 5, 5, 16, 11, 11);
    public static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0, 5, 5, 3, 11, 11);
    public static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(5, 5, 13, 11, 11, 16);

    public WireConnectorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.offset(state.get(FACING)), state.get(FACING));
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide().getOpposite());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case UP -> {
                return UP_SHAPE;
            }
            case DOWN -> {
                return DOWN_SHAPE;
            }
            case NORTH -> {
                return NORTH_SHAPE;
            }
            case EAST -> {
                return EAST_SHAPE;
            }
            case WEST -> {
                return WEST_SHAPE;
            }
            case SOUTH -> {
                return SOUTH_SHAPE;
            }
            case null, default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);

        if (blockEntity instanceof WireConnectorBlockEntity wireEntity) {
            if (wireEntity.getConnectedTo().isPresent()) world.playSound(null, pos, SoundEvent.of(Stormsoul.id("block.wire_connector.violent_disconnect")), SoundCategory.BLOCKS, 0.25F, 1.0F);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
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
