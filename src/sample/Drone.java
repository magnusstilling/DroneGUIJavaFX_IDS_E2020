package sample;

import com.sun.prism.Graphics;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

public class Drone {

    private double x, y, width, height, angle;
    private int altitude;

    public Drone(double x, double y, double width, double height, double angle, int altitude) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.altitude = altitude;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x += x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y += y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle += angle;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude += altitude;
    }

    public void drawDrone(double x, double y, double width, double height, GraphicsContext graphicsContext, Canvas canvasCanvas){
        graphicsContext = canvasCanvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(this.x, this.y, width, height);
    }

    public void rotateDrone(GraphicsContext graphicsContext, int angle, String dir){
        if(dir.equalsIgnoreCase("L")){
            angle = angle *-1;
        }
        Rotate rotate = new Rotate();
        rotate.setAngle(angle);
        rotate.setPivotX(this.x);
        rotate.setPivotY(this.y);
        drawDrone(x,y,width,height,graphicsContext, graphicsContext.getCanvas());
    }

    public void flipDrone(){

    }

}
