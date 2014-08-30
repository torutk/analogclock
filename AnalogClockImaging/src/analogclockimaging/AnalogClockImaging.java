/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package analogclockimaging;

import java.time.LocalTime;
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

        RotateTransition secondsHandTransition = createRotateTransition(Duration.seconds(60), secondsHand, getAngleOfSeconds(LocalTime.now()));
        secondsHandTransition.play();

        RotateTransition minuteTransition = createRotateTransition(Duration.minutes(60), minuteHand, getAngleOfMinute(LocalTime.now()));
        minuteTransition.play();

        RotateTransition hourTranslation = createRotateTransition(Duration.hours(12), hourHand, getAngleOfHour(LocalTime.now()));
        hourTranslation.play();

        root.getChildren().addAll(
                clockDial, hourHand, minuteHand, secondsHand, centerPoint
        );
        Scene scene = new Scene(root);

        primaryStage.setTitle("Clock");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 360度回転を繰り返すアニメーションの設定。
     *
     * @param duration 1回転するのに要する時間
     * @param node 回転するノード
     * @param startAngle 回転開始角度
     * @return 指定下パラメータで初期化したRotateTransitionインスタンス
     */
    private RotateTransition createRotateTransition(Duration duration, Node node, double startAngle) {
        RotateTransition rt = new RotateTransition(duration, node);
        rt.setFromAngle(startAngle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        return rt;
    }

    /**
     * 引数で指定した時刻における短針（時）の角度（0時を0度とした時計回り）
     *
     * @param time 時刻
     * @return 指定した時刻における短針の角度
     */
    private static double getAngleOfHour(LocalTime time) {
        return (time.getHour() % 12 + time.getMinute() / 60d + time.getSecond() / (60d * 60d)) * 360 / 12;
    }

    /**
     * 引数で指定した時刻における長針（分）の角度（0分を0度とした時計回り）。
     *
     * @param time 時刻
     * @return 指定した時刻における長針の角度
     */
    private static double getAngleOfMinute(LocalTime time) {
        return (time.getMinute() + time.getSecond() / 60d) * 360 / 60;
    }

    /**
     * 引数で指定した時刻における秒針（秒）の角度（0秒を0度とした時計回り）
     *
     * @param time 時刻
     * @return 指定した時刻における秒針の角度
     */
    private static double getAngleOfSeconds(LocalTime time) {
        return time.getSecond() * 360 / 60;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
