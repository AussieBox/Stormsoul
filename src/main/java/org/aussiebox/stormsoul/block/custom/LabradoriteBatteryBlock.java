package org.aussiebox.stormsoul.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.LabradoriteBatteryBlockEntity;
import org.jetbrains.annotations.Nullable;

public class LabradoriteBatteryBlock extends BlockWithEntity {
    public static final MapCodec<LabradoriteBatteryBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codec.DOUBLE.fieldOf("max_storage").forGetter(block -> block.maxStorage), createSettingsCodec())
                    .apply(instance, LabradoriteBatteryBlock::new)
    );
    private final double maxStorage;
    public static final IntProperty STORED_DISPLAY = IntProperty.of("stored_display", 0, 8);

    public LabradoriteBatteryBlock(double maxStorage, Settings settings) {
        super(settings);
        this.maxStorage = maxStorage;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LabradoriteBatteryBlockEntity(maxStorage, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.LABRADORITE_BATTERY_BLOCK_ENTITY, LabradoriteBatteryBlockEntity::tick);
    }

    public static boolean ifNotEmpty(BlockState state, BlockView world, BlockPos pos) {
        return state.get(STORED_DISPLAY, 0) != 0;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STORED_DISPLAY);
    }
}
