package game.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.PlayerComponent;

public class PlayerSpikeHandler extends CollisionHandler {
	   public PlayerSpikeHandler() {
	        super(EntityType.PLAYER, EntityType.SPIKE);
	    }

	    @Override
	    protected void onCollisionBegin(Entity player, Entity walljump) {
	        player.getComponent(PlayerComponent.class).losehealth();
	    }
	}
