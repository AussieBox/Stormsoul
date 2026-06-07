package org.aussiebox.stormsoul.blockentity.custom;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LabrasteelClampBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    @Getter private ItemStack stack = ItemStack.EMPTY;

    public LabrasteelClampBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LABRASTEEL_CLAMP_BLOCK_ENTITY, pos, state);
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        if (world != null) world.updateListeners(pos, this.getCachedState(), world.getBlockState(pos), 3);
        markDirty();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoBlockEntity>("Clamp", (test) -> {
            if (stack != ItemStack.EMPTY) return test.setAndContinue(RawAnimation.begin().thenPlayAndHold("clamp"));
            else return test.setAndContinue(RawAnimation.begin().thenPlayAndHold("unclamp"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected void readData(ReadView tag) {
        super.readData(tag);

        stack = tag.read("stack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    @Override
    protected void writeData(WriteView tag) {
        super.writeData(tag);

        if (!stack.isEmpty()) tag.put("stack", ItemStack.CODEC, stack);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
