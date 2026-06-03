package org.aussiebox.stormsoul.blockentity.custom;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.custom.ConnectedCasingBlock;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ConnectedCasingBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Getter private Identifier texture;

    public ConnectedCasingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONNECTED_CASING_BLOCK_ENTITY, pos, state);
    }

    public ConnectedCasingBlockEntity(Identifier texture, BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONNECTED_CASING_BLOCK_ENTITY, pos, state);
        this.texture = texture;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoBlockEntity>("North", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.NORTH, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("north"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("East", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.EAST, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("east"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("South", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.SOUTH, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("south"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("West", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.WEST, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("west"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("Up", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.UP, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("up"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("Down", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.DOWN, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("down"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("NorthEastUpper", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.NE_UPPER, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("northeast_upper"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("NorthEastLower", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.NE_LOWER, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("northeast_lower"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("NorthWestUpper", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.NW_UPPER, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("northwest_upper"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("NorthWestLower", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.NW_LOWER, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("northwest_lower"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("SouthEastUpper", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.SE_UPPER, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("southeast_upper"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("SouthEastLower", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.SE_LOWER, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("southeast_lower"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("SouthWestUpper", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.SW_UPPER, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("southwest_upper"));
            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<GeoBlockEntity>("SouthWestLower", (test) -> {
            test.controller().transitionLength(0);
            if (world == null) return PlayState.STOP;
            if (world.getBlockState(pos).get(ConnectedCasingBlock.SW_LOWER, false)) return test.setAndContinue(RawAnimation.begin().thenLoop("southwest_lower"));
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected void readData(ReadView tag) {
        super.readData(tag);

        texture = tag.read("texture", Identifier.CODEC).orElse(Stormsoul.id("textures/block/copper_labrasteel_battery.png"));
    }

    @Override
    protected void writeData(WriteView tag) {
        super.writeData(tag);

        tag.put("texture", Identifier.CODEC, texture);
    }
}
