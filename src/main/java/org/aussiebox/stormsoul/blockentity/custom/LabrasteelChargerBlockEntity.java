package org.aussiebox.stormsoul.blockentity.custom;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.StormsoulConstants;
import org.aussiebox.stormsoul.block.custom.LabrasteelChargerBlock;
import org.aussiebox.stormsoul.block.custom.LabrasteelClampBlock;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.recipe.ChargingRecipe;
import org.aussiebox.stormsoul.recipe.ModRecipes;
import org.aussiebox.stormsoul.recipe.inventory.ChargingInventory;
import org.aussiebox.stormsoul.recipe.serializer.ChargingRecipeSerializer;
import org.aussiebox.stormsoul.util.StormsoulUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LabrasteelChargerBlockEntity extends AbstractStormsoulBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    @Getter @Nullable ChargingRecipe crafting = null;
    @Getter int craftTicks = 0;
    @Getter int originalCraftTicks = 0;
    @Getter @Nullable BlockPos clampPos = null;
    @Getter int craftCooldown = 0;

    @Getter private double lastSpinTime = 0;
    @Getter private double spinTime = 0;

    private double spincrement = 1;
    private double lastSpincrement = 0;

    public LabrasteelChargerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LABRASTEEL_CHARGER_BLOCK_ENTITY, pos, state);
        setMaxStoredStormsoul(1000);
        setInputDirections(state.get(LabrasteelChargerBlock.FACING, Direction.UP).getOpposite());
    }

    public void startCrafting(ChargingRecipe recipe, ChargingInventory inventory, BlockPos clampPos, World world) {
        this.crafting = recipe;
        this.originalCraftTicks = inventory.getStack().getCount() * 160; // 8s per item
        this.craftTicks = this.originalCraftTicks;
        this.clampPos = clampPos;
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof LabrasteelChargerBlockEntity chargerEntity)) return;
        AbstractStormsoulBlockEntity.tick(world, pos, state, entity);

        chargerEntity.lastSpinTime = chargerEntity.spinTime;
        chargerEntity.lastSpincrement = chargerEntity.spincrement;

        if (chargerEntity.craftCooldown > 0) chargerEntity.spinTime += MathHelper.lerp((double) chargerEntity.craftCooldown / 40, 1d, 50d);
        else if (chargerEntity.craftTicks > 0) chargerEntity.spinTime += MathHelper.lerp((double) chargerEntity.craftTicks / chargerEntity.originalCraftTicks, 50d, 1d);
        else chargerEntity.spinTime++;

        if (world.isClient) return;

        ServerRecipeManager manager = (ServerRecipeManager) world.getRecipeManager();
        if (manager == null) return;

        if (chargerEntity.craftCooldown > 0) chargerEntity.craftCooldown--;
        else if (chargerEntity.craftTicks > 0) {
            chargerEntity.craftTicks--;

            BlockEntity clampEntity = world.getBlockEntity(chargerEntity.clampPos);
            if (clampEntity == null) return;

            Vec3d pos1 = chargerEntity.pos.toCenterPos().add(0.5, 0.5, 0.5);
            Vec3d pos2 = clampEntity.getPos().toCenterPos().subtract(0.5, 0.5, 0.5);
            Vec3d particlePos = StormsoulUtil.getRandomPosition(new Box(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z), new Random());
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(Stormsoul.STORMSOUL_SPARK, particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);

                DamageSource damageSource = world.getDamageSources().create(StormsoulConstants.CHARGER_INTERFERENCE);
                List<Entity> entities = world.getOtherEntities(null, new Box(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z));
                if (chargerEntity.craftTicks % 20 == 0) entities.forEach(entity1 -> entity1.damage(serverWorld, damageSource, 2));
            }
        }
        else {
            BlockPos theoreticalClampPos = pos.offset(state.get(LabrasteelChargerBlock.FACING, Direction.UP), 2);
            if (world.getBlockEntity(theoreticalClampPos) instanceof LabrasteelClampBlockEntity clampEntity) {
                if (world.getBlockState(theoreticalClampPos).get(LabrasteelClampBlock.FACING, Direction.UP) == state.get(LabrasteelChargerBlock.FACING, Direction.UP).getOpposite()) {
                    ChargingInventory input = new ChargingInventory(clampEntity.getStack(), chargerEntity.getStoredStormsoul(), pos, theoreticalClampPos);
                    Optional<RecipeEntry<ChargingRecipe>> entry = manager.getFirstMatch(ModRecipes.CHARGING_TYPE, input, world);
                    entry.ifPresent(recipeEntry -> chargerEntity.startCrafting(recipeEntry.value(), input, theoreticalClampPos, world));
                }
            }
        }
        if (chargerEntity.craftTicks == 0 && chargerEntity.crafting != null) {
            if (world.getBlockEntity(chargerEntity.clampPos) instanceof LabrasteelClampBlockEntity clampEntity) {
                chargerEntity.removeStored(chargerEntity.crafting.getStormsoul() * clampEntity.getStack().getCount());
                clampEntity.setStack(chargerEntity.crafting.getOutput().copyWithCount(clampEntity.getStack().getCount()));
            }
            world.setBlockState(chargerEntity.clampPos, world.getBlockState(chargerEntity.clampPos).with(LabrasteelClampBlock.LOCKED, false));
            chargerEntity.crafting = null;
            chargerEntity.clampPos = null;
            chargerEntity.craftCooldown = 40;
            chargerEntity.originalCraftTicks = 0;
        }

        chargerEntity.spincrement = chargerEntity.spinTime - chargerEntity.lastSpinTime;

        if (chargerEntity.spincrement != chargerEntity.lastSpincrement) world.updateListeners(pos, chargerEntity.getCachedState(), world.getBlockState(pos), 3);
        chargerEntity.markDirty();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoBlockEntity>("Spin", (test) -> test.setAndContinue(RawAnimation.begin().thenLoop("spin"))));
        controllers.add(new AnimationController<GeoBlockEntity>("EnergyRing", (test) -> test.setAndContinue(RawAnimation.begin().thenLoop("energy_ring"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected void readData(ReadView tag) {
        super.readData(tag);

        crafting = tag.read("crafting", ChargingRecipeSerializer.CODEC.codec()).orElse(null);
        craftTicks = tag.getInt("craftTicks", 0);
        originalCraftTicks = tag.getInt("originalCraftTicks", 0);
        clampPos = tag.read("clampPos", BlockPos.CODEC).orElse(null);
        craftCooldown = tag.getInt("craftCooldown", 0);
        spinTime = tag.getDouble("spinTime", 0);
        lastSpinTime = tag.getDouble("lastSpinTime", 0);
    }

    @Override
    protected void writeData(WriteView tag) {
        super.writeData(tag);

        if (crafting != null) tag.put("crafting", ChargingRecipeSerializer.CODEC.codec(), crafting);
        if (craftTicks > 0) tag.putInt("craftTicks", craftTicks);
        if (originalCraftTicks > 0) tag.putInt("originalCraftTicks", originalCraftTicks);
        if (clampPos != null) tag.put("clampPos", BlockPos.CODEC, clampPos);
        if (craftCooldown > 0) tag.putInt("craftCooldown", craftCooldown);
        tag.putDouble("spinTime", spinTime);
        tag.putDouble("lastSpinTime", lastSpinTime);
    }
}
