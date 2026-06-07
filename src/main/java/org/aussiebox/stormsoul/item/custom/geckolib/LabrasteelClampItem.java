package org.aussiebox.stormsoul.item.custom.geckolib;

import com.llamalad7.mixinextras.lib.apache.commons.mutable.MutableObject;
import net.minecraft.item.BlockItem;
import org.aussiebox.stormsoul.block.ModBlocks;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class LabrasteelClampItem extends BlockItem implements GeoItem {
    public final MutableObject<GeoRenderProvider> geoRenderProvider = new MutableObject<>();
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public LabrasteelClampItem(Settings settings) {
        super(ModBlocks.LABRASTEEL_CLAMP, settings);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoItem>("Clamp", (test) -> test.setAndContinue(RawAnimation.begin().thenPlayAndHold("clamp"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(this.geoRenderProvider.getValue());
    }
}
