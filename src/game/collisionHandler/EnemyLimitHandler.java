package game.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.FlyingEyeComponent;
import game.entities.GoblinComponent;
import game.entities.SkeletonComponent;

public class EnemyLimitHandler extends CollisionHandler {
	public EnemyLimitHandler() {
		super(EntityType.ENEMY, EntityType.ENEMYLIMIT);
	}

	protected void onCollisionBegin(Entity enemy, Entity enemyLimit) {
		if (enemy.getComponents().get(5).getClass() == FlyingEyeComponent.class) {
			enemy.getComponent(FlyingEyeComponent.class).changeDirection();
		} else if (enemy.getComponents().get(5).getClass() == SkeletonComponent.class) {
			enemy.getComponent(SkeletonComponent.class).changeDirection();
		}
		else {
			enemy.getComponent(GoblinComponent.class).changeDirection();
		}

	}
}
