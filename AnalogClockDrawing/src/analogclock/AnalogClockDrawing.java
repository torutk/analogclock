/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package analogclock;

import java.time.LocalTime;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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

/**
 * アナログ時計（長針、短針、秒針）を表示するプログラム。
 *
 * @author TAKAHASHI,Toru
 */
public class AnalogClockDrawing extends Application {

    private static final double UNIT_SIZE = 100d;

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();
        Node clockDial = createClockDial();
        Node hourHand = createHourHand();
        Node minuteHand = createMinuteHand();
        Node secondHand = createSecondHand();
        Node centerPoint = createCenter();

        root.getChildren().addAll(
                clockDial, hourHand, minuteHand, secondHand, centerPoint
        );

        LocalTime time = LocalTime.now();
        RotateTransition secondsTransition = createRotateTransition(Duration.seconds(60), secondHand, getSecondsAngle(time));
        secondsTransition.play();
        RotateTransition minuteTransition = createRotateTransition(Duration.minutes(60), minuteHand, getMinuteAgnel(time));
        minuteTransition.play();
        RotateTransition hourTransition = createRotateTransition(Duration.hours(12), hourHand, getHourAngle(time));
        hourTransition.play();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Clock");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    RotateTransition createRotateTransition(Duration duration, Node node, int startAngle) {
        RotateTransition rt = new RotateTransition(duration, node);
        rt.setFromAngle(startAngle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        return rt;
    }

    Node createClockDial() {
        Pane pane = new Pane();
        pane.getChildren().addAll(createCircle(), createTickMarks());
        return pane;
    }

    // 時計の文字盤（背景）を作成する
    Node createCircle() {
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
        Pane pane = new Pane();
        pane.setPrefSize(UNIT_SIZE * 2, UNIT_SIZE * 2);
        Node hourHand = createHourOrMinuteHand(UNIT_SIZE * 0.4, Color.BLACK);
        pane.getChildren().add(hourHand);
        return pane;
    }

    // 時計の長針を作成する
    Node createMinuteHand() {
        Pane pane = new Pane();
        pane.setPrefSize(UNIT_SIZE * 2, UNIT_SIZE * 2);
        Node minuteHand = createHourOrMinuteHand(UNIT_SIZE * 0.2, Color.BLACK);
        pane.getChildren().add(minuteHand);
        return pane;
    }

    // 時計の秒針を作成する
    Node createSecondHand() {
        Pane pane = new Pane();
        pane.setPrefSize(UNIT_SIZE * 2, UNIT_SIZE * 2);
        Line line = new Line(UNIT_SIZE, UNIT_SIZE * 1.1, UNIT_SIZE, UNIT_SIZE * 0.2);
        pane.getChildren().add(line);
        return pane;
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
