package tech.wolfdev.launcher.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import tech.wolfdev.launcher.Launcher;

public class RichPresence {

    Launcher main = new Launcher();
    public String VERSION = "1.0.0";
    public void startup() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {

            main.logger.info("Discord RPC connected as " + user.username + "#" + user.discriminator);
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder("In the main menu");
            presence.setDetails("Running version " + VERSION);
            presence.setBigImage("phoenix", "Wings Launcher");
        }).build();
        DiscordRPC.discordInitialize("885969636707950602", handlers, true);

    }
}
