package net.lolad.game_event.mixin;

import net.lolad.game_event.GameEventManager;
import net.lolad.game_event.duck.MinecraftServerDuck;
import net.lolad.game_event.duck.ServerResourceManagerDuck;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin implements MinecraftServerDuck {
    @Accessor("serverResourceManager")
    public abstract ServerResourceManager getServerResourceManager();
    @Override
    public GameEventManager getGameEventManager() {
        return ((ServerResourceManagerDuck)getServerResourceManager()).getGameEventManager();
    }
}
