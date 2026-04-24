package org.aussiebox.stormsoul.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.ArtificialCloudBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ArtificialCloudBlock extends BlockWithEntity {
    public static final MapCodec<ArtificialCloudBlock> CODEC = createCodec(ArtificialCloudBlock::new);
    private static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(2, 2, 2, 14, 8, 14);
    private static final VoxelShape ON_TOP_SHAPE = Block.createCuboidShape(1, 7, 1, 15, 7.8, 15);

    public ArtificialCloudBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof ArtificialCloudBlockEntity entity)) return OUTLINE_SHAPE;
        return OUTLINE_SHAPE.offset(0, entity.lastLargeCloudOffset/16, 0);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof ArtificialCloudBlockEntity cloudEntity)) return VoxelShapes.empty();
        EntityShapeContext entityContext = (EntityShapeContext) context;

        if (entityContext == null) return VoxelShapes.empty();
        if (entityContext.getEntity() == null) return VoxelShapes.empty();
        Entity entity = entityContext.getEntity();

        if (entity.isSneaking()) return VoxelShapes.empty();
        if (entity.getPos().getY() >= pos.getY()+0.1) return ON_TOP_SHAPE;

        return VoxelShapes.empty();
    }



    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ArtificialCloudBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.ARTIFICIAL_CLOUD_BLOCK_ENTITY, ArtificialCloudBlockEntity::tick);
    }
}
