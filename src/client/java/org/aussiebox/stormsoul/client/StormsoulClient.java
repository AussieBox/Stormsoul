package org.aussiebox.stormsoul.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.client.model.ArtificialCloudModel;
import org.aussiebox.stormsoul.client.particle.ArtificialCloudSparkParticle;
import org.aussiebox.stormsoul.client.render.blockentity.ArtificialCloudBlockEntityRenderer;

public class StormsoulClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(ModBlocks.ARTIFICIAL_CLOUD, BlockRenderLayer.TRANSLUCENT);

        BlockEntityRendererFactories.register(ModBlockEntities.ARTIFICIAL_CLOUD_BLOCK_ENTITY, ArtificialCloudBlockEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ArtificialCloudModel.LAYER, ArtificialCloudModel::getTexturedModelData);

        ParticleFactoryRegistry.getInstance().register(Stormsoul.ARTIFICIAL_CLOUD_SPARK, ArtificialCloudSparkParticle.Factory::new);
    }
}
