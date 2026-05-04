package org.aussiebox.stormsoul.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.recipe.ControlledShockRecipe;
import org.aussiebox.stormsoul.recipe.ModRecipes;
import org.aussiebox.stormsoul.recipe.inventory.ControlledShockInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin extends Entity {
    @Shadow
    public abstract void setCosmetic(boolean cosmetic);

    public LightningEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "powerLightningRod", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LightningRodBlock;setPowered(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private void stormsoul$craftControlledShockRecipe(CallbackInfo ci, @Local BlockPos pos, @Local BlockState state) {
        LightningEntity entity = (LightningEntity) (Object) this;
        if (entity == null) return;
        World world = entity.getWorld();
        if (world == null) return;
        ServerRecipeManager manager = (ServerRecipeManager) world.getRecipeManager();
        if (manager == null) return;

        List<ItemStack> stacks = new ArrayList<>();
        List<ItemEntity> itemEntities = world.getEntitiesByClass(ItemEntity.class, new Box(pos.getX()-1.5, pos.getY()-1.5, pos.getZ()-1.5, pos.getX()+1.5, pos.getY()+1.5, pos.getZ()+1.5), Entity::isAlive);
        itemEntities.forEach((itemEntity -> stacks.add(itemEntity.getStack())));

        List<BlockState> blocks = world.getStatesInBox(new Box(pos.getX()-1, pos.getY()-1, pos.getZ()-1, pos.getX()+1, pos.getY()-1, pos.getZ()+1)).toList();

        ControlledShockInventory input = new ControlledShockInventory(stacks, blocks, pos);
        Optional<RecipeEntry<ControlledShockRecipe>> entry = manager.getFirstMatch(ModRecipes.CONTROLLED_SHOCK_TYPE, input, world);
        if (entry.isEmpty()) return;

        entity.setCosmetic(true);
        ControlledShockRecipe recipe = entry.get().value();
        ItemEntity resultEntity = new ItemEntity(world, pos.getX(), pos.getY()+3, pos.getZ(), recipe.getOutput());
        resultEntity.setToDefaultPickupDelay();

        itemEntities.forEach(itemEntity -> itemEntity.remove(RemovalReason.DISCARDED));
        world.spawnEntity(resultEntity);
    }
}
