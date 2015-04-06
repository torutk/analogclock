/*
 * The MIT License
 *
 * Copyright 2014 TAKAHASHI,Toru.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package analogclock;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 時計のパーツを描画してファイルに出力する機能を持つ
 *
 * @author TAKAHASHI,Toru
 */
public class AnalogClockDrawingTest {

    AnalogClockDrawing sut;
    Group root;
    Scene scene;

    public AnalogClockDrawingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        new JFXPanel();
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        sut = new AnalogClockDrawing();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testStart() {
    }

    @Test
    public void testCreateRotateTimeline() {
    }

    @Test
    public void testCreateClockDial() throws IOException {
        createSceneAndCapture(sut.createClockDial(), "clockdial.png");
    }

    @Test
    public void testCreateCircle() throws IOException {
        createSceneAndCapture(sut.createCircle(), "clockCircle.png");
    }

    @Test
    public void testCreateTickMarks() {
        createSceneAndCapture(sut.createTickMarks(), "clockTickMarks.png");
    }

    @Test
    public void testCreateTickMark() {

    }

    @Test
    public void testCreateCenter() {
        createSceneAndCapture(sut.createCenter(), "clockCenter.png");
    }

    @Test
    public void testCreateHourHand() {
        createSceneAndCapture(sut.createHourHand(), "clockHourHand.png");
    }

    @Test
    public void testCreateMinuteHand() {
        createSceneAndCapture(sut.createMinuteHand(), "clockMinuteHand.png");
    }

    @Test
    public void testCreateSecondsHand() {
        createSceneAndCapture(sut.createSecondHand(), "clockSecondsHand.png");
    }

    @Test
    public void testCreateHourOrMinuteHand() {
    }

    @Test
    public void testMain() {
    }

    private void createSceneAndCapture(Node node, String fileName) {
        Platform.runLater(() -> {
            root = new Group();
            root.getChildren().add(node);
            scene = new Scene(root, 200, 200);
            captureScene(scene, fileName);
        });
    }

    private void captureScene(Scene scene, String fileName) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(scene.snapshot(null), null), "png", new File(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
