package dev.nobleskye.skyenet. Default = {maven_group}.{mod_id};

import net.fabricmc.api.ModInitializer;
import xyz.nucleoid.plasmid.api.game.GameType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dev.nobleskye.skyenet. Default = {maven_group}.{mod_id}.game.SkyeNetConfig;
import dev.nobleskye.skyenet. Default = {maven_group}.{mod_id}.game.SkyeNetWaiting;

public class SkyeNet implements ModInitializer {

    public static final String ID = "SkyeNet";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final GameType<SkyeNetConfig> TYPE = GameType.register(
            Identifier.of(ID, "SkyeNet"),
            SkyeNetConfig.CODEC,
            SkyeNetWaiting::open
    );

    @Override
    public void onInitialize() {}
}
