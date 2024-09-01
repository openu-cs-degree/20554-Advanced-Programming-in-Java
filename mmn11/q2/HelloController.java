import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int X_MIN = 1;
    private static final int X_MAX = 13;
    private static/*final*/int Y_MIN = 0;
    private static/*final*/int Y_MAX = 100;
    private static final int X_TICK = 1;
    private static final int Y_TICK = 10;
    private static final int X_MARGIN = 50;
    private static final int Y_MARGIN = 70;
    private static final int AXIS_FONT_SIZE = 15;
    private static final int TEMPERATURE_FONT_SIZE = 12;
    private static final Color AXIS_COLOR = Color.BLACK;
    private static final Color GRID_COLOR = Color.LIGHTGRAY;
    private int currentYearIndex = 0;

    @FXML
    private Canvas myCanvas;
    private GraphicsContext myGraphicsContext;
    @FXML
    private Button myButton;
    @FXML
    private Label myLabel;

    @FXML
    protected void buttonClick() {
        incrementYear();
        drawGraph(myGraphicsContext);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentYearIndex = Data.years.length;
        myButton.setText("Let's start the show!");
        myLabel.setText("...");
        myGraphicsContext = myCanvas.getGraphicsContext2D();
        myGraphicsContext.setTextAlign(TextAlignment.CENTER);
        myGraphicsContext.setTextBaseline(VPos.CENTER);
    }

    private void drawGraph(GraphicsContext gc) {
        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
        drawGrid(gc);
        for (int i = 0; i < 12; ++i) {
            drawRect(gc, i);
        }
        drawAxes(gc);

    }
    private void drawAxes(GraphicsContext gc) {
        gc.setStroke(AXIS_COLOR);
        gc.setFill(AXIS_COLOR);
//        gc.setLineWidth(1);
        gc.setFont(Font.font(AXIS_FONT_SIZE));

        // draw lines
        gc.strokeLine(X_MARGIN, HEIGHT - Y_MARGIN, WIDTH - X_MARGIN, HEIGHT - Y_MARGIN);
        gc.strokeLine(X_MARGIN, HEIGHT - Y_MARGIN, X_MARGIN, Y_MARGIN);

        // draw x ticks
        for (int x = X_MIN; x < X_MAX; x += X_TICK) {
            double xPos = X_MARGIN + (x - X_MIN) * (WIDTH - 2 * X_MARGIN) / (double)(X_MAX - X_MIN);
            gc.strokeLine(xPos, HEIGHT - Y_MARGIN, xPos, HEIGHT - Y_MARGIN + 10);
            gc.fillText(Integer.toString(x), xPos, HEIGHT - Y_MARGIN + AXIS_FONT_SIZE * 1.5);
        }

        // draw y ticks
        for (int y = Y_MIN; y <= Y_MAX; y += Y_TICK) {
            double yPos = HEIGHT - Y_MARGIN - (y - Y_MIN) * (HEIGHT - 2 * Y_MARGIN) / (double)(Y_MAX - Y_MIN);
            gc.strokeLine(X_MARGIN, yPos, X_MARGIN - 10, yPos);
            gc.fillText(Integer.toString(y), X_MARGIN - AXIS_FONT_SIZE * 1.5, yPos);
        }

        // draw axes' arrows
        final int ARROW_SIZE = 10;
        gc.strokeLine(WIDTH - X_MARGIN             , HEIGHT - Y_MARGIN,              WIDTH - X_MARGIN + ARROW_SIZE * 2, HEIGHT - Y_MARGIN);
        gc.strokeLine(WIDTH - X_MARGIN + ARROW_SIZE, HEIGHT - Y_MARGIN - ARROW_SIZE, WIDTH - X_MARGIN + ARROW_SIZE * 2, HEIGHT - Y_MARGIN);
        gc.strokeLine(WIDTH - X_MARGIN + ARROW_SIZE, HEIGHT - Y_MARGIN + ARROW_SIZE, WIDTH - X_MARGIN + ARROW_SIZE * 2, HEIGHT - Y_MARGIN);
        gc.strokeLine(X_MARGIN, Y_MARGIN - ARROW_SIZE * 2, X_MARGIN, Y_MARGIN);
        gc.strokeLine(X_MARGIN - ARROW_SIZE, Y_MARGIN - ARROW_SIZE, X_MARGIN, Y_MARGIN - ARROW_SIZE * 2);
        gc.strokeLine(X_MARGIN + ARROW_SIZE, Y_MARGIN - ARROW_SIZE, X_MARGIN, Y_MARGIN - ARROW_SIZE * 2);

        // draw axes' labels
        gc.setFont(Font.font(TEMPERATURE_FONT_SIZE));
        gc.fillText("Month #", WIDTH - X_MARGIN + AXIS_FONT_SIZE, HEIGHT - Y_MARGIN + ARROW_SIZE * 1.5);
        gc.fillText("Temperature", X_MARGIN, Y_MARGIN - ARROW_SIZE * 3);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(GRID_COLOR);
//        gc.setLineWidth(1);
        for (int x = X_MIN + X_TICK; x < X_MAX; x += X_TICK) {
            double xPos = X_MARGIN + (x - X_MIN) * (WIDTH - 2 * X_MARGIN) / (double)(X_MAX - X_MIN);
            gc.strokeLine(xPos, HEIGHT - Y_MARGIN, xPos, Y_MARGIN);
        }
        for (int y = Y_MIN + Y_TICK; y < Y_MAX; y += Y_TICK) {
            double yPos = HEIGHT - Y_MARGIN - (y - Y_MIN) * (HEIGHT - 2 * Y_MARGIN) / (double)(Y_MAX - Y_MIN);
            gc.strokeLine(X_MARGIN, yPos, WIDTH - X_MARGIN, yPos);
        }
    }

    private void drawRect(GraphicsContext gc, int index) {
        int[] record = Data.records.get(Data.years[currentYearIndex]);
        int temperature = record[index];
        double rectHeight = ((HEIGHT - 2 * Y_MARGIN) / (double)(Y_MAX - Y_MIN)) * (temperature - Y_MIN);
        double rectWidth  =  (WIDTH  - 2 * X_MARGIN) / (double)(X_MAX - X_MIN)  - 2;
        double xPos =  X_MARGIN + (index + 1 - X_MIN) * (WIDTH - 2 * X_MARGIN) / (double)(X_MAX - X_MIN) + 1;
        double yPos = -Y_MARGIN + HEIGHT - rectHeight;
//        System.out.println("x: " + (int)xPos + ", y: " + (int)yPos + ", width: " + (int)rectWidth + ", height: " + rectHeight);
        gc.setFill(temperature == Arrays.stream(record).max().getAsInt() ? Color.RED :
                  (temperature == Arrays.stream(record).min().getAsInt() ? Color.BLUE : Color.GRAY));
        gc.fillRect(xPos,yPos,rectWidth,rectHeight);

        // draw temperature because why not
        gc.setFont(Font.font(TEMPERATURE_FONT_SIZE));
        gc.setFill(gc.getFill() == Color.BLUE ? Color.LIGHTGRAY : Color.BLACK);
        gc.fillText(temperature + "°C", xPos + rectWidth / 2, yPos + TEMPERATURE_FONT_SIZE * 2 / 3.0);
    }

    private void incrementYear() {
        currentYearIndex = getNextYearIndex();

        // update text on screen
        myButton.setText("Switch to " + Data.years[getNextYearIndex()]);
        myLabel.setText("Temperatures of " + Data.years[currentYearIndex] + ":");

        // update Y_MAX and Y_MIN
        int[] record = Data.records.get(Data.years[currentYearIndex]);
        Y_MAX = Arrays.stream(record).max().orElse(Y_MAX); Y_MAX += ( 10 - Y_MAX % 10); Y_MAX = Math.max(0, Math.min(100, Y_MAX));
        Y_MIN = Arrays.stream(record).min().orElse(Y_MIN); Y_MIN += (-10 - Y_MIN % 10); Y_MIN = Math.max(0, Math.min(100, Y_MIN));
    }
    private int getNextYearIndex() {
        return (currentYearIndex >= Data.years.length - 1) ? 0 : (currentYearIndex + 1);
    }

}