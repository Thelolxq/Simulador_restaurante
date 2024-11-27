package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import components.Background;
import javafx.util.Duration;
import models.Chef;
import models.Cliente;
import models.Mesero;
import models.Request;
import monitors.MonitorsMesas;

import java.util.Random;

public class RestaurantSimulate extends GameApplication {
        private final int maxCapacity = 20;
        private final double meserosTotal =  maxCapacity * 0.12;
        private MonitorsMesas monitorsMesas = new MonitorsMesas();
        private final Background background = new Background();
        private Request request = new Request();
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(1600);
        gameSettings.setWidth(1600);
        gameSettings.setTitle("Restaurant simulator");
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setManualResizeEnabled(true);
        gameSettings.setScaleAffectedOnResize(true);
        gameSettings.setPreserveResizeRatio(true);
    }

    @Override
    protected void initGame(){
        background.background();
        initializeSimulator();
        generateCliente();
        generateChef();
    }
    private void initializeSimulator(){
        FXGL.getGameWorld().addEntityFactory(new SimulatorFactory());
    }

    private void generateCliente(){
        for(int i = 1; i < 30; i++){
            FXGL.runOnce(()->{
                SpawnData clienteData = new SpawnData(160,110);
                FXGL.spawn("cliente",clienteData);
                System.out.println("se ha generado un cliente");
                Thread cliente = new Thread(new Cliente(monitorsMesas));
                cliente.start();
            }, Duration.seconds(i * poison(0.5)));
        }
    }



    private void generateChef(){
        for (int i = 0; i < meserosTotal; i++) {
            FXGL.runOnce(() -> {
                Thread mesero = new Thread(new Mesero(monitorsMesas, request));
                mesero.start();
                System.out.println("Mesero generado");
            }, Duration.seconds(i * 1));

            FXGL.runOnce(() -> {
                Thread chef = new Thread(new Chef(request));
                chef.start();
                System.out.println("Chef generado");
            }, Duration.seconds(i * 1));
        }
    }

    private double poison(double cantidad){
        Random random = new Random();
        double u = random.nextDouble();
       return Math.log(1 / (1 - u)) / cantidad;
    }


    public static void main(String[] args){
        launch(args);
    }
}
