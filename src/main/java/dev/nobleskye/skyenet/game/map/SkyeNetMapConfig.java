package dev.nobleskye.skyenet.game.map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;

public class SkyeNetMapConfig {
    public static final Codec<SkyeNetMapConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("spawn_block").forGetter(map -> map.spawnBlock)
    ).apply(instance, SkyeNetMapConfig::new));

    public final BlockState spawnBlock;

    public SkyeNetMapConfig(BlockState spawnBlock) {
        this.spawnBlock = spawnBlock;
    }
}
