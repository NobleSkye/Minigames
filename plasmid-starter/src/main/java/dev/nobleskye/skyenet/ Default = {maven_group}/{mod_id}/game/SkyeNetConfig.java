package dev.nobleskye.skyenet. Default = {maven_group}.{mod_id}.game;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.nobleskye.skyenet. Default = {maven_group}.{mod_id}.game.map.SkyeNetMapConfig;
import xyz.nucleoid.plasmid.api.game.common.config.WaitingLobbyConfig;

public record SkyeNetConfig(WaitingLobbyConfig players, SkyeNetMapConfig mapConfig, int timeLimitSecs) {
    public static final MapCodec<SkyeNetConfig> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            WaitingLobbyConfig.CODEC.fieldOf("players").forGetter(SkyeNetConfig::players),
            SkyeNetMapConfig.CODEC.fieldOf("map").forGetter(SkyeNetConfig::mapConfig),
            Codec.INT.fieldOf("time_limit_secs").forGetter(SkyeNetConfig::timeLimitSecs)
    ).apply(instance, SkyeNetConfig::new));
}
