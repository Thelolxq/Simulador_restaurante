package models;

import monitors.MonitorsMesas;

public class Chef implements Runnable {
    private final Request request;

    public Chef(Request request) {
        this.request = request;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (request.hasOrders()) {
                    String order = request.removeOrder(); // Remueve una orden del buffer
                    System.out.println("Chef procesa la: " + order);

                }
                Thread.sleep(5000); // Simula el tiempo de procesar la orden
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
