package tech.wolfdev.launcher.minecraft;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class VersionManager {
    public void downloadVersion(String version) {
        MinecraftVersions versions = new MinecraftVersions();
        versions.loadVersions();

        if (versions.getVersions().containsKey(version)) {
            String stringUrl = versions.getVersions().get(version).getClientDownloadUrl();
            String manifestUrl = versions.getVersions().get(version).getUrl();

            String path = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\.minecraft\\versions\\Wings_" + version + "\\";

            if(Files.exists(Paths.get(path))) {
                System.out.println("Version already exists");
            } else {
                try {
                    Files.createDirectory(Paths.get(path));


                    URL url = new URL(stringUrl);
                    URL manifest = new URL(manifestUrl);
                    URLConnection manifestConnection = manifest.openConnection();
                    URLConnection connection = url.openConnection();
                    InputStream manifestStream = manifestConnection.getInputStream();
                    InputStream inputStream = connection.getInputStream();
                    Files.copy(manifestStream, Paths.get(path + version + ".json"), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(inputStream, Paths.get(path + "\\"+  version + ".jar"), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Version " + version + " downloaded successfully!");
                    System.out.println("Downloaded items:\n " + version + ".json (100%)\n " + version + ".jar (100%)");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("Version " + version + " not found!");
        }

    }
}
