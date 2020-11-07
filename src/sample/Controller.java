package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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
    public TableView tableViewColumns;
    private GraphicsContext graphicsContext;

    private ObservableList<UdpPackage> savedPackages = FXCollections.observableArrayList();
    private ObservableList<UdpPackage> loggedPackages = FXCollections.observableArrayList();

    private UdpPackageReceiver receiver;
    private DatagramSocket sender;

    int x = 50;
    int y = 50;
    int w = 50;
    int h = 50;
    int altitude = 0;

    public void initialize() throws UnknownHostException {
        // runs when application GUI is ready
        /*UDP*/
        System.out.println("creates list of packages");
        UdpPackage test1 = new UdpPackage("name", "data", InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000,4000);
        UdpPackage test2 = new UdpPackage("name", "hello world", InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000,4000);
        loggedPackages.addAll(test1, test2);
        savedPackages.addAll(test1, test2);

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
        receiver = new UdpPackageReceiver(loggedPackages, 6000);
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
