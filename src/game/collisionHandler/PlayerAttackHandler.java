package game.collisionHandler;

import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.FlyingEyeComponent;
import game.entities.GoblinComponent;
import game.entities.PlayerComponent;
import game.entities.SkeletonComponent;

public class PlayerAttackHandler extends CollisionHandler {

	public PlayerAttackHandler() {
		super(EntityType.PLAYER_ATTACK, EntityType.ENEMY);
	}

	protected void onCollision(Entity attack, Entity enemy) {
		List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
		if (player.get(0).getComponent(PlayerComponent.class).getAttack()) {
			if(enemy.getComponents().get(5).getClass() == FlyingEyeComponent.class) {
				enemy.getComponent(FlyingEyeComponent.class).addDamage();
			}
			else if(enemy.getComponents().get(5).getClass() == SkeletonComponent.class) {
				enemy.getComponent(SkeletonComponent.class).addDamage();
			}
			else {
				enemy.getComponent(GoblinComponent.class).addDamage();
			}
		}
		
	}

	protected void onCollisionBegin(Entity attack, Entity enemy) {
		List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
		player.get(0).getComponent(PlayerComponent.class).setEnemy(enemy);
	}

	protected void onCollisionEnd(Entity attack, Entity enemy) {
		List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
		player.get(0).getComponent(PlayerComponent.class).clearEnemy();
	}
}
