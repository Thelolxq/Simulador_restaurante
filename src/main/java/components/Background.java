package components;

import com.almasb.fxgl.dsl.FXGL;

public class Background {

    public void background(){
        FXGL.entityBuilder()
                .at(0,0)
                .view("restaurant.png")
                .buildAndAttach();
    }



}
