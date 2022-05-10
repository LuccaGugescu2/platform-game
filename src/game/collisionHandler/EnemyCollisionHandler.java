package game.collisionHandler;

import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.EnemyComponent;
import game.entities.PlayerComponent;

public class EnemyCollisionHandler extends CollisionHandler {

	public EnemyCollisionHandler() {
		super(EntityType.PLAYER, EntityType.ENEMY);
	}
	 protected void onCollisionBegin(Entity player, Entity enemy) {
		 player.getComponent(PlayerComponent.class).losehealth();
	 }
}
