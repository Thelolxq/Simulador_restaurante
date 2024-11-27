package models;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Request {
    private final BlockingQueue<String> orders = new LinkedBlockingQueue<>();

    public void addOrder(String order) throws InterruptedException {
        orders.put(order);
        System.out.println("Orden añadida: " + order);
    }

    public String removeOrder() throws InterruptedException {
        String order = orders.take();
        System.out.println("Orden procesada: " + order);
        return order;
    }

    public boolean hasOrders() {
        return !orders.isEmpty();
    }
}
