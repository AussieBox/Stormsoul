package org.aussiebox.stormsoul.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.LabradoriteBatteryBlockEntity;
import org.jetbrains.annotations.Nullable;

public class LabradoriteBatteryBlock extends BlockWithEntity  {
    public static final MapCodec<LabradoriteBatteryBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codec.DOUBLE.fieldOf("max_storage").forGetter(block -> block.maxStorage), createSettingsCodec())
                    .apply(instance, LabradoriteBatteryBlock::new)
    );
    private final double maxStorage;

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
        return validateTicker(type, ModBlockEntities.COPPER_LABRADORITE_BATTERY_BLOCK_ENTITY, LabradoriteBatteryBlockEntity::tick);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof LabradoriteBatteryBlockEntity entity) {
            entity.setComponents(itemStack.getComponents());
            entity.markDirty();
        }
    }
}
