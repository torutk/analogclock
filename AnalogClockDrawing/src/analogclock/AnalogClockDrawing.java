/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package analogclock;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 * アナログ時計（長針、短針、秒針）を表示するプログラム。
 *
 * @author TAKAHASHI,Toru
 */
public class AnalogClockDrawing extends Application {

    private static final double UNIT_SIZE = 100d;
    private Rotate secondsHandRotation;
    private Rotate minuteHandRotation;
    private Rotate hourHandRotation;

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        root.getChildren().addAll(
                createDial(),
                createMinuteHand(),
                createHourHand(),
                createSecondsHand(),
                createTickMarks(),
                createCenter()
        );

        LocalTime time = LocalTime.now();
        Timeline secondsAnimation = createRotateTimeline(Duration.seconds(60), getSecondsAngle(time), secondsHandRotation);
        secondsAnimation.play();
        Timeline minuteAnimation = createRotateTimeline(Duration.minutes(60), getMinuteAgnel(time), minuteHandRotation);
        minuteAnimation.play();
        Timeline hourAnimation = createRotateTimeline(Duration.hours(12), getHourAngle(time), hourHandRotation);
        hourAnimation.play();

        Scene scene = new Scene(root, UNIT_SIZE * 2, UNIT_SIZE * 2);
        WritableImage snapshot = scene.snapshot(null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("clockSecondsHand.png"));
        } catch (IOException ex) {

        }
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    Timeline createRotateTimeline(Duration duration, int startAngle, Rotate rotate) {
        Timeline timeline = new Timeline();
        rotate.setAngle(startAngle);
        timeline.getKeyFrames().add(
                new KeyFrame(duration, new KeyValue(rotate.angleProperty(), startAngle + 360))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }

    // 時計の文字盤（背景）を作成する
    Node createDial() {
        RadialGradient gradient = new RadialGradient(
                0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0.8, Color.WHITE), new Stop(0.9, Color.BLACK), new Stop(0.95, Color.WHITE), new Stop(1.0, Color.BLACK)
        );
        Circle circle = new Circle(UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, gradient);
        return circle;
    }

    // 時計の文字盤（分刻み）を作成する
    Node createTickMarks() {
        Group tickMarkGroup = new Group();
        List<Node> tickMarks = IntStream.range(0, 60)
                .mapToObj(this::createTickMark)
                .collect(toList());
        tickMarkGroup.getChildren().addAll(tickMarks);
        return tickMarkGroup;
    }

    // 時計の文字盤の分刻みの1つを作成する
    Node createTickMark(int n) {
        Line line;
        if (n % 5 == 0) {
            line = new Line(UNIT_SIZE, UNIT_SIZE * 0.12, UNIT_SIZE, UNIT_SIZE * 0.2);
        } else {
            line = new Line(UNIT_SIZE, UNIT_SIZE * 0.15, UNIT_SIZE, UNIT_SIZE * 0.16);
        }
        line.getTransforms().add(new Rotate(360 / 60 * n, UNIT_SIZE, UNIT_SIZE));
        line.setStrokeWidth(2);
        return line;
    }

    // 時計の中心点を作成する
    Node createCenter() {
        return new Circle(UNIT_SIZE, UNIT_SIZE, UNIT_SIZE * 0.05, Color.BLACK);
    }

    // 時計の短針を作成する
    Node createHourHand() {
        hourHandRotation = new Rotate(0, UNIT_SIZE, UNIT_SIZE);
        Node hourHand = createHourOrMinuteHand(UNIT_SIZE * 0.4, Color.BLACK);
        hourHand.getTransforms().add(hourHandRotation);
        return hourHand;
    }

    // 時計の長針を作成する
    Node createMinuteHand() {
        minuteHandRotation = new Rotate(0, UNIT_SIZE, UNIT_SIZE);
        Node minuteHand = createHourOrMinuteHand(UNIT_SIZE * 0.2, Color.BLACK);
        minuteHand.getTransforms().add(minuteHandRotation);
        return minuteHand;
    }

    // 時計の秒針を作成する
    Node createSecondsHand() {
        Line line = new Line(UNIT_SIZE, UNIT_SIZE * 1.1, UNIT_SIZE, UNIT_SIZE * 0.2);
        secondsHandRotation = new Rotate(0, UNIT_SIZE, UNIT_SIZE);
        line.getTransforms().add(secondsHandRotation);
        return line;
    }

    // 時計の針を作成する
    Node createHourOrMinuteHand(double stretchRelativeToRim, Color color) {
        Path path = new Path(
                new MoveTo(UNIT_SIZE, UNIT_SIZE),
                new LineTo(UNIT_SIZE * 0.9, UNIT_SIZE * 0.9),
                new LineTo(UNIT_SIZE, stretchRelativeToRim),
                new LineTo(UNIT_SIZE * 1.1, UNIT_SIZE * 0.9),
                new LineTo(UNIT_SIZE, UNIT_SIZE)
        );
        path.setFill(color);
        path.setStroke(Color.TRANSPARENT);
        return path;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private int getSecondsAngle(LocalTime time) {
        return time.getSecond() * 360 / 60;
    }

    private int getMinuteAgnel(LocalTime time) {
        return (int) ((time.getMinute() + time.getSecond() / 60d) * 360 / 60);
    }

    private int getHourAngle(LocalTime time) {
        return (int) ((time.getHour() % 12 + time.getMinute() / 60d + time.getSecond() / 3600d) * 360 / 12);
    }

}
