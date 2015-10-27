/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package analogclocksvg;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 *
 * @author TAKAHASHI,Toru
 */
public class AnalogClockController implements Initializable {

    @FXML
    private SVGPath hourHand;
    @FXML
    private SVGPath minuteHand;
    @FXML
    private SVGPath secondHand;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalTime now = LocalTime.now();
        
        RotateTransition secondTransition = createRotateTransition(
                Duration.seconds(60), secondHand, getSecondAngle(now), 360d, 100d, 100d
        );
        secondTransition.play();
        
        RotateTransition minuteTransition = createRotateTransition(
                Duration.minutes(60), minuteHand, getMinuteAgnel(now), 360d, 100d, 100d
        );
        minuteTransition.play();

        RotateTransition hourTransition = createRotateTransition(
                Duration.hours(12), hourHand, getHourAngle(now), 360d, 100d, 100d
        );
        hourTransition.play();
    }

    /**
     * node の回転中心を、(pivotX, pivotY)とし、回転時間をduration、回転開始角度をfromAngle、回転終了角度をbyAngleとした
     * RotateTransitionを作成する。
     * <p>
     * nodeのデフォルトの回転中心は BoundsInLocalの中心なので、（要確認）
     * 
     * @param duration 1回の回転に要する時間
     * @param node 回転対象ノード
     * @param fromAngle 回転開始角度
     * @param byAngle 回転終了角度
     * @param pivotX 回転中心のX座標
     * @param pivotY 回転中心のY座標
     * @return 指定した回転中心位置、ノード、回転開始・終了角度で回転するRotateTransitionインスタンス
     */
    private RotateTransition createRotateTransition(Duration duration, Node node, double fromAngle, double byAngle, double pivotX, double pivotY) {
        Bounds bounds = node.getBoundsInLocal();
        double defaultPivotX = (bounds.getMinX() + bounds.getMaxX()) / 2d;
        double defaultPivotY = (bounds.getMinY() + bounds.getMaxY()) / 2d;
        double translateX = defaultPivotX - pivotX;
        double translateY = defaultPivotY - pivotY;
        node.getTransforms().add(new Translate(translateX, translateY));
        node.setTranslateX(-translateX);
        node.setTranslateY(-translateY);
        RotateTransition rt = new RotateTransition(duration, node);
        rt.setFromAngle(fromAngle);
        rt.setByAngle(byAngle);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        return rt;
    }
    
    int getSecondAngle(LocalTime time) {
        return time.getSecond() * 360 / 60;
    }

    int getMinuteAgnel(LocalTime time) {
        return (int) ((time.getMinute() + time.getSecond() / 60d) * 360 / 60);
    }

    int getHourAngle(LocalTime time) {
        return (int) ((time.getHour() % 12 + time.getMinute() / 60d + time.getSecond() / 3600d) * 360 / 12);
    }
}
