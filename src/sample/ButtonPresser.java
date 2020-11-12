package sample;

import javafx.application.Platform;

public class ButtonPresser implements Runnable{
    private Controller controller;
    private UdpPackage udpPackage;
    boolean running = true;

    public ButtonPresser(Controller controller, UdpPackage udpPackage){
        this.controller = controller;
        this.udpPackage = udpPackage;
    }


    @Override
    public void run() {
        while(running){
            String data = udpPackage.getDataAsString();
            Platform.runLater(
                    new Runnable() {
                        @Override
                        public void run() {
                            controller.buttonFire(data);
                        }
                    });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
