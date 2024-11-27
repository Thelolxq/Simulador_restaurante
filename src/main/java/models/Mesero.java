package models;

import monitors.MonitorsMesas;

public class Mesero implements Runnable {
    private int id;
    private static int counter = 1;
    private final MonitorsMesas monitorsMesas;
    private final Request request;
    private boolean isBusy = false;

    public Mesero(MonitorsMesas monitorsMesas, Request request) {
        this.id = counter++;
        this.monitorsMesas = monitorsMesas;
        this.request = request;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (monitorsMesas) {
                    if (!monitorsMesas.hasClientes() || isBusy) {
                        monitorsMesas.wait(); // Espera hasta que haya clientes disponibles
                    }
                    if (monitorsMesas.hasClientes()) {
                        isBusy = true;
                        String order = "Orden del cliente " + monitorsMesas.asignarMesa();
                        System.out.println("Mesero " + id + " toma la orden: " + order);
                        request.addOrder(order); // Añade la orden al buffer
                        monitorsMesas.notifyAll();
                        isBusy = false; // Mesero queda disponible
                        System.out.println("Mesero " + id + " disponible" );
                    }
                }
                Thread.sleep(5000); // Simula tiempo para tomar la orden
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
