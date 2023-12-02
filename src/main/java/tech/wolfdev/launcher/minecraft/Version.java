package tech.wolfdev.launcher.minecraft;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Version {
    private String id;
    private String releaseTime;
    private MinecraftVersionType type;
    private String url;

    public Version(String id, String releaseTime, MinecraftVersionType type, String url) {
        this.id = id;
        this.releaseTime = releaseTime;
        this.type = type;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public MinecraftVersionType getType() {
        return type;
    }

    public void setType(MinecraftVersionType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientDownloadUrl() {
        try {
            URL url = new URL(getUrl());
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
                JSONObject downloads = (JSONObject) manifest.get("downloads");
                JSONObject client = (JSONObject) downloads.get("client");
                return (String) client.get("url");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public JSONObject getManifestObject() {
        try {
            URL url = new URL(getUrl());
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

                return (JSONObject) parser.parse(result.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
