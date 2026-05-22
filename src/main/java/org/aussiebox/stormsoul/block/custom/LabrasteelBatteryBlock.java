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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelBatteryBlockEntity;
import org.jetbrains.annotations.Nullable;

public class LabrasteelBatteryBlock extends BlockWithEntity  {
    public static final MapCodec<LabrasteelBatteryBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.STRING.fieldOf("id").forGetter(block -> block.id),
                            Codec.DOUBLE.fieldOf("max_storage").forGetter(block -> block.maxStorage),
                            Identifier.CODEC.fieldOf("texture").forGetter(block -> block.texture),
                            createSettingsCodec()
                    )
                    .apply(instance, LabrasteelBatteryBlock::new)
    );
    private final String id;
    private final double maxStorage;
    private final Identifier texture;

    public LabrasteelBatteryBlock(double maxStorage, String id, Settings settings) {
        super(settings);
        this.id = id;
        this.maxStorage = maxStorage;
        this.texture = Stormsoul.id("textures/block/" + id + "_labrasteel_battery.png");
    }

    // Used in Codec, shouldn't be used elsewhere
    public LabrasteelBatteryBlock(String id, double maxStorage, Identifier texture, Settings settings) {
        super(settings);
        this.id = id;
        this.maxStorage = maxStorage;
        this.texture = texture;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LabrasteelBatteryBlockEntity(maxStorage, texture, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.LABRASTEEL_BATTERY_BLOCK_ENTITY, LabrasteelBatteryBlockEntity::tick);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof LabrasteelBatteryBlockEntity entity) {
            entity.setComponents(itemStack.getComponents());
            entity.markDirty();
        }
    }
}
