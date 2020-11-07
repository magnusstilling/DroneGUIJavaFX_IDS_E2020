package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

public class Controller {
    public Button buttonTakeOff;
    public Button buttonFlipLeft;
    public Button buttonUp;
    public Button buttonMoveLeft;
    public Button buttonMoveRight;
    public Button buttonMoveForward;
    public Button buttonLand;
    public Button buttonDown;
    public Button buttonRotateLeft;
    public Button buttonRotateRight;
    public Button buttonMoveBack;
    public Button buttonFlipRight;
    public TableColumn tableColumnFromIP;
    public TableColumn tableColumnFromPort;
    public TableColumn tableColumnToIP;
    public TableColumn tableColumnToPort;
    public TableColumn tableColumnASCII;
    public TableColumn tableColumnHEX;
    public Slider sliderAltitude;
    public Canvas canvasCanvas;
    private GraphicsContext graphicsContext;

    /*
    public void draw(GraphicsContext graphicsContext) {
        GraphicsContext canvas = canvasCanvas.getGraphicsContext2D();
        graphicsContext.strokeOval(50,50,50,50);
    }

     */

    int x = 50;
    int y = 50;
    int w = 50;
    int h = 50;
    int altitude = 0;

    public void initialize() {
        // runs when application GUI is ready
        System.out.println("ready!");

        graphicsContext = canvasCanvas.getGraphicsContext2D();

        graphicsContext.fillRect(x, y, w, h);

        Runnable target; //hvad gør den!?

        System.out.println(sliderAltitude.getValue());

    }

    public void takeOff(MouseEvent mouseEvent) {
        x -= 5;
        y += 5;
        w += 10;
        h += 10;
        altitude = 5;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
        sliderAltitude.setValue(altitude);
    }

    public void moveLeft(MouseEvent mouseEvent) {
        x -= 10;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
    }

    public void moveRight(MouseEvent mouseEvent) {
        x += 10;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
    }

    public void moveBack(MouseEvent mouseEvent) {
        y += 10;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
    }

    public void moveForward(MouseEvent mouseEvent) {
        y -= 10;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
    }

    public void rotateRight(MouseEvent mouseEvent) {
    }

    public void rotateLeft(MouseEvent mouseEvent) {
    }

    public void moveDown(MouseEvent mouseEvent) {
        altitude -= 5;
        sliderAltitude.setValue(altitude);
    }

    public void flipLeft(MouseEvent mouseEvent) {
    }

    public void moveUp(MouseEvent mouseEvent) {
        altitude += 5;
        sliderAltitude.setValue(altitude);
    }

    public void land(MouseEvent mouseEvent) {
        x += 5;
        y -= 5;
        w -= 10;
        h -= 10;
        altitude = 0;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
        sliderAltitude.setValue(altitude);
    }

}
