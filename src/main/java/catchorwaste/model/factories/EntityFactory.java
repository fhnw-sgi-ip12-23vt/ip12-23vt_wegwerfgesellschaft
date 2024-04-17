package catchorwaste.model.factories;

import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.components.PlayerDirectionComponent;
import catchorwaste.model.components.ImageNameComponent;
import catchorwaste.model.components.IsCatchedComponent;
import catchorwaste.model.components.CartDirectionComponent;
import catchorwaste.model.components.ItemStatusComponent;
import catchorwaste.model.components.ItemTypeComponent;

import catchorwaste.model.enums.EntityType;
import catchorwaste.model.enums.ItemStatus;
import catchorwaste.model.enums.ItemType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static catchorwaste.model.constants.Constants.PLAYERSIZE;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.image;


public class EntityFactory implements com.almasb.fxgl.entity.EntityFactory {


    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        Entity entity = FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(imageMap.get("wegwerfpolizist_r_resized")))
                .with(new CargoComponent(null))
                .with(new PlayerDirectionComponent(true))
                .with(new CollidableComponent(true))
                .scale(2, 2)
                .type(EntityType.PLAYER)
                .build();

        return entity;
    }

    @Spawns("BOX")
    public Entity newBox(SpawnData data) {
        Entity entity = FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(data.get("width"),data.get("height")))
                .build();

        return entity;
    }

    @Spawns("OBJECT")
    public Entity newObject(SpawnData data) {
        Random random = new Random();
        Image[] zufall = {
                imageMap.get("iphone_d"), imageMap.get("iphone_f"), imageMap.get("iphone_r"),
                imageMap.get("kleid_d"), imageMap.get("kleid_f"), imageMap.get("kleid_r"),
                imageMap.get("lampe_d"), imageMap.get("lampe_f"), imageMap.get("lampe_r")};
        String[] names = {
                "iphone_d", "iphone_f", "iphone_r",
                "kleid_d", "kleid_f", "kleid_r",
                "lampe_d", "lampe_f", "lampe_r"};
        int zufallszahl = random.nextInt(zufall.length);
        String name = names[zufallszahl];
        ItemStatus itemStatus;
        ItemType itemType = null;
        // Bestimme den ItemType basierend auf dem Namen des Objekts
        if (name.startsWith("iphone")) {
            itemType = ItemType.IPHONE;
        } else if (name.startsWith("kleid")) {
            itemType = ItemType.DRESS;
        } else if (name.startsWith("lampe")) {
            itemType = ItemType.LAMP;
        }
        if (name.endsWith("_d")) {
            itemStatus = ItemStatus.DEFECT;
        } else if ((name.endsWith("_f"))) {
            itemStatus = ItemStatus.FUNCTIONAL;
        } else {
            itemStatus = ItemStatus.REPAIRABLE;
        }
        return FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(zufall[zufallszahl]))
                .type(EntityType.OBJECT)
                .scale(1, 1)
                .with(new ProjectileComponent(new Point2D(0, 1), 100))
                .with(new ImageNameComponent(name))
                .with(new IsCatchedComponent(false))
                .with(new ItemTypeComponent(itemType))
                .with(new ItemStatusComponent(itemStatus))
                .with(new CollidableComponent(true))
                .with(new BoundingBoxComponent())
                .build();
    }



    @Spawns("CART")
    public Entity newCart(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(imageMap.get(data.get("CargoName") + "_cart_horizontal")))
                .with(new ImageNameComponent(data.get("CargoName")))
                .with(new CartDirectionComponent(true))
                .with(new CollidableComponent(true))
                .scale(1.7, 1.7)
                .type(EntityType.CART)
                .build();
    }


    @Spawns("HOUSE")
    public Entity newHouse(SpawnData data) {
        var houses = new String[]{"haus1", "haus2"};
        return FXGL.entityBuilder(data)
                .view(new ImageView(imageMap.get(houses[(int) data.get("Position") - 1])))
                .type(EntityType.HOUSE)
                .scale(1.2, 1.2)
                .build();
    }


    @Spawns("BACKGROUND")
    public Entity newBackground(SpawnData data) {
        var backgrounds = new String[]{"background_bad", "streets"};
        return FXGL.entityBuilder(data)
                .view(new ImageView(imageMap.get(backgrounds[(int) data.get("Position") - 1])))
                .type(EntityType.BACKGROUND)
                .build();
    }


    @Spawns("WORKSTATION")
    public Entity newWorkstation(SpawnData data) {
        var workstations = new String[]{"reparieren", "markt", "recycle"};
        return FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(imageMap.get(workstations[(int) data.get("Position") - 1])))
                .with(new CollidableComponent(true))
                .scale(1.45, 1.45)
                .zIndex(10)
                .build();
    }


}

