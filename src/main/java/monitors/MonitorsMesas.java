package monitors;


import com.almasb.fxgl.dsl.FXGL;
import enums.ClienteTypes;
import javafx.util.Duration;
import models.Cliente;

import java.util.ArrayList;

import java.util.List;

public class MonitorsMesas {
    private final int capacidadMaxima = 20;
    private List<float[]> coordenadas;
    private int mesasDisponibles = capacidadMaxima;
    private final List<Cliente> clientesAtendidos = new ArrayList<>();
    public MonitorsMesas() {
        // Inicializar las coordenadas de las mesas (ejemplo con 10 mesas)
        coordenadas = new ArrayList<>();
        coordenadas.add(new float[]{150, 420});  // Mesa 1
        coordenadas.add(new float[]{150, 520});  // Mesa 2
        coordenadas.add(new float[]{150, 620});  // Mesa 3
        coordenadas.add(new float[]{150, 720});  // Mesa 4
        coordenadas.add(new float[]{150, 820});  // Mesa 5
        coordenadas.add(new float[]{150, 920});  // Mesa 6
        coordenadas.add(new float[]{150, 1020}); // Mesa 7
        coordenadas.add(new float[]{150, 1120}); // Mesa 8
        coordenadas.add(new float[]{150, 1220}); // Mesa 9
        coordenadas.add(new float[]{150, 1320}); // Mesa 10
        coordenadas.add(new float[]{550, 420});  // Mesa 11
        coordenadas.add(new float[]{550, 520});  // Mesa 12
        coordenadas.add(new float[]{550, 620});  // Mesa 13
        coordenadas.add(new float[]{550, 720});  // Mesa 14
        coordenadas.add(new float[]{550, 820});  // Mesa 15
        coordenadas.add(new float[]{550, 920});  // Mesa 16
        coordenadas.add(new float[]{550, 1020}); // Mesa 17
        coordenadas.add(new float[]{550, 1120}); // Mesa 18
        coordenadas.add(new float[]{550, 1220}); // Mesa 19
        coordenadas.add(new float[]{550, 1320}); // Mesa 20
    }

    public synchronized void buscarMesa(Cliente cliente) throws InterruptedException {
        while (mesasDisponibles == 0) { // Mientras no haya mesas disponibles
            System.out.println("Cliente " + cliente.getId() + " está esperando una mesa." + cliente.setState(ClienteTypes.EN_ESPERA_POR_MESA));
            wait(); // El cliente espera hasta ser notificado
        }

        mesasDisponibles--;
        float[] mesaAsignada = coordenadas.remove(0);
        cliente.setMesaAsignada(mesaAsignada);
        clientesAtendidos.add(cliente);
        System.out.println("Cliente " + cliente.getId() + " obtuvo una mesa. Mesas disponibles: " + mesasDisponibles);
        System.out.println("El cliente" + cliente.getId() + " estado: " + cliente.setState(ClienteTypes.EN_ESPERA_POR_MESERO));
    }

    public synchronized void liberarMesa(Cliente cliente) {
        if (mesasDisponibles == 0) {
            mesasDisponibles++;
            coordenadas.add(cliente.getMesaAsignada());
            FXGL.runOnce(() -> {
                if (cliente.getClienteEntity() != null) {
                    FXGL.getGameWorld().removeEntity(cliente.getClienteEntity());
                }
            }, Duration.seconds(1));
            System.out.println("Cliente " + cliente.getId() + " ha dejado su mesa. Mesas disponibles: " + mesasDisponibles);
            notifyAll();
        }
    }


    public synchronized Cliente asignarMesa() throws InterruptedException {
        while (clientesAtendidos.isEmpty()) {
            System.out.println("No hay clientes esperando ser atendidos.");
            wait();
        }

        Cliente cliente = clientesAtendidos.get(0);
        clientesAtendidos.remove(cliente);
        System.out.println("Cliente " + cliente.getId() + " está siendo atendido por un mesero.");
        return cliente;
    }


    public synchronized boolean hasClientes() {
        return !clientesAtendidos.isEmpty();
    }

}
