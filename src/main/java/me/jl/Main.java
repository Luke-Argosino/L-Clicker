package me.jl;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.jl.Listeners.KeyListener;
import me.jl.Utils.AutoClicker;
import me.jl.Utils.Destruct;
import org.jnativehook.GlobalScreen;

import java.io.File;

public class Main extends Application implements EventHandler<ActionEvent> {

    private Button selfDestruct;
    private Button randomizeCPS;
    private TextField minCPS;
    private TextField maxCPS;
    public TextField keybind;
    private CheckBox windowCheck;
    private CheckBox spikeCheck;
    private TextField spikeStrength;

    public static double minimumCPS;
    public static double maximumCPS;
    public volatile static boolean windowOnly = false;

    public volatile static Thread ac = new Thread(new AutoClicker());

    private static Stage stage = new Stage();

    public static void main(String[] args) {

        launch(args);

    }

    public static Stage getStage() {

        return stage;

    }

    @Override
    public void start(Stage primaryStage) {

        ac.start();

        stage = primaryStage;

        stage.setTitle("L-Clicker");

        Tooltip selfTip = new Tooltip("Deletes the autoclicker");

        selfDestruct = new Button("Self Destruct");
        selfDestruct.setTooltip(selfTip);
        selfDestruct.setOnAction(this);

        Tooltip randomTip = new Tooltip("Completely randomizes CPS");

        randomizeCPS = new Button("Randomize CPS");
        randomizeCPS.getStyleClass().add("buttonblue");
        randomizeCPS.setTooltip(randomTip);
        randomizeCPS.setOnAction(this);

        AutoClicker updateCPS = new AutoClicker();

        double defaultMin = (Math.random()*6 + 4.5);
        String sMin = defaultMin + "";
        minCPS = new TextField(sMin);
        minCPS.getStyleClass().add("text-input");
        minCPS.textProperty().addListener((observable, oldValue, newValue) -> {

            double min = Double.parseDouble(minCPS.getText());

            updateCPS.updateMinCPS(min);

        });
        Label minLabel = new Label("Min CPS");

        double defaultMax = (Math.random()*4.5 + 10);
        String sMax = defaultMax + "";
        maxCPS = new TextField(sMax);
        maxCPS.getStyleClass().add("text-input");
        maxCPS.textProperty().addListener((observable, oldValue, newValue) -> {

            double max = Double.parseDouble(maxCPS.getText());

            updateCPS.updateMaxCPS(max);

        });
        Label maxLabel = new Label("Max CPS");

        spikeStrength = new TextField("0");
        spikeStrength.getStyleClass().add("text-input");
        spikeStrength.textProperty().addListener((observable, oldValue, newValue) -> {

            double spikeNum = Double.parseDouble(spikeStrength.getText());

            updateCPS.spikeStrength = spikeNum;

        });
        Label spikeSLabel = new Label("Spike Strength");

        keybind = new TextField("P");
        keybind.getStyleClass().add("text-input");
        keybind.textProperty().addListener((observable, oldValue, newValue) -> {

            updateCPS.keyBind = false;
            KeyListener update = new KeyListener();
            update.updateKey(newValue.toUpperCase());

        });
        Label keyLabel = new Label("Keybind");

        Tooltip spikeTip = new Tooltip("Makes the CPS spike up or down randomly");

        spikeCheck = new CheckBox();
        spikeCheck.getStyleClass().add("combo-box");
        spikeCheck.setTooltip(spikeTip);
        spikeCheck.selectedProperty().addListener(observable -> {

            updateCPS.spikes ^= true;
            //System.out.println("Spikes " + updateCPS.spikes);

        });
        Label spikeLabel = new Label("Spikes?");

        Tooltip windowTip = new Tooltip("Makes it only click in Minecraft");

        windowCheck = new CheckBox();
        windowCheck.getStyleClass().add("combo-box");
        windowCheck.setTooltip(windowTip);
        windowCheck.selectedProperty().addListener(observable -> {

            windowOnly ^= true;
            //System.out.println("Window Only " + windowOnly);

        });
        Label winLabel = new Label("Window Only?");

        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(12);
        grid.setPadding(new Insets(15, 15, 15, 15));

        GridPane.setConstraints(maxLabel, 0, 1);
        GridPane.setConstraints(maxCPS, 1, 1);
        GridPane.setConstraints(minLabel, 0, 0);
        GridPane.setConstraints(minCPS, 1, 0);
        GridPane.setConstraints(spikeLabel, 0, 2);
        GridPane.setConstraints(spikeCheck, 1, 2);
        GridPane.setConstraints(spikeStrength, 1, 3);
        GridPane.setConstraints(spikeSLabel, 0, 3);
        GridPane.setConstraints(winLabel, 0, 5);
        GridPane.setConstraints(windowCheck, 1, 5);
        GridPane.setConstraints(keyLabel, 0, 4);
        GridPane.setConstraints(keybind, 1, 4);
        GridPane.setConstraints(randomizeCPS, 1, 6);
        GridPane.setConstraints(selfDestruct, 1, 7);

        grid.getChildren().addAll(selfDestruct, minCPS, maxCPS, minLabel, maxLabel, randomizeCPS, windowCheck, winLabel, keyLabel, keybind, spikeLabel, spikeCheck, spikeStrength, spikeSLabel);


        Scene scene = new Scene(grid, 300, 275);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent t) {

            System.runFinalization();
            System.gc();
            System.runFinalization();
            System.gc();
            Platform.exit();
            System.exit(0);

        }
        });

    }

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == selfDestruct) {

            Destruct  selfD = new Destruct();

            selfD.Destroy();

        }

        else if (event.getSource() == randomizeCPS) {

            double randomOne = 7.3498539;
            double randomTwo = 14.4239721;

            double difference = randomTwo - randomOne;

            double newCPS = ((Math.random()*difference) + randomOne);
            double newCPSTwo = ((Math.random()*difference) + randomOne);

            while (Math.abs(newCPS - newCPSTwo) < 3.1) {

                if (newCPS > newCPSTwo) {

                    newCPSTwo -= 2;

                } else {

                    newCPS += 2;

                }

            }

            double min;
            double max;

            if (newCPS > newCPSTwo) {

                max = newCPS;
                min = newCPSTwo;

            } else {

                min = newCPS;
                max = newCPSTwo;

            }

            minCPS.setText(Double.toString(min));
            maxCPS.setText(Double.toString(max));

        }

    }

}
