package org.aussiebox.stormsoul.client;

import com.google.common.base.Suppliers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.client.geckolib.model.LabradoriteBatteryModel;
import org.aussiebox.stormsoul.client.geckolib.renderer.LabradoriteBatteryRenderer;
import org.aussiebox.stormsoul.client.geckolib.renderer.StormsoulIlluminosRenderer;
import org.aussiebox.stormsoul.client.model.ArtificialCloudModel;
import org.aussiebox.stormsoul.client.particle.ArtificialCloudSparkParticle;
import org.aussiebox.stormsoul.client.render.blockentity.ArtificialCloudBlockEntityRenderer;
import org.aussiebox.stormsoul.item.ModItems;
import org.aussiebox.stormsoul.item.custom.StormsoulIlluminosItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Supplier;

public class StormsoulClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.TRANSLUCENT,
                ModBlocks.ARTIFICIAL_CLOUD,
                ModBlocks.COPPER_LABRADORITE_BATTERY
        );

        BlockEntityRendererFactories.register(ModBlockEntities.ARTIFICIAL_CLOUD_BLOCK_ENTITY, ArtificialCloudBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.COPPER_LABRADORITE_BATTERY_BLOCK_ENTITY, (context) -> new LabradoriteBatteryRenderer(new LabradoriteBatteryModel(Stormsoul.id("textures/block/copper_labradorite_battery.png"))));

        EntityModelLayerRegistry.registerModelLayer(ArtificialCloudModel.LAYER, ArtificialCloudModel::getTexturedModelData);

        ParticleFactoryRegistry.getInstance().register(Stormsoul.ARTIFICIAL_CLOUD_SPARK, ArtificialCloudSparkParticle.Factory::new);

        ModItems.STORMSOUL_ILLUMINOS.geoRenderProvider.setValue(new GeoRenderProvider() {
            private final Supplier<GeoItemRenderer<StormsoulIlluminosItem>> renderer = Suppliers.memoize(StormsoulIlluminosRenderer::new);

            @Override
            public @Nullable GeoItemRenderer<StormsoulIlluminosItem> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });
    }
}
