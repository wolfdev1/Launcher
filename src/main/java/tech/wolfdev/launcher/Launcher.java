package tech.wolfdev.launcher;

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wolfdev.launcher.discord.RichPresence;
import tech.wolfdev.launcher.minecraft.GameLauncher;
import tech.wolfdev.launcher.minecraft.MinecraftVersions;
import tech.wolfdev.launcher.minecraft.Version;
import tech.wolfdev.launcher.ui.LoginUI;

import java.io.IOException;

public class Launcher {
    public Logger logger = LoggerFactory.getLogger(Launcher.class);
    public static void main(String[] args) throws IOException, InterruptedException {



        GameLauncher gameLauncher = new GameLauncher();
        MinecraftVersions versions = new MinecraftVersions();
        versions.loadVersions();
        Version version = versions.getVersions().get("1.18.2");
        //gameLauncher.launch(version, "test");
        LoginUI loginUI = new LoginUI();
        loginUI.login();

    

        RichPresence richPresence = new RichPresence();
            richPresence.startup();

    }
}
