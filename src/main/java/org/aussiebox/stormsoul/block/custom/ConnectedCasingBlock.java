package org.aussiebox.stormsoul.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.ConnectedCasingBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectedCasingBlock extends BlockWithEntity {
    public static final MapCodec<ConnectedCasingBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Identifier.CODEC.fieldOf("texture").forGetter(block -> block.texture),
                            createSettingsCodec()
                    )
                    .apply(instance, ConnectedCasingBlock::new)
    );
    private final Identifier texture;

    public static final Property<Boolean> NORTH = BooleanProperty.of("north");
    public static final Property<Boolean> EAST = BooleanProperty.of("east");
    public static final Property<Boolean> SOUTH = BooleanProperty.of("south");
    public static final Property<Boolean> WEST = BooleanProperty.of("west");
    public static final Property<Boolean> UP = BooleanProperty.of("up");
    public static final Property<Boolean> DOWN = BooleanProperty.of("down");
    public static final Property<Boolean> NE_UPPER = BooleanProperty.of("northeast_upper");
    public static final Property<Boolean> NE_LOWER = BooleanProperty.of("northeast_lower");
    public static final Property<Boolean> NW_UPPER = BooleanProperty.of("northwest_upper");
    public static final Property<Boolean> NW_LOWER = BooleanProperty.of("northwest_lower");
    public static final Property<Boolean> SE_UPPER = BooleanProperty.of("southeast_upper");
    public static final Property<Boolean> SE_LOWER = BooleanProperty.of("southeast_lower");
    public static final Property<Boolean> SW_UPPER = BooleanProperty.of("southwest_upper");
    public static final Property<Boolean> SW_LOWER = BooleanProperty.of("southwest_lower");

    public ConnectedCasingBlock(Identifier texture, Settings settings) {
        super(settings);
        this.texture = texture;
        setDefaultState(this.getDefaultState()
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false)
                .with(NE_UPPER, false)
                .with(NE_LOWER, false)
                .with(NW_UPPER, false)
                .with(NW_LOWER, false)
                .with(SE_UPPER, false)
                .with(SE_LOWER, false)
                .with(SW_UPPER, false)
                .with(SW_LOWER, false)
        );
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return updateProperties(ctx.getWorld(), ctx.getBlockPos(), this.getDefaultState());
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return updateProperties(world, pos, state);
    }

    public static BlockState updateProperties(WorldView world, BlockPos pos, BlockState state) {
        if (world == null) return state;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof ConnectedCasingBlockEntity entity)) return state;

        List<Direction> adjacentCasing = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockEntity be = world.getBlockEntity(pos.offset(direction));
            if (be instanceof ConnectedCasingBlockEntity casing) {
                if (casing.getTexture().equals(entity.getTexture())) {
                    adjacentCasing.add(direction);
                }
            }
        }

        boolean hasN = adjacentCasing.contains(Direction.NORTH);
        boolean hasS = adjacentCasing.contains(Direction.SOUTH);
        boolean hasE = adjacentCasing.contains(Direction.EAST);
        boolean hasW = adjacentCasing.contains(Direction.WEST);
        boolean hasU = adjacentCasing.contains(Direction.UP);
        boolean hasD = adjacentCasing.contains(Direction.DOWN);

        state = state.with(NORTH, hasN)
                .with(SOUTH, hasS)
                .with(EAST, hasE)
                .with(WEST, hasW)
                .with(UP, hasU)
                .with(DOWN, hasD);

        boolean neUpper = (hasN && hasE && !hasU) || (hasU && (hasN || hasE) &&
                (isCasingMatch(world, pos.offset(Direction.NORTH).offset(Direction.UP), entity) ||
                        isCasingMatch(world, pos.offset(Direction.EAST).offset(Direction.UP), entity)));
        state = state.with(NE_UPPER, neUpper);

        boolean neLower = (hasN && hasE && !hasD) || (hasD && (hasN || hasE) &&
                (isCasingMatch(world, pos.offset(Direction.NORTH).offset(Direction.DOWN), entity) ||
                        isCasingMatch(world, pos.offset(Direction.EAST).offset(Direction.DOWN), entity)));
        state = state.with(NE_LOWER, neLower);

        boolean nwUpper = (hasN && hasW && !hasU) || (hasU && (hasN || hasW) &&
                (isCasingMatch(world, pos.offset(Direction.NORTH).offset(Direction.UP), entity) ||
                        isCasingMatch(world, pos.offset(Direction.WEST).offset(Direction.UP), entity)));
        state = state.with(NW_UPPER, nwUpper);

        boolean nwLower = (hasN && hasW && !hasD) || (hasD && (hasN || hasW) &&
                (isCasingMatch(world, pos.offset(Direction.NORTH).offset(Direction.DOWN), entity) ||
                        isCasingMatch(world, pos.offset(Direction.WEST).offset(Direction.DOWN), entity)));
        state = state.with(NW_LOWER, nwLower);

        boolean seUpper = (hasS && hasE && !hasU) || (hasU && (hasS || hasE) &&
                (isCasingMatch(world, pos.offset(Direction.SOUTH).offset(Direction.UP), entity) ||
                        isCasingMatch(world, pos.offset(Direction.EAST).offset(Direction.UP), entity)));
        state = state.with(SE_UPPER, seUpper);

        boolean seLower = (hasS && hasE && !hasD) || (hasD && (hasS || hasE) &&
                (isCasingMatch(world, pos.offset(Direction.SOUTH).offset(Direction.DOWN), entity) ||
                        isCasingMatch(world, pos.offset(Direction.EAST).offset(Direction.DOWN), entity)));
        state = state.with(SE_LOWER, seLower);

        boolean swUpper = (hasS && hasW && !hasU) || (hasU && (hasS || hasW) &&
                (isCasingMatch(world, pos.offset(Direction.SOUTH).offset(Direction.UP), entity) ||
                        isCasingMatch(world, pos.offset(Direction.WEST).offset(Direction.UP), entity)));
        state = state.with(SW_UPPER, swUpper);

        boolean swLower = (hasS && hasW && !hasD) || (hasD && (hasS || hasW) &&
                (isCasingMatch(world, pos.offset(Direction.SOUTH).offset(Direction.DOWN), entity) ||
                        isCasingMatch(world, pos.offset(Direction.WEST).offset(Direction.DOWN), entity)));
        state = state.with(SW_LOWER, swLower);

        return state;
    }

    private static boolean isCasingMatch(WorldView world, BlockPos pos, ConnectedCasingBlockEntity baseEntity) {
        Optional<ConnectedCasingBlockEntity> optEntity = world.getBlockEntity(pos, ModBlockEntities.CONNECTED_CASING_BLOCK_ENTITY);
        return optEntity.isPresent() && optEntity.get().getTexture().equals(baseEntity.getTexture());
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ConnectedCasingBlockEntity(texture, pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(
                NORTH, EAST, SOUTH, WEST, UP, DOWN,
                NE_UPPER, NE_LOWER,
                NW_UPPER, NW_LOWER,
                SE_UPPER, SE_LOWER,
                SW_UPPER, SW_LOWER
        );
    }
}
