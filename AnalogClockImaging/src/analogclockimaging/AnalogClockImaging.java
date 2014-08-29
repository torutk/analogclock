/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package analogclockimaging;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * アナログ時計（画像ファイルで実現）
 *
 * @author TAKAHASHI,Toru
 */
public class AnalogClockImaging extends Application {

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();
        ImageView clockDial = new ImageView(new Image("file:clockDial.png"));
        ImageView hourHand = new ImageView(new Image("file:clockHourHand.png"));
        ImageView minuteHand = new ImageView(new Image("file:clockMinuteHand.png"));
        ImageView secondsHand = new ImageView(new Image("file:clockSecondsHand.png"));
        ImageView centerPoint = new ImageView(new Image("file:clockCenterPoint.png"));

        RotateTransition secondsHandTransition = createRotateTransition(Duration.seconds(60), secondsHand, 360);
        secondsHandTransition.play();

        RotateTransition minuteTransition = createRotateTransition(Duration.minutes(60), minuteHand, 360);
        minuteTransition.play();

        RotateTransition hourTranslation = createRotateTransition(Duration.hours(12), hourHand, 360);
        hourTranslation.play();

        root.getChildren().addAll(
                clockDial, hourHand, minuteHand, secondsHand, centerPoint
        );
        Scene scene = new Scene(root);

        primaryStage.setTitle("Clock");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private RotateTransition createRotateTransition(Duration duration, Node node, int angle) {
        RotateTransition rt = new RotateTransition(duration, node);
        rt.setByAngle(angle);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        return rt;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
