/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package analogclocksvg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author TAKAHASHI,Toru
 */
public class AnalogClockSvg extends Application {

    private double dragStartX;
    private double dragStartY;
    private ContextMenu popup = new ContextMenu();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AnalogClock.fxml"));
        Scene scene = new Scene(root, 200, 200, Color.TRANSPARENT);
        // マウスのドラッグ操作でウィンドウを移動
        scene.setOnMousePressed(e -> {
            dragStartX = e.getSceneX();
            dragStartY = e.getSceneY();
        });
        scene.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - dragStartX);
            stage.setY(e.getScreenY() - dragStartY);
        });
        // マウスのホイール操作でウィンドウサイズを変更
        scene.setOnScroll(e -> {
            double scaleFactor = e.getDeltaY() > 0 ? 1.1 : 0.9;
            root.setScaleX(root.getScaleX() * scaleFactor);
            root.setScaleY(root.getScaleY() * scaleFactor);
        });
        // 右クリックでポップアップメニュー
        MenuItem exitItem = new MenuItem("exit");
        exitItem.setOnAction(e -> Platform.exit());
        popup.getItems().add(exitItem);
        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                popup.show(stage, e.getScreenX(), e.getScreenY());
            }
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
