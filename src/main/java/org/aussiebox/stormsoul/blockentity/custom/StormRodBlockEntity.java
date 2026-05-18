package org.aussiebox.stormsoul.blockentity.custom;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.block.custom.ArtificialCloudBlock;
import org.aussiebox.stormsoul.block.custom.StormRodBlock;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.cca.ArtificialStormComponent;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class StormRodBlockEntity extends AbstractStormsoulBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    @Getter private boolean isActive;
    @Getter private boolean struck;

    public StormRodBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORM_ROD_BLOCK_ENTITY, pos, state);
        setMaxStoredStormsoul(100);
        setTransferPerTick(1);
        setOutputDirections(state.get(StormRodBlock.FACING).getOpposite());
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof StormRodBlockEntity stormEntity)) return;
        AbstractStormsoulBlockEntity.tick(world, pos, state, entity);

        ArtificialStormComponent storm = ArtificialStormComponent.KEY.get(world);

        boolean setActiveTo = false;
        boolean setStruckTo = false;
        BlockPos boxEndPos = pos.up(3);
        for (BlockState checkState : world.getStatesInBox(new Box(pos.getX(), pos.getY(), pos.getZ(), boxEndPos.getX(), boxEndPos.getY(), boxEndPos.getZ())).toList()) {
            if (checkState.isOf(ModBlocks.ARTIFICIAL_CLOUD) && storm.storming) {
                setActiveTo = true;
                Optional<Boolean> striking = checkState.getOrEmpty(ArtificialCloudBlock.STRIKING);
                if (!setStruckTo) setStruckTo = striking.orElse(false);
            }
        }

        stormEntity.isActive = setActiveTo;
        stormEntity.struck = setStruckTo;

        if (stormEntity.struck) stormEntity.addStored(1);
        stormEntity.markDirty();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoBlockEntity>("Main", (test) -> {
            if (!isActive) return test.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            else if (!struck) return test.setAndContinue(RawAnimation.begin().thenLoop("active"));
            else return test.setAndContinue(RawAnimation.begin().thenLoop("struck"));
        }).transitionLength(0));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected void readData(ReadView tag) {
        isActive = tag.getBoolean("isActive", false);
        struck = tag.getBoolean("struck", false);
    }

    @Override
    protected void writeData(WriteView tag) {
        tag.putBoolean("isActive", isActive);
        tag.putBoolean("struck", struck);
    }
}
