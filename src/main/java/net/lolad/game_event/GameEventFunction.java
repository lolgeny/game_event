package net.lolad.game_event;

import net.minecraft.server.function.CommandFunction;
import net.minecraft.world.event.GameEvent;

public class GameEventFunction {
    public final GameEvent trigger;
    public final CommandFunction.LazyContainer function;
//    @Nullable public final LocationCheckLootCondition predicate;
    public GameEventFunction(GameEvent trigger, CommandFunction.LazyContainer function) {
        this.trigger = trigger;
        this.function = function;
    }
}
