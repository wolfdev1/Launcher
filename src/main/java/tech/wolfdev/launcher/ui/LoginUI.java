package tech.wolfdev.launcher.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tech.wolfdev.launcher.Launcher;
import tech.wolfdev.launcher.minecraft.GameLauncher;
import tech.wolfdev.launcher.minecraft.MinecraftVersionType;
import tech.wolfdev.launcher.minecraft.MinecraftVersions;
import tech.wolfdev.launcher.minecraft.Version;

import java.io.*;
import java.util.Arrays;

public class LoginUI extends Application {

    private static final String MINECRAFT_DIR = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\.minecraft";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("WingsLauncher (Initial window)");
        primaryStage.getIcons().add(new javafx.scene.image.Image("https://static.wikia.nocookie.net/pixelpeople/images/c/c1/Phoenix.png"));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));





        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#d66e3a")), new Stop(1, Color.web("#d6983a")));
        Text scenetitle = new Text("Welcome");
        scenetitle.setFill(gradient);
        scenetitle.setFont(Font.font("Minecraft", FontWeight.MEDIUM, 20));

        grid.add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("Minecraft Username:");
        userName.setStyle("-fx-text-fill: white;");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);




        Button btn = new Button("Let's play");


        Label version = new Label("Version:");
        version.setStyle("-fx-text-fill: white;");
        grid.add(version, 0, 2);
        MinecraftVersions versions = new MinecraftVersions();
        versions.loadVersions();

        File[] filteredFiles = new File(MINECRAFT_DIR + "\\versions").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("Wings_");
            }
        });

        String[] arr = Arrays.stream(filteredFiles)
                .map(File::getName)
                .map(s -> {
                    int lastUnderscoreIndex = s.lastIndexOf("_");
                    if (lastUnderscoreIndex == -1) {
                        return s;
                    }
                    return s.substring(lastUnderscoreIndex + 1);
                })
                .toArray(String[]::new);

        ChoiceBox<String> versionSelector = new ChoiceBox<>();
        versionSelector.getItems().addAll(arr);
        versionSelector.setValue(arr[0]);
        grid.add(versionSelector, 1, 2);


        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    new GameLauncher().launch(versions.getVersions().get(versionSelector.getValue()), userTextField.getText());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException | InterruptedException e) {
                    System.out.println("Error while launching game");
                    throw new RuntimeException(e);
                }
            }
        });

        btn.setStyle("-fx-background-color: "+gradient.toString().replace("0x", "#")+";" +
                " -fx-text-fill: white; -fx-font-size: 12pt; -fx-border-radius: 20;");
        grid.setStyle("-fx-background-color: #0d0d0c;");

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    public static void login() {
        launch();
    }
}