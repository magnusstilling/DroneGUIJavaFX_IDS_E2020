package sample;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Controller{
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
    public TableView tableViewColumns;
    private GraphicsContext graphicsContext;

    private ObservableList<UdpPackage> loggedPackages = FXCollections.observableArrayList();

    private UdpPackageReceiver receiver;
    private ButtonPresser buttonPresser;
    private DatagramSocket sender;
    private UdpPackage packet;

    double x, y, w = 50, h = 50, altitude = 0;
    int toPort = 6000;
    boolean running = false;

    private Drone drone = new Drone(x,y,50,50, 0, 0);
    public void initialize() throws UnknownHostException {
        // runs when application GUI is ready
        /*UDP*/
        this.running = true;
        //add list of items to table
        tableViewColumns.setItems(loggedPackages);

        //set columns content

        tableColumnASCII.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("dataAsString")
        );
        tableColumnHEX.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("dataAsHex")
        );
        tableColumnFromPort.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, Integer>("fromPort")
        );
        tableColumnFromIP.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("fromIp")
        );
        tableColumnToPort.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, Integer>("toPort")
        );
        tableColumnToIP.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("toIp")
        );

        //add udp server/receiver
        receiver = new UdpPackageReceiver(loggedPackages, toPort);
        new Thread(receiver).start();
        new Thread(buttonPresser).start();

        //create udp sender
        try {
            sender = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        /*Graphics*/
        System.out.println("ready!");

        graphicsContext = canvasCanvas.getGraphicsContext2D();

        x= canvasCanvas.getWidth()/2;
        y= canvasCanvas.getHeight()/2;

        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);

        Runnable target;

        System.out.println(sliderAltitude.getValue());
    }

    public void setColor(Button buttonColorChange) {
        buttonColorChange.setStyle("-fx-background-color: cyan;");
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(420));
        pauseTransition.setOnFinished(event -> buttonColorChange.setStyle("-fx-background-color: grey;"));
        pauseTransition.play();
    }


    public void takeOff(MouseEvent mouseEvent) throws UnknownHostException {
        drone.setAltitude(50);
        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);
        sliderAltitude.setValue(drone.getAltitude());
        droneCommand("takeoff");
        setColor(buttonTakeOff);
    }

    public void moveLeft(ActionEvent actionEvent) throws UnknownHostException {
        drone.setX(-10);
        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);
        droneCommand("left 40");
        setColor(buttonMoveLeft);
    }

    public void moveRight(ActionEvent actionEvent) throws UnknownHostException {
        drone.setX(10);
        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);
        droneCommand("right 40");
        setColor(buttonMoveRight);
    }

    public void moveBack(ActionEvent actionEvent) throws UnknownHostException {
        drone.setY(10);
        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);
        droneCommand("back 40");
        setColor(buttonMoveBack);
    }

    public void moveForward(ActionEvent actionEvent) throws UnknownHostException {
        drone.setY(-10);
        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);
        droneCommand("forward 40");
        setColor(buttonMoveForward);
    }

    public void rotateRight(ActionEvent actionEvent) throws UnknownHostException {
        drone.rotateDrone(graphicsContext,45,"R");
        droneCommand("cw 45");
        setColor(buttonRotateRight);
    }

    public void rotateLeft(ActionEvent actionEvent) throws UnknownHostException {
        drone.rotateDrone(graphicsContext,45,"L");
        droneCommand("ccw 45");
        setColor(buttonRotateLeft);
    }

    public void moveDown(ActionEvent actionEvent) throws UnknownHostException {
        drone.setAltitude(-5);
        sliderAltitude.setValue(drone.getAltitude());
        droneCommand("down 40");
        setColor(buttonDown);
    }

    public void moveUp(ActionEvent actionEvent) throws UnknownHostException {
        drone.setAltitude(5);
        sliderAltitude.setValue(drone.getAltitude());
        droneCommand("up 40");
        setColor(buttonUp);
    }

    public void flipLeft(ActionEvent actionEvent) throws UnknownHostException {
        drone.setX(-50);
        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);
        droneCommand("flip l");
        setColor(buttonFlipLeft);
    }

    public void flipRight(ActionEvent actionEvent) throws UnknownHostException{
        drone.setX(50);
        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);
        droneCommand("flip r");
        setColor(buttonFlipRight);
    }

    public void land(ActionEvent actionEvent) throws UnknownHostException {
        drone.setAltitude(-drone.getAltitude());
        drone.drawDrone(x,y,w,h,graphicsContext,canvasCanvas);
        sliderAltitude.setValue(drone.getAltitude());
        droneCommand("land");
        setColor(buttonLand);
    }

    public void droneCommand(String command) throws UnknownHostException {
        UdpPackage takeOffPackage = new UdpPackage(command, InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000,4000);
        loggedPackages.addAll(takeOffPackage);
    }

    public void buttonFire(String data){
        

    }
}
