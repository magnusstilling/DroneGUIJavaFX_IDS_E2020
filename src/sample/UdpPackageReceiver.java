package sample;

import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

/*
implementing is more flexible than extending classes since an extension locks the class from implementing something else
you can only extend on class whereas you can implement multiple functions
*/
public class UdpPackageReceiver extends Controller implements Runnable{

    private final Controller controller;
    boolean running = false;
    DatagramSocket socket;
    private byte[] buf = new byte[256];
    int port;

    List udpPackages;

    public UdpPackageReceiver(List udpPackages, int port, Controller controller) {
        this.running = true;
        this.udpPackages = udpPackages;
        this.port = port;
        this.controller = controller;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void shutDown(){
        running = false;
    }

    @Override
    public void run() {
        while (running)
        {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                sendToGui(new String(packet.getData()));
                System.out.println("package arrived!");
                UdpPackage udpPackage = new UdpPackage(packet.getData(), packet.getAddress(), socket.getLocalAddress(), packet.getPort(), socket.getLocalPort());
                udpPackages.add(udpPackage);
                String msg = udpPackage.getDataAsString();
                buf = new byte[256];
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendToGui(String msg)
    {
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        controller.receiveMsg(msg);
                    }
                });

    }
}
