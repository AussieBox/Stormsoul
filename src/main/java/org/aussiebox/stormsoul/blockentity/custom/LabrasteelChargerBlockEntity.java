package org.aussiebox.stormsoul.blockentity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.aussiebox.stormsoul.block.custom.LabrasteelChargerBlock;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LabrasteelChargerBlockEntity extends AbstractStormsoulBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public LabrasteelChargerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LABRASTEEL_CHARGER_BLOCK_ENTITY, pos, state);
        setMaxStoredStormsoul(1000);
        setInputDirections(state.get(LabrasteelChargerBlock.FACING, Direction.UP).getOpposite());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoBlockEntity>("Spin", (test) -> test.setAndContinue(RawAnimation.begin().thenLoop("spin"))));
        controllers.add(new AnimationController<GeoBlockEntity>("EnergyRing", (test) -> test.setAndContinue(RawAnimation.begin().thenLoop("energy_ring"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
