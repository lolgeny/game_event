package net.lolad.game_event.mixin;

import net.lolad.game_event.duck.MinecraftServerDuck;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow
    abstract public MinecraftServer getServer();
    @Inject(method= "emitGameEvent(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;)V",at=@At("TAIL"))
    private void emitGameEvent(Entity entity, GameEvent event, BlockPos pos, CallbackInfo ci) {
        ((MinecraftServerDuck)getServer()).getGameEventManager().game_events.forEach(
                (id, game_event_function) -> {
                    if (game_event_function.trigger.getId().equals(event.getId())) {
                        for (LootCondition predicate : game_event_function.predicate) {
                            LootContext context = new LootContext.Builder((ServerWorld) (Object) this)
                                    .parameter(LootContextParameters.ORIGIN, Vec3d.of(pos))
                                    .optionalParameter(LootContextParameters.THIS_ENTITY, entity)
                                    .build(LootContextTypes.SELECTOR);
                            if (!predicate.test(context)) {
                                return;
                            }
                        }
                        getServer().getCommandFunctionManager().getFunction(game_event_function.function.getId()).ifPresent(
                            command -> {
                                ServerCommandSource source = getServer().getCommandFunctionManager().getTaggedFunctionSource()
                                        .withPosition(Vec3d.of(pos));
                                if (entity != null) {
                                    source = source.withEntity(entity);
                                }
                                getServer().getCommandFunctionManager().execute(
                                        command,
                                        source
                                );
                            }
                        );
                    }
                }
        );
    }
}
