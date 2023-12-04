package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.texture.Texture;

import static com.pi4j.example.CatchOrWaste.FxglTest.*;
import static com.pi4j.example.CatchOrWaste.Variables.*;

public class Player {

    private final Entity entity;
    public Player(Entity entity) {
        this.entity = entity;
    }

    public boolean playerOnUpdate(){
        boundaries();
        return isAtRightEnd();
    }

    public void move(String move){
        setImage(move);
        movePlayer(move);
    }

    private void boundaries(){

        //Boundary right
        if(this.entity.getX() > PLAYER_RIGHT){
            this.entity.setX(PLAYER_RIGHT);

        //Boundary left
        }else if(this.entity.getX()<0){
            this.entity.setX(0);
        }
    }

    private boolean isAtRightEnd(){
        if(this.entity.getX() == PLAYER_RIGHT){
            setImage("Down");
            return true;
        }else{
            return false;
        }
    }

    private boolean isAtLeftEnd(){
        if(this.entity.getX() < PLAYER_LEFT){
            return true;
        }else{
            return false;
        }
    }

    //move Player to the given direction
    private void movePlayer(String direction){
        switch (direction){
            case "Left":
                this.entity.translateX(SPEED*(-1));
                break;
            case "Right":
                this.entity.translateX(SPEED);
                break;
        }
    }

    //change the image of the Player according to the activity it is doing
    private void setImage(String move){
        switch (move){
            case "Left":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("wegwerfpolizist_l.png")));
                break;
            case "Right":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("wegwerfpolizist_r.png")));
                break;
            case "Down":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("wegwerfpolizist_d.png")));
                break;
        }
    }
}
