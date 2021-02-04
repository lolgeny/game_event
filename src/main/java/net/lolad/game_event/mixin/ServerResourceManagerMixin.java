package net.lolad.game_event.mixin;

import net.lolad.game_event.GameEventManager;
import net.lolad.game_event.duck.ServerResourceManagerDuck;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public abstract class ServerResourceManagerMixin implements ServerResourceManagerDuck {
    private GameEventManager gameEventManager = null;
    @Override
    public GameEventManager getGameEventManager() {
        return gameEventManager;
    }
    @Accessor("resourceManager")
    abstract ReloadableResourceManager getResourceManager();
    @Inject(method= "<init>(Lnet/minecraft/util/registry/DynamicRegistryManager;Lnet/minecraft/server/command/CommandManager$RegistrationEnvironment;I)V",at=@At("RETURN"))
    private void init(DynamicRegistryManager dynamicRegistryManager, CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci) {
        this.gameEventManager = new GameEventManager();
        getResourceManager().registerListener(this.gameEventManager);
    }
}
