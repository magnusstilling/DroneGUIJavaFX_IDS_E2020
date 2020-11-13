package sample;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Controller{
    //Javafx fields
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

    //Udp fields
    private UdpPackageReceiver receiver;
    private DatagramSocket sender;
    int toPort = 6000;
    boolean running = false;

    //Drone fields
    double halfWindowWidth = 200;
    double halfWindowHeight = 100;
    double droneWidth = 20;
    double droneHeight = 20;
    double droneAngle = 0;
    int droneAltitude = 0;
    //DroneObject
    private Drone drone = new Drone(halfWindowWidth, halfWindowHeight, droneWidth, droneHeight, droneAngle, droneAltitude);



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
        receiver = new UdpPackageReceiver(loggedPackages, toPort, this);
        new Thread(receiver).start();

        //create udp sender
        try {
            sender = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        /*Graphics*/
        System.out.println("ready!");

        graphicsContext = canvasCanvas.getGraphicsContext2D();

        drone.drawDrone(canvasCanvas);

        Runnable target;

        System.out.println(sliderAltitude.getValue());
    }

    public void setColor(Button buttonColorChange, int isButtonActionLegal) {
        switch(isButtonActionLegal) {
            case 0:
                buttonColorChange.setStyle("-fx-background-color: red;");
                PauseTransition pauseTransition2 = new PauseTransition(Duration.millis(420));
                pauseTransition2.setOnFinished(event -> buttonColorChange.setStyle("-fx-background-color: grey;"));
                pauseTransition2.play();
                break;
            case 1:
                buttonColorChange.setStyle("-fx-background-color: green;");
                PauseTransition pauseTransition = new PauseTransition(Duration.millis(420));
                pauseTransition.setOnFinished(event -> buttonColorChange.setStyle("-fx-background-color: grey;"));
                pauseTransition.play();
                break;
        }


    }


    public void takeOff(MouseEvent mouseEvent) throws UnknownHostException {
        if(!drone.isFlying()) {
            drone.setAltitude(50);
            drone.setWidth(drone.getWidth());
            drone.setHeight(drone.getHeight());

            drone.drawDrone(canvasCanvas);
            drone.setFlying(true);
            sliderAltitude.setValue(drone.getAltitude());
            droneCommand("takeoff");
            setColor(buttonTakeOff, 1);
        } else {
            setColor(buttonTakeOff, 0);
            System.out.println("Cannot takeOff. Drone is already flying");
        }
    }

    public void moveLeft(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying()) {
            drone.setX(-10);
            drone.drawDrone(canvasCanvas);
            droneCommand("left 40");
            setColor(buttonMoveLeft, 1);
        } else{
            setColor(buttonMoveLeft, 0);
            System.out.println("Cannot moveLeft. Drone is not flying");
        }
    }

    public void moveRight(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying()) {
            drone.setX(10);
            drone.drawDrone(canvasCanvas);
            droneCommand("right 40");
            setColor(buttonMoveRight, 1);
        } else{
            setColor(buttonMoveRight, 0);
            System.out.println("Cannot moveRight. Drone is not flying");
        }
    }

    public void moveBack(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying()) {
            drone.setY(10);
            drone.drawDrone(canvasCanvas);
            droneCommand("back 40");
            setColor(buttonMoveBack, 1);
        } else{
            setColor(buttonMoveBack, 0);
            System.out.println("Cannot moveBack. Drone is not flying");
        }
    }

    public void moveForward(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying()) {
            drone.setY(-10);
            drone.drawDrone(canvasCanvas);
            droneCommand("forward 40");
            setColor(buttonMoveForward, 1);
        }else{
            setColor(buttonMoveForward, 0);
            System.out.println("Cannot moveForward. Drone is not flying");
        }
    }

    public void rotateRight(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying()) {
            drone.rotateDrone(canvasCanvas, 45, "right");
            droneCommand("cw 45");
            setColor(buttonRotateRight, 1);
            System.out.println("Drone rotated Right");
        } else{
            setColor(buttonRotateRight, 0);
            System.out.println("Cannot rotateRight. Drone is not flying");
        }
    }

    public void rotateLeft(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying()) {
            drone.rotateDrone(canvasCanvas,45,"left");
            droneCommand("ccw 45");
            setColor(buttonRotateLeft, 1);
            System.out.println("Drone rotated Left");
        } else{
            setColor(buttonRotateLeft, 0);
            System.out.println("Cannot rotateLeft. Drone is not flying");
        }
    }

    public void moveDown(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying() && (sliderAltitude.getValue() > 5)) {
            drone.setAltitude(-5);
            drone.drawDrone(canvasCanvas);
            droneCommand("down 40");
            sliderAltitude.setValue(drone.getAltitude());
            setColor(buttonDown, 1);
        } else{
            setColor(buttonDown, 0);
            //Logik der gør at den lander hvis altitude gøres mindre end 10?
            System.out.println("Cannot moveDown further. Drone is too close to the ground");
        }
    }

    public void moveUp(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying() && (sliderAltitude.getValue() < 100)) {
            drone.setAltitude(5);
            drone.drawDrone(canvasCanvas);
            droneCommand("up 40");
            sliderAltitude.setValue(drone.getAltitude());
            setColor(buttonUp, 1);
        } else{
            setColor(buttonUp, 0);
            System.out.println("WATCH OUT ICARUS!!! THE DRONE IS GETTING TOO CLOSE TO THE SUN!!!");
        }
    }

    public void flipLeft(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying() && (sliderAltitude.getValue() >= 50)){
            drone.setX(-50);
            drone.drawDrone(canvasCanvas);
            droneCommand("flip l");
            setColor(buttonFlipLeft, 1);
        } else{
            setColor(buttonFlipLeft, 0);
            System.out.println("Cannot flipLeft. Drone is too close to the ground");
        }
    }

    public void flipRight(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying() && (sliderAltitude.getValue() >= 50)){
            drone.setX(50);
            drone.drawDrone(canvasCanvas);
            droneCommand("flip r");
            setColor(buttonFlipRight, 1);
            //drone.flipDrone(canvasCanvas, "right");
        } else{
            setColor(buttonFlipRight, 0);
            System.out.println("Cannot flipRight. Drone is too close to the ground");
        }
    }

    public void land(ActionEvent actionEvent) throws UnknownHostException {
        if(drone.isFlying()) {
            drone.overWriteAltitude(0);
            drone.setWidth(-drone.getWidth()/2);
            drone.setHeight(-drone.getHeight()/2);
            drone.drawDrone(canvasCanvas);
            sliderAltitude.setValue(drone.getAltitude());
            drone.setFlying(false);
            droneCommand("land");
            setColor(buttonLand, 1);
        } else{
            setColor(buttonLand, 0);
            System.out.println("Cannot land. Drone is not flying");
        }
    }

    public void droneCommand(String command) throws UnknownHostException {
        UdpPackage takeOffPackage = new UdpPackage(command, InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000,4000);
        loggedPackages.addAll(takeOffPackage);
    }

    public void receiveMsg(String msg) {

        //takeOff, moveLeft, moveRight, moveBack, moveForward, rotateLeft, rotateRight, moveDown, moveUp, flipLeft, flipRight, land



        Event.fireEvent(buttonTakeOff, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
        System.out.println("fucking fantastisk");
        System.out.println(msg);
    }
}
