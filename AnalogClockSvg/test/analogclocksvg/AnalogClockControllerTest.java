/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package analogclocksvg;

import java.time.LocalTime;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.joining;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author TAKAHASHI,Toru
 */
public class AnalogClockControllerTest {

    AnalogClockController sut;

    public AnalogClockControllerTest() {
    }

    @Before
    public void setUp() {
        sut = new AnalogClockController();
    }

    @Test
    public void getMinuteAngleのテスト() {
        LocalTime time = LocalTime.of(0, 0, 30);
        assertThat(sut.getMinuteAgnel(time), is(3));
    }

    @Test
    public void 文字盤の分刻みSVGデータ生成() {
        String pathData = IntStream.range(0, 60)
                .mapToObj(this::createLine)
                .map(line -> String.format("M %.1f,%.1f L %.1f,%.1f", line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()))
                .collect(joining(" "));
        System.out.println(pathData);
    }

    /**
     * 文字盤の分個別のラインを生成する。
     * 
     * 5分置きに長い線を、それ以外の分は短い線を作成し、分に応じて回転させる。
     * 
     * @param minute 生成する対象の分
     * @return 指定した分に対応するライン
     */
    private Line createLine(int minute) {
        Rotate rot = new Rotate(minute * 6, 100, 100);
        Point2D p0 = rot.transform(100, minute % 5 == 0 ? 12 : 16);
        Point2D p1 = rot.transform(100, minute % 5 == 0 ? 32 : 18);
        Line line = new Line(p0.getX(), p0.getY(), p1.getX(), p1.getY());
        return line;
    }
}
