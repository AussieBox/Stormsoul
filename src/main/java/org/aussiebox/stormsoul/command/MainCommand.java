package org.aussiebox.stormsoul.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.cca.ArtificialStormComponent;

public class MainCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal(Stormsoul.MOD_ID)
                        .then(CommandManager.literal("artificial_storm")
                                .then(CommandManager.literal("advance")
                                        .requires(context -> context.hasPermissionLevel(2))
                                        .executes(MainCommand::advanceStorm)
                                )
                        )
        );
    }

    public static int advanceStorm(CommandContext<ServerCommandSource> context) {
        World world = context.getSource().getWorld();
        ArtificialStormComponent storm = ArtificialStormComponent.KEY.get(world);
        context.getSource().sendMessage(Text.translatable("command.main.advance_storm." + storm.advance()));
        return 1;
    }

}
