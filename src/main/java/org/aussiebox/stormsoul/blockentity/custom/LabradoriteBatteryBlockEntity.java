package org.aussiebox.stormsoul.blockentity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LabradoriteBatteryBlockEntity extends AbstractStormsoulBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public LabradoriteBatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COPPER_LABRADORITE_BATTERY_BLOCK_ENTITY, pos, state);
    }

    public LabradoriteBatteryBlockEntity(double maxStored, BlockPos pos, BlockState state) {
        super(ModBlockEntities.COPPER_LABRADORITE_BATTERY_BLOCK_ENTITY, pos, state);
        setMaxStoredStormsoul(maxStored);
        setInputDirections(Direction.UP);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof LabradoriteBatteryBlockEntity batteryEntity)) return;
        AbstractStormsoulBlockEntity.tick(world, pos, state, entity);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
