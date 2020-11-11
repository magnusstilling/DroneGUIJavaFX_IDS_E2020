package sample;

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
    private DatagramSocket sender;
    private UdpPackage packet;

    int x = 50, y = 50, w = 50, h = 50, altitude = 0;
    int toPort = 6000;
    boolean running = false;
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

        //create udp sender
        try {
            sender = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        /*Graphics*/
        System.out.println("ready!");

        graphicsContext = canvasCanvas.getGraphicsContext2D();

        graphicsContext.fillRect(x, y, w, h);

        Runnable target;

        System.out.println(sliderAltitude.getValue());

    }

    public void takeOff(ActionEvent actionEvent) throws UnknownHostException {
        x -= 5;
        y += 5;
        w += 10;
        h += 10;
        altitude = 5;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
        sliderAltitude.setValue(altitude);
        droneCommand("takeoff");

    }

    public void moveLeft(MouseEvent mouseEvent) throws UnknownHostException {
        x -= 10;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
        droneCommand("left 40");
    }

    public void moveRight(MouseEvent mouseEvent) throws UnknownHostException {
        x += 10;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
        droneCommand("right 40");
    }

    public void moveBack(MouseEvent mouseEvent) throws UnknownHostException {
        y += 10;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
        droneCommand("back 40");
    }

    public void moveForward(MouseEvent mouseEvent) throws UnknownHostException {
        y -= 10;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
        droneCommand("forward 40");
    }

    public void rotateRight(MouseEvent mouseEvent) throws UnknownHostException {
        droneCommand("cw 45");
    }

    public void rotateLeft(MouseEvent mouseEvent) throws UnknownHostException {
        droneCommand("ccw 45");
    }

    public void moveDown(MouseEvent mouseEvent) throws UnknownHostException {
        altitude -= 5;
        sliderAltitude.setValue(altitude);

        droneCommand("down 40");
    }

    public void flipLeft(MouseEvent mouseEvent) throws UnknownHostException {
        droneCommand("flip l");
    }

    public void flipRight(MouseEvent mouseEvent) throws UnknownHostException{
        droneCommand("flip r");
    }

    public void moveUp(MouseEvent mouseEvent) throws UnknownHostException {
        altitude += 5;
        sliderAltitude.setValue(altitude);
        droneCommand("up 40");
    }

    public void land(MouseEvent mouseEvent) throws UnknownHostException {
        x += 5;
        y -= 5;
        w -= 10;
        h -= 10;
        altitude = 0;
        graphicsContext.clearRect(0, 0, canvasCanvas.getWidth(), canvasCanvas.getHeight());
        graphicsContext.fillRect(x, y, w, h);
        sliderAltitude.setValue(altitude);
        droneCommand("land");
    }

    public void droneCommand(String command) throws UnknownHostException {
        UdpPackage takeOffPackage = new UdpPackage(command, InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000,4000);
        loggedPackages.addAll(takeOffPackage);
    }
}
