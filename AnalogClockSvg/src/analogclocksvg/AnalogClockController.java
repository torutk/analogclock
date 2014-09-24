/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package analogclocksvg;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Rotate;
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

        Rotate secondHandRotate = new Rotate(0, 100, 100);
        secondHand.getTransforms().add(secondHandRotate);
        Timeline secondAnimation = createRotateTimeline(Duration.seconds(60), getSecondAngle(now), secondHandRotate);

        Rotate minuteHandRotate = new Rotate(0, 100, 100);
        minuteHand.getTransforms().add(minuteHandRotate);
        Timeline minuteAnimation = createRotateTimeline(Duration.minutes(60), getMinuteAgnel(now), minuteHandRotate);

        Rotate hourHandRotate = new Rotate(0, 100, 100);
        hourHand.getTransforms().add(hourHandRotate);
        Timeline hourAnimation = createRotateTimeline(Duration.hours(12), getHourAngle(now), hourHandRotate);

        secondAnimation.play();
        minuteAnimation.play();
        hourAnimation.play();
    }

    private Timeline createRotateTimeline(Duration duration, int startAngle, Rotate rotate) {
        Timeline timeline = new Timeline();
        rotate.setAngle(startAngle);
        timeline.getKeyFrames().add(new KeyFrame(duration, new KeyValue(rotate.angleProperty(), startAngle + 360)));
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
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
