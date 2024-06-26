package catchorwaste.controller.entities;

import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.components.IsCatchedComponent;
import catchorwaste.model.components.PlayerDirectionComponent;
import catchorwaste.model.enums.EntityType;
import catchorwaste.view.entities.PlayerView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import static catchorwaste.controller.entities.CartController.cartDistance;
import static catchorwaste.model.variables.Constants.CART_HEIGHT_AT_STREET;
import static catchorwaste.model.variables.Constants.STREET_LEFT_END;
import static catchorwaste.model.variables.Constants.STREET_RIGHT_END;

import static catchorwaste.model.variables.globalVariables.playerSpeed;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class PlayerController {

    private PlayerView playerView;
    private CartController cartController;

    public PlayerController(CartController cartController){
        this.playerView = new PlayerView();
        this.cartController = cartController;
    }

    public void catchObject(){

        FXGL.onCollision(EntityType.PLAYER, EntityType.OBJECT, (player,object)->{
            if(!player.getComponent(CargoComponent.class).isFull()){
                player.getComponent(CargoComponent.class).setCatchedEntity(object);
                object.getComponent(IsCatchedComponent.class).setCatched(true);
                object.removeComponent(ProjectileComponent.class);
            }
        });
    }

    public void boundaries(GameWorld gameWorld){
        //Boundary right
        for (Entity player : gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if(player.getX() > STREET_RIGHT_END){
                player.setX(STREET_RIGHT_END);

                //Boundary left
            }else if(player.getX() < STREET_LEFT_END){
                player.setX(STREET_LEFT_END);
            }
        }
    }

    public void movePlayer(boolean direction, GameWorld gameWorld){
        for(Entity player: gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if(direction){
                playerView.changePlayerImage("Right",gameWorld);
                player.getComponent(PlayerDirectionComponent.class).setDirection(true);
                player.translateX(playerSpeed);
            }else{
                player.getComponent(PlayerDirectionComponent.class).setDirection(false);
                playerView.changePlayerImage("Left",gameWorld);
                player.translateX(-playerSpeed);
            }
        }
    }

    public void isAtStreetEnd(){
        for (Entity player : getGameWorld().getEntitiesByType(EntityType.PLAYER)) {
            if (player.getX() == STREET_RIGHT_END) {
                if(player.getComponent(CargoComponent.class).isFull() && cartDistance(false)){
                    playerView.changePlayerImage("Down_Right", getGameWorld());
                    cartController.spawnCart(STREET_RIGHT_END+50, CART_HEIGHT_AT_STREET,
                            player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
                }else if(!player.getComponent(CargoComponent.class).isFull()){
                    playerView.changePlayerImage("Down_Right", getGameWorld());
                }else{
                    playerView.changePlayerImage("", getGameWorld());
                }
            }
            if (player.getX() == STREET_LEFT_END) {
                if(player.getComponent(CargoComponent.class).isFull() && cartDistance(true)){
                    playerView.changePlayerImage("Down_Left", getGameWorld());
                    cartController.spawnCart(STREET_LEFT_END-player.getBoundingBoxComponent().getWidth()*0.2,
                            CART_HEIGHT_AT_STREET, player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
                }else if(!player.getComponent(CargoComponent.class).isFull()){
                    playerView.changePlayerImage("Down_Left", getGameWorld());
                }else{
                    playerView.changePlayerImage("Left", getGameWorld());
                }
            }
        }
    }
}
