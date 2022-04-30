package game;

import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.Texture;

import game.entities.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import static com.almasb.fxgl.dsl.FXGL.*;
import static game.EntityType.*;

//la classe factory definisce le specifiche delle varie entità ad esempio se hanno le collisioni, le fisiche, se hanno una classe componente dove sono presenti le animazioni ...
public class PlatformerFactory implements EntityFactory {
	/**
	 * crea il background che si muove in base alla posizione del player
	 * @param data
	 * @return
	 */
    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .view(new ScrollingBackgroundView(texture("background/forest.png").getImage(), getAppWidth(), getAppHeight()))
                .zIndex(-1)
                .at(data.getX(), data.getY() + (80 * 70 - getAppHeight()))
                .with(new IrremovableComponent())
                .build();
    }
    /**
     * crea una piattaforma in base ai valori presenti nel file level1.tmx(i file .tmx vengono creati usando il software "Tiled")
     * @param data
     * @return
     */
    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }
    /**
     * crea un entità invisibile che permette al player di scivolare solo su alcuni tipi di muro 
     * @param data
     * @return
     */
    @Spawns("walljump")
    public Entity newWalljump(SpawnData data) {
        return entityBuilder(data)
                .type(WALLJUMP)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    /**
     * crea l' entità player e gli assegna le fisiche e alcuni elementi presenti nel PlayerComponent come ad esempio le animazioni
     * @param data
     * @return
     */
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(6, 8)));

        // fa scivolare il player sul muro
        physics.setFixtureDef(new FixtureDef().friction(0.005f));

        return entityBuilder(data)
                .type(PLAYER)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
                .with(new PlayerComponent())
                .scale(new Point2D(1.5, 1.5))
                .build();
    }
    @Spawns("box")
    public Entity newBox(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        Image image = image("box.png");
        Texture texture = new Texture(image);
        return entityBuilder(data)
                .type(BOX)
                .view(texture)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
                .scale(new Point2D(0.8, 0.8))
                .build();
        
    }



}
