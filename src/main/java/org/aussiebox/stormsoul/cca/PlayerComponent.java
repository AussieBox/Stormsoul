package org.aussiebox.stormsoul.cca;

import lombok.Getter;
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

    @Getter private int illuminosSurgeTicks = 0;

    public PlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public void startSurge() {
        illuminosSurgeTicks = 120;
        sync();
    }

    @Override
    public void serverTick() {
        if (illuminosSurgeTicks > 0) illuminosSurgeTicks--;
        sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void readData(ReadView tag) {
        illuminosSurgeTicks = tag.getInt("illuminosSurgeTicks", 0);
    }

    @Override
    public void writeData(WriteView tag) {
        tag.putInt("illuminosSurgeTicks", illuminosSurgeTicks);
    }
}
