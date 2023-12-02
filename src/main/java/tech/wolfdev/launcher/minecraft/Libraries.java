package tech.wolfdev.launcher.minecraft;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Libraries {
    String GAME_DIRECTORY = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\.minecraft";
    public void downloadLibraries(Version version) throws IOException {

            JSONArray libraries = (JSONArray) version.getManifestObject().get("libraries");;
            for (int i = 0; i < libraries.size(); i++) {
                JSONObject library = (JSONObject) libraries.get(i);
                String name = library.get("name").toString();
                String onlyName = name.split(":")[1];
                JSONObject downloads = (JSONObject) library.get("downloads");
                JSONObject artifactObj = (JSONObject) downloads.get("artifact");
                String url = artifactObj.get("url").toString();

                URL u = new URL(url);
                URLConnection connection = u.openConnection();
                InputStream artifact = connection.getInputStream();
                Files.copy(artifact, Paths.get(getLibraryPath(version) + "\\" + onlyName + ".jar"), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Library " + onlyName + " (100%) downloaded successfully from server!");

            }
    }

    public boolean hasLauncherLibraries(Version version) {
        File launcherLibraries = new File(GAME_DIRECTORY + "\\libraries\\WingsLauncher\\" + version.getId());
        return launcherLibraries.exists();
    }
    public String getLibraryPath(Version version) {
        return GAME_DIRECTORY + "\\libraries\\WingsLauncher\\" + version.getId();
    }

}
