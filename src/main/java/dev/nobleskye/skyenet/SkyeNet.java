// filepath: /mnt/sda4/SkyeNetwork/Minigames/src/main/java/dev/nobleskye/skyenet/SkyeNet.java
package dev.nobleskye.skyenet;

import net.fabricmc.api.ModInitializer;
import xyz.nucleoid.plasmid.api.game.GameType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dev.nobleskye.skyenet.game.SkyeNetConfig;
import dev.nobleskye.skyenet.game.SkyeNetWaiting;

public class SkyeNet implements ModInitializer {

    public static final String ID = "skyenet";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final GameType<SkyeNetConfig> TYPE = GameType.register(
            Identifier.of(ID, "skyenet"),
            SkyeNetConfig.CODEC,
            SkyeNetWaiting::open
    );

    @Override
    public void onInitialize() {}
}
