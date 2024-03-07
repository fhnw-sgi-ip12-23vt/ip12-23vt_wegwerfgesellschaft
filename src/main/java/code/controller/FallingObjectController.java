package code.controller;

import code.model.components.CargoComponent;
import code.model.components.IsCatchedComponent;
import code.model.components.PlayerDirectionComponent;
import code.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import static code.model.Constants.Constants.PLAYERSIZE;
import static code.model.FallingObjectModel.amount_FO;
import static code.view.FallingObjectView.spawnObjects;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class FallingObjectController {


    public static void dropObjects(GameWorld gameWorld){
        var entities = gameWorld.getEntitiesByType(EntityType.OBJECT);
        if(!entities.isEmpty()){
            for(Entity entity : entities){
                if(entity.getY()>=getAppHeight()){
                   entity.removeFromWorld();
                }
            }
        }
    }

    public static void stickToPlayer(GameWorld gameWorld){
        var entities = gameWorld.getEntitiesByType(EntityType.OBJECT);
        var players = gameWorld.getEntitiesByType(EntityType.PLAYER);
        for (Entity object : entities) {
            var isCatched = object.getComponent(IsCatchedComponent.class).isCatched();
            for (Entity player: players){
                if (!player.getComponent(CargoComponent.class).isFull()
                        && isCatched){
                    player.getComponent(CargoComponent.class).setCatchedEntity(object);
                    if(player.getComponent(PlayerDirectionComponent.class).getDirection()){
                        object.setX(player.getX());
                        object.setY(player.getY());
                    }else{
                        object.setX(player.getX()*0.75);
                        object.setY(player.getY());
                    }
                } else if (player.getComponent(CargoComponent.class).isFull()
                        && isCatched && player.getComponent(CargoComponent.class).getCatchedEntity().equals(object)) {
                    if(player.getComponent(PlayerDirectionComponent.class).getDirection()){
                        object.setX(player.getX()+PLAYERSIZE*1.2);
                        object.setY(player.getY());
                    }else{
                        object.setX(player.getX()+PLAYERSIZE/5*3);
                        object.setY(player.getY());
                    }
                }
            }
        }
    }

}
