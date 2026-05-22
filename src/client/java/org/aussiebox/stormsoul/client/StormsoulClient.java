package org.aussiebox.stormsoul.client;

import com.google.common.base.Suppliers;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerRawAnimationBuilder;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.StormsoulAnimations;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.cca.PlayerComponent;
import org.aussiebox.stormsoul.client.geckolib.renderer.LabrasteelBatteryRenderer;
import org.aussiebox.stormsoul.client.geckolib.renderer.StormRodRenderer;
import org.aussiebox.stormsoul.client.geckolib.renderer.StormsoulIlluminosRenderer;
import org.aussiebox.stormsoul.client.geckolib.renderer.item.StormRodItemRenderer;
import org.aussiebox.stormsoul.client.model.ArtificialCloudModel;
import org.aussiebox.stormsoul.client.particle.SparkParticle;
import org.aussiebox.stormsoul.client.particle.SurgeTrailParticle;
import org.aussiebox.stormsoul.client.render.blockentity.ArtificialCloudBlockEntityRenderer;
import org.aussiebox.stormsoul.client.render.blockentity.WireConnectorBlockEntityRenderer;
import org.aussiebox.stormsoul.item.ModItems;
import org.aussiebox.stormsoul.item.custom.StormsoulIlluminosItem;
import org.aussiebox.stormsoul.item.custom.geckolib.StormRodItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Supplier;

public class StormsoulClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.TRANSLUCENT,
                ModBlocks.ARTIFICIAL_CLOUD,
                ModBlocks.STORM_ROD,
                ModBlocks.COPPER_LABRASTEEL_BATTERY,
                ModBlocks.IRON_LABRASTEEL_BATTERY
        );

        BlockEntityRendererFactories.register(ModBlockEntities.ARTIFICIAL_CLOUD_BLOCK_ENTITY, ArtificialCloudBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.STORM_ROD_BLOCK_ENTITY, (context) -> new StormRodRenderer());
        BlockEntityRendererFactories.register(ModBlockEntities.LABRASTEEL_BATTERY_BLOCK_ENTITY, (context) -> new LabrasteelBatteryRenderer());
        BlockEntityRendererFactories.register(ModBlockEntities.WIRE_CONNECTOR_BLOCK_ENTITY, WireConnectorBlockEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ArtificialCloudModel.LAYER, ArtificialCloudModel::getTexturedModelData);

        ParticleFactoryRegistry.getInstance().register(Stormsoul.ARTIFICIAL_CLOUD_SPARK, SparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Stormsoul.STORMSOUL_SPARK, SparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Stormsoul.SURGE_TRAIL, SurgeTrailParticle.Factory::new);

        ModItems.STORMSOUL_ILLUMINOS.geoRenderProvider.setValue(new GeoRenderProvider() {
            private final Supplier<GeoItemRenderer<StormsoulIlluminosItem>> renderer = Suppliers.memoize(StormsoulIlluminosRenderer::new);

            @Override
            public @Nullable GeoItemRenderer<StormsoulIlluminosItem> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });

        ModItems.STORM_ROD.geoRenderProvider.setValue(new GeoRenderProvider() {
            private final Supplier<GeoItemRenderer<StormRodItem>> renderer = Suppliers.memoize(StormRodItemRenderer::new);

            @Override
            public @Nullable GeoItemRenderer<StormRodItem> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });

        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(StormsoulAnimations.STORMSOUL_ILLUMINOS, 1500,
                player -> new PlayerAnimationController(player, (controller, state, animSetter) -> {
                    PlayerComponent component = PlayerComponent.KEY.get(player);
                    if (component.getIlluminosSurgeTicks() == 0) return PlayState.STOP;
                    Stormsoul.LOGGER.info("off you go :3c");
                    if (player.getOffHandStack().isOf(ModItems.STORMSOUL_ILLUMINOS)) return animSetter.setAnimation(PlayerRawAnimationBuilder.begin().thenLoop(StormsoulAnimations.STORMSOUL_ILLUMINOS_SURGE_LEFT).build());
                    else return animSetter.setAnimation(PlayerRawAnimationBuilder.begin().thenLoop(StormsoulAnimations.STORMSOUL_ILLUMINOS_SURGE_RIGHT).build());
                })
        );
    }
}
