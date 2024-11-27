package game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import enums.SimulatorTypes;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class SimulatorFactory  implements EntityFactory {

    @Spawns("chef")
    public Entity createChef(SpawnData data){
       return FXGL.entityBuilder()
                .from(data)
                .type(SimulatorTypes.CHEF)
                .viewWithBBox("player.png")
                .buildAndAttach();

    }
    @Spawns("mesero")
    public Entity createMesero(SpawnData data){
        return FXGL.entityBuilder()
                .from(data)
                .type(SimulatorTypes.MESERO)
                .viewWithBBox("player.png")
                .buildAndAttach();
    }

    @Spawns("cliente")
    public Entity createCliente(SpawnData data){
        return FXGL.entityBuilder()
                .at(160, 110)
                .from(data)
                .type(SimulatorTypes.CLIENTE)
                .viewWithBBox("player.png")
                .buildAndAttach();
    }

}
