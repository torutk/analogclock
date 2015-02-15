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
    private boolean isScrollStarted;
    private ContextMenu popup = new ContextMenu();
    private Parent root;
    
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("AnalogClock.fxml"));
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
        // 時計のサイズを変更する
        // マウスのホイール操作でウィンドウサイズを変更
        scene.setOnScrollStarted(e -> isScrollStarted = true);
        scene.setOnScrollFinished(e ->isScrollStarted = false);
        scene.setOnScroll(e -> {
            if (isScrollStarted) return;
            double zoomFactor = e.getDeltaY() > 0 ? 1.1 : 0.9;
            zoom(zoomFactor);
        });
        // タッチパネルのピンチ操作でウィンドウサイズを変更
        scene.setOnZoom(e -> {
            zoom(e.getZoomFactor());
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

    private void zoom(double factor) {
        root.setScaleX(root.getScaleX() * factor);
        root.setScaleY(root.getScaleY() * factor);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
