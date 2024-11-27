package models;

import enums.ClienteTypes;
import monitors.MonitorsMesas;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.dsl.FXGL;

public class Cliente implements Runnable {
    private int id = 0;
    private static int contador = 1;
    private ClienteTypes state;
    private MonitorsMesas monitorsMesas;
    private float[] mesaAsignada;
    private Entity clienteEntity;  // Para la entidad en FXGL

    public Cliente(MonitorsMesas monitorsMesas) {
        this.id = contador++;
        this.state = ClienteTypes.EN_ESPERA_POR_MESA;
        this.monitorsMesas = monitorsMesas;
    }

    public int getId() {
        return id;
    }

    public ClienteTypes setState(ClienteTypes newState) {
        return this.state = newState;
    }

    public float[] getMesaAsignada() {
        return mesaAsignada;
    }

    public void setMesaAsignada(float[] mesaAsignada) {
        this.mesaAsignada = mesaAsignada;
    }
    public Entity getClienteEntity() {
        return clienteEntity;
    }

    public void setClienteEntity(Entity clienteEntity) {
        this.clienteEntity = clienteEntity;
    }

    @Override
    public void run() {
        try {
            // El cliente busca una mesa
            monitorsMesas.buscarMesa(this);
            // Esperar a que se asigne una mesa
            Thread.sleep(5000); // Simula tiempo de espera para asignar mesa

            // Mover al cliente a la mesa asignada
            if (mesaAsignada != null) {
                moveToTable();
            }

            // El cliente espera el servicio o interactúa con la mesa
            // Ejemplo: esperar al mesero, realizar algún pedido, etc.

            // Finalmente, liberar la mesa después de usarla
            monitorsMesas.liberarMesa(this);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void moveToTable() {

        if (mesaAsignada != null && clienteEntity != null) {

            double targetX = mesaAsignada[0];
            double targetY = mesaAsignada[1];

            clienteEntity.setPosition(targetX, targetY);
        }
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", estado=" + state + "}";
    }
}
