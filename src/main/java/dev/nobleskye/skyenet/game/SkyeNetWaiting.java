package dev.nobleskye.skyenet.game;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.plasmid.api.game.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import dev.nobleskye.skyenet.game.map.SkyeNetMap;
import dev.nobleskye.skyenet.game.map.SkyeNetMapGenerator;
import xyz.nucleoid.plasmid.api.game.common.GameWaitingLobby;
import xyz.nucleoid.plasmid.api.game.event.GameActivityEvents;
import xyz.nucleoid.plasmid.api.game.event.GamePlayerEvents;
import xyz.nucleoid.plasmid.api.game.player.JoinOffer;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.player.PlayerDeathEvent;

public class SkyeNetWaiting {
    private final GameSpace gameSpace;
    private final SkyeNetMap map;
    private final SkyeNetConfig config;
    private final SkyeNetSpawnLogic spawnLogic;
    private final ServerWorld world;

    private SkyeNetWaiting(GameSpace gameSpace, ServerWorld world, SkyeNetMap map, SkyeNetConfig config) {
        this.gameSpace = gameSpace;
        this.map = map;
        this.config = config;
        this.world = world;
        this.spawnLogic = new SkyeNetSpawnLogic(gameSpace, world, map);
    }

    public static GameOpenProcedure open(GameOpenContext<SkyeNetConfig> context) {
        SkyeNetConfig config = context.config();
        SkyeNetMapGenerator generator = new SkyeNetMapGenerator(config.mapConfig());
        SkyeNetMap map = generator.build();

        RuntimeWorldConfig worldConfig = new RuntimeWorldConfig()
                .setGenerator(map.asGenerator(context.server()));

        return context.openWithWorld(worldConfig, (game, world) -> {
            SkyeNetWaiting waiting = new SkyeNetWaiting(game.getGameSpace(), world, map, context.config());

            GameWaitingLobby.addTo(game, config.players());

            game.listen(GameActivityEvents.REQUEST_START, waiting::requestStart);
            game.listen(GamePlayerEvents.ADD, waiting::addPlayer);
            game.listen(GamePlayerEvents.OFFER, JoinOffer::accept);
            game.listen(GamePlayerEvents.ACCEPT, joinAcceptor -> joinAcceptor.teleport(world, Vec3d.ZERO));
            game.listen(PlayerDeathEvent.EVENT, waiting::onPlayerDeath);
        });
    }

    private GameResult requestStart() {
        SkyeNetActive.open(this.gameSpace, this.world, this.map, this.config);
        return GameResult.ok();
    }

    private void addPlayer(ServerPlayerEntity player) {
        this.spawnPlayer(player);
    }

    private EventResult onPlayerDeath(ServerPlayerEntity player, DamageSource source) {
        player.setHealth(20.0f);
        this.spawnPlayer(player);
        return EventResult.DENY;
    }

    private void spawnPlayer(ServerPlayerEntity player) {
        this.spawnLogic.resetPlayer(player, GameMode.ADVENTURE);
        this.spawnLogic.spawnPlayer(player);
    }
}
