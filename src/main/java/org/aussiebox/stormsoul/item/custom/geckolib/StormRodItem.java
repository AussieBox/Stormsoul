package org.aussiebox.stormsoul.item.custom.geckolib;

import com.llamalad7.mixinextras.lib.apache.commons.mutable.MutableObject;
import net.minecraft.item.BlockItem;
import org.aussiebox.stormsoul.block.ModBlocks;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class StormRodItem extends BlockItem implements GeoItem {
    public final MutableObject<GeoRenderProvider> geoRenderProvider = new MutableObject<>();
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public StormRodItem(Settings settings) {
        super(ModBlocks.STORM_ROD, settings);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoBlockEntity>("Main", (test) -> test.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
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
