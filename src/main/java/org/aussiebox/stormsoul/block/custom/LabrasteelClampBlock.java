package org.aussiebox.stormsoul.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelClampBlockEntity;
import org.aussiebox.stormsoul.util.StormsoulUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class LabrasteelClampBlock extends BlockWithEntity {
    public static final MapCodec<LabrasteelClampBlock> CODEC = createCodec(LabrasteelClampBlock::new);
    private static final Map<Direction, VoxelShape> SHAPES = StormsoulUtil.createUpwardFacingShapeMap(Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final EnumProperty<Direction> FACING = Properties.FACING;
    public static final BooleanProperty LOCKED = BooleanProperty.of("clamp_locked");

    public LabrasteelClampBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(FACING));
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof LabrasteelClampBlockEntity entity)) return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        if (stack.isEmpty()) {
            if (!entity.getStack().isEmpty()) {
                player.getInventory().offerOrDrop(entity.getStack());
                entity.setStack(ItemStack.EMPTY);
                return ActionResult.SUCCESS;
            }
        } else {
            player.getInventory().removeStack(player.getInventory().getSelectedSlot());
            if (entity.getStack().isEmpty()) entity.setStack(stack);
            else {
                player.getInventory().offerOrDrop(entity.getStack());
                entity.setStack(stack);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        if (context.getPlayer() != null)
            if (context.getPlayer().isSneaking()) return this.getDefaultState().with(FACING, context.getSide().getOpposite());
        return this.getDefaultState().with(FACING, context.getSide());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LabrasteelClampBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LOCKED);
    }
}
