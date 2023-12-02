package tech.wolfdev.launcher.minecraft;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.wolfdev.launcher.Launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MinecraftVersions {
    private HashMap<String, Version> versions = new HashMap<>();
    public void loadVersions() {
        try {
            URL url = new URL("https://launchermeta.mojang.com/mc/game/version_manifest.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                inputStream.close();

                JSONParser parser = new JSONParser();
                JSONObject manifest = (JSONObject) parser.parse(result.toString());
                JSONArray versionsArray = (JSONArray) manifest.get("versions");

                for (Object obj : versionsArray) {
                    HashMap<String, MinecraftVersionType> types = new HashMap<>();
                    types.put("release", MinecraftVersionType.RELEASE);
                    types.put("snapshot", MinecraftVersionType.SNAPSHOT);
                    types.put("old_beta", MinecraftVersionType.BETA);
                    types.put("old_alpha", MinecraftVersionType.ALPHA);

                    JSONObject versionJson = (JSONObject) obj;
                    String id = (String) versionJson.get("id");
                    String releaseTime = (String) versionJson.get("releaseTime");
                    MinecraftVersionType type = (types.getOrDefault(versionJson.get("type"), MinecraftVersionType.UNKNOWN));
                    String urlString = (String) versionJson.get("url");
                    versions.put(id ,new Version(id, releaseTime, type, urlString));
                }

            } else {
                Launcher launcher = new Launcher();
                launcher.logger.error("Failed to load versions");
            }
        } catch (ParseException | IOException ex) {
            Launcher launcher = new Launcher();
            launcher.logger.error("Failed to load versions");
            ex.printStackTrace();
        }

    }

    public HashMap<String, Version> getVersions() {
        return versions;
    }


}
