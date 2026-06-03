package org.aussiebox.stormsoul.client;

import com.google.common.base.Suppliers;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.animation.PlayerRawAnimationBuilder;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.layered.IAnimation;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonMode;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.util.Arm;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.StormsoulAnimations;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.cca.PlayerComponent;
import org.aussiebox.stormsoul.client.geckolib.renderer.*;
import org.aussiebox.stormsoul.client.geckolib.renderer.item.ConnectedCasingItemRenderer;
import org.aussiebox.stormsoul.client.geckolib.renderer.item.LabrasteelChargerItemRenderer;
import org.aussiebox.stormsoul.client.geckolib.renderer.item.StormRodItemRenderer;
import org.aussiebox.stormsoul.client.mixin.accessor.EntityTrackingSoundInstanceAccessor;
import org.aussiebox.stormsoul.client.mixin.accessor.SoundManagerAccessor;
import org.aussiebox.stormsoul.client.mixin.accessor.SoundSystemAccessor;
import org.aussiebox.stormsoul.client.model.ArtificialCloudModel;
import org.aussiebox.stormsoul.client.particle.SparkParticle;
import org.aussiebox.stormsoul.client.particle.SurgeTrailParticle;
import org.aussiebox.stormsoul.client.render.blockentity.ArtificialCloudBlockEntityRenderer;
import org.aussiebox.stormsoul.client.render.blockentity.WireConnectorBlockEntityRenderer;
import org.aussiebox.stormsoul.item.ModItems;
import org.aussiebox.stormsoul.item.custom.StormsoulIlluminosItem;
import org.aussiebox.stormsoul.item.custom.geckolib.ConnectedCasingItem;
import org.aussiebox.stormsoul.item.custom.geckolib.LabrasteelChargerItem;
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
                ModBlocks.IRON_LABRASTEEL_BATTERY,
                ModBlocks.LABRASTEEL_CHARGER
        );

        BlockEntityRendererFactories.register(ModBlockEntities.ARTIFICIAL_CLOUD_BLOCK_ENTITY, ArtificialCloudBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.STORM_ROD_BLOCK_ENTITY, (context) -> new StormRodRenderer());
        BlockEntityRendererFactories.register(ModBlockEntities.LABRASTEEL_BATTERY_BLOCK_ENTITY, (context) -> new LabrasteelBatteryRenderer());
        BlockEntityRendererFactories.register(ModBlockEntities.WIRE_CONNECTOR_BLOCK_ENTITY, WireConnectorBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.LABRASTEEL_CHARGER_BLOCK_ENTITY, (context) -> new LabrasteelChargerRenderer());
        BlockEntityRendererFactories.register(ModBlockEntities.CONNECTED_CASING_BLOCK_ENTITY, (context) -> new ConnectedCasingRenderer());

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

        ModItems.LABRASTEEL_CHARGER.geoRenderProvider.setValue(new GeoRenderProvider() {
            private final Supplier<GeoItemRenderer<LabrasteelChargerItem>> renderer = Suppliers.memoize(LabrasteelChargerItemRenderer::new);

            @Override
            public @Nullable GeoItemRenderer<LabrasteelChargerItem> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });

        ModItems.SILVER_CASING.geoRenderProvider.setValue(new GeoRenderProvider() {
            private final Supplier<GeoItemRenderer<ConnectedCasingItem>> renderer = Suppliers.memoize(() -> new ConnectedCasingItemRenderer(Stormsoul.id("textures/block/silver_casing.png")));

            @Override
            public @Nullable GeoItemRenderer<ConnectedCasingItem> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });

        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(StormsoulAnimations.STORMSOUL_ILLUMINOS, 1500,
                player -> new PlayerAnimationController(player,
                        (controller, state, animSetter) -> PlayState.STOP
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            PlayerComponent component = PlayerComponent.KEY.get(client.player);
            PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(client.player, StormsoulAnimations.STORMSOUL_ILLUMINOS);
            if (controller == null) return;

            controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
            if (component.getIlluminosSurgeTicks() == 0) {
                controller.stopTriggeredAnimation();

                SoundManagerAccessor manager = (SoundManagerAccessor) client.getSoundManager();
                SoundSystemAccessor system = (SoundSystemAccessor) manager.getSoundSystem();
                for (SoundInstance sound : system.getSources().keySet()) {
                    if (sound instanceof EntityTrackingSoundInstance trackingSound) {
                        Entity entity = ((EntityTrackingSoundInstanceAccessor) trackingSound).getTrackedEntity();
                        if (entity == client.player && trackingSound.getId().getPath().equals("item.stormsoul_illuminos.surge")) {
                            client.getSoundManager().stop(trackingSound);
                            break;
                        }
                    }
                }
            }
            else {
                if (!controller.isPlayingTriggeredAnimation()) {
                    if (client.player.getOffHandStack().isOf(ModItems.STORMSOUL_ILLUMINOS)) {
                        if (client.player.getMainArm() == Arm.RIGHT) {
                            controller.setFirstPersonConfiguration(IAnimation.DEFAULT_FIRST_PERSON_CONFIG.setShowLeftArm(true).setShowRightArm(false));
                            controller.triggerAnimation(PlayerRawAnimationBuilder.begin().thenLoop(StormsoulAnimations.STORMSOUL_ILLUMINOS_SURGE_LEFT).build());
                        } else {
                            controller.setFirstPersonConfiguration(IAnimation.DEFAULT_FIRST_PERSON_CONFIG.setShowRightArm(true).setShowLeftArm(false));
                            controller.triggerAnimation(PlayerRawAnimationBuilder.begin().thenLoop(StormsoulAnimations.STORMSOUL_ILLUMINOS_SURGE_RIGHT).build());
                        }
                    }
                    else {
                        if (client.player.getMainArm() == Arm.RIGHT) {
                            controller.setFirstPersonConfiguration(IAnimation.DEFAULT_FIRST_PERSON_CONFIG.setShowRightArm(true).setShowLeftArm(false));
                            controller.triggerAnimation(PlayerRawAnimationBuilder.begin().thenLoop(StormsoulAnimations.STORMSOUL_ILLUMINOS_SURGE_RIGHT).build());
                        } else {
                            controller.setFirstPersonConfiguration(IAnimation.DEFAULT_FIRST_PERSON_CONFIG.setShowLeftArm(true).setShowRightArm(false));
                            controller.triggerAnimation(PlayerRawAnimationBuilder.begin().thenLoop(StormsoulAnimations.STORMSOUL_ILLUMINOS_SURGE_LEFT).build());
                        }
                    }
                }
                client.player.setBodyYaw(client.player.getYaw(client.getRenderTickCounter().getTickProgress(true)));
                client.player.setHeadYaw(client.player.getBodyYaw());
            }
        });
    }
}
