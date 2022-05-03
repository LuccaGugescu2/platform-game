package game.collisionHandler;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.PlatformerApp;
import game.data.Config;
import game.entities.CheckpointComponent;
import game.entities.PlayerComponent;

public class PlayerChekpointHandler extends CollisionHandler {

    public  PlayerChekpointHandler() {
        super(EntityType.PLAYER, EntityType.CHEKPOINT);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity checkpoint) {
    	Config.playerPosition =  player.getPosition();
    	checkpoint.getComponent(CheckpointComponent.class).setActive();
    }
}
