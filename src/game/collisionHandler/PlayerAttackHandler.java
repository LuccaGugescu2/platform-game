package game.collisionHandler;

import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.EnemyComponent;
import game.entities.PlayerComponent;

public class PlayerAttackHandler extends CollisionHandler {

	public PlayerAttackHandler() {
		super(EntityType.PLAYER_ATTACK, EntityType.ENEMY);
	}

	 protected void onCollision(Entity attack, Entity enemy) {
		 List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
			if (player.get(0).getComponent(PlayerComponent.class).getAttack()) {
				enemy.getComponent(EnemyComponent.class).addDamage();
				if(enemy.getComponent(EnemyComponent.class).health == 0) {
					enemy.removeFromWorld();
				}
			}
	 }
	 protected void onCollisionBegin(Entity attack, Entity enemy) {
		 List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
		 player.get(0).getComponent(PlayerComponent.class).setEnemy(enemy);
	 }
	 protected void onCollisionEnd(Entity attack, Entity enemy) {
		 List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
		 player.get(0).getComponent(PlayerComponent.class).clearEnemy();	 }
}
