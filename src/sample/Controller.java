package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;

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

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.strokeOval(50,50,50,50);
    }

    public void initialize() {
        // runs when application GUI is ready
        System.out.println("ready!");

        //graphicsContext canvas = canvasCanvas.getGraphicsContext2D();

    }
}
