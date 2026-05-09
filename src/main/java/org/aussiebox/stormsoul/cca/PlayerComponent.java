package org.aussiebox.stormsoul.cca;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.aussiebox.stormsoul.Stormsoul;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerComponent implements AutoSyncedComponent, ServerTickingComponent {
    public static final ComponentKey<PlayerComponent> KEY = ComponentRegistry.getOrCreate(Stormsoul.id("player"), PlayerComponent.class);
    private final PlayerEntity player;

    public PlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
//        World world = player.getWorld();
//        if (world == null) return;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void readData(ReadView tag) {

    }

    @Override
    public void writeData(WriteView tag) {

    }
}
