package tech.wolfdev.launcher.minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class GameLauncher {
    private static final String MINECRAFT_DIR = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\.minecraft";
    private static final String MINECRAFT_VERSION_DIR = MINECRAFT_DIR + "\\versions\\Wings_";

    public void launch(Version version, String username) throws IOException, InterruptedException {
        String versionPath = MINECRAFT_VERSION_DIR + version.getId();
        String classpath = versionPath + "\\" + version.getId() + ".jar";

        if (Files.exists(Paths.get(classpath))) {
            if (new Libraries().hasLauncherLibraries(version)) {
                StringBuilder cp = new StringBuilder(classpath + ";");
                File libs = new File(new Libraries().getLibraryPath(version));

                for (File file : libs.listFiles()) {
                    cp.append(file.getAbsolutePath()).append(";");
                }
                String natives = getNatives();
                if (natives == null) {
                    System.out.println("Natives not found!");
                    return;
                }

                String[] args = new String[] {
                        System.getProperty("java.home") + "\\bin\\javaw.exe",
                        "-Djava.library.path=" + natives,
                        "-cp",
                        cp.toString(),
                        version.getManifestObject().get("mainClass").toString(),
                        "--username",
                        username,
                        "--gameDir",
                        MINECRAFT_DIR,
                        "--assetsDir",
                        MINECRAFT_DIR + "\\assets",
                        "--assetIndex",
                        version.getId().substring(0, version.getId().lastIndexOf(".")),
                        "--accessToken",
                        "0",
                        "--version",
                        version.getId(),
                };
                Process minecraft = new ProcessBuilder().command(args).redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectErrorStream(true).start();
                minecraft.waitFor();
                System.out.println("Minecraft with version " + version.getId() + " closed with exit code " + minecraft.exitValue());
            } else {
                File libDir = new File(new Libraries().getLibraryPath(version));
                libDir.mkdirs();
                new Libraries().downloadLibraries(version);
            }
        } else {
            System.out.println("Version " + version.getId() + " not found!");
        }
    }

    private String getNatives() {
        File binFolder = new File(MINECRAFT_DIR + "\\bin");
        return Arrays.stream(binFolder.listFiles()).filter(File::isDirectory).findFirst().map(File::getAbsolutePath).orElse(null);
    }
}