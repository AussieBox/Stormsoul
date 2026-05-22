package org.aussiebox.stormsoul.blockentity.custom;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LabrasteelBatteryBlockEntity extends AbstractStormsoulBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Getter private Identifier texture;

    public LabrasteelBatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LABRASTEEL_BATTERY_BLOCK_ENTITY, pos, state);
    }

    public LabrasteelBatteryBlockEntity(double maxStored, Identifier texture, BlockPos pos, BlockState state) {
        super(ModBlockEntities.LABRASTEEL_BATTERY_BLOCK_ENTITY, pos, state);
        setMaxStoredStormsoul(maxStored);
        this.texture = texture;
        setInputDirections(Direction.UP);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof LabrasteelBatteryBlockEntity batteryEntity)) return;
        AbstractStormsoulBlockEntity.tick(world, pos, state, entity);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController());
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
