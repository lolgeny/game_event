package net.lolad.game_event;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.loot.LootGsons;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class GameEventManager extends JsonDataLoader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = LootGsons.getTableGsonBuilder().create();
    public Map<Identifier, GameEventFunction> game_events = ImmutableMap.of();
    public GameEventManager() {
        super(GSON, "game_events");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {
        ImmutableMap.Builder<Identifier, GameEventFunction> game_events = ImmutableMap.builder();
        loader.forEach(
            (id, json) -> {
                try {
//                    LocationCheckLootCondition predicate = null;
//                    if (json.getAsJsonObject().has("predicate")) {
//                        predicate = new LocationCheckLootCondition.Serializer().fromJson(
//                                json.getAsJsonObject().get("predicate").getAsJsonObject(),
//                                new JsonDeserializationContext() {
//                                    @Override
//                                    public <T> T deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
//                                        return null;
//                                    }
//                                }
//                        );
//                    }
                    game_events.put(id, new GameEventFunction(
                            Registry.GAME_EVENT.get(
                                    Identifier.tryParse(
                                            json
                                                    .getAsJsonObject()
                                                    .get("event")
                                                    .getAsString()
                                    )
                            ),
                            new CommandFunction.LazyContainer(
                                    Identifier.tryParse(json
                                            .getAsJsonObject()
                                            .get("function")
                                            .getAsString()
                                    )
                            )
                        )
                    );
                } catch (Exception e) {
                    LOGGER.error("Couldn't pass game event handler {}", id, e);
                }
            }
        );
        this.game_events = game_events.build();
    }
}
