package net.lolad.game_event;

import com.google.common.collect.ImmutableSet;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.world.event.GameEvent;

public class GameEventFunction {
    public final GameEvent trigger;
    public final CommandFunction.LazyContainer function;
    public final ImmutableSet<LootCondition> predicate;
    public GameEventFunction(GameEvent trigger, CommandFunction.LazyContainer function, ImmutableSet<LootCondition> predicate) {
        this.trigger = trigger;
        this.function = function;
        this.predicate = predicate;
    }
}
