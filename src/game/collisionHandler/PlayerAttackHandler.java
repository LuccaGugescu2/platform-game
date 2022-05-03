package game.collisionHandler;

import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.PlayerComponent;

public class PlayerAttackHandler extends CollisionHandler {

	public PlayerAttackHandler() {
		super(EntityType.PLAYER_ATTACK, EntityType.ENEMY);
	}

	@Override
	protected void onCollisionBegin(Entity attack, Entity enemy) {
		List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
		player.get(0).getComponent(PlayerComponent.class).setEnemyColliding();
		player.get(0).getComponent(PlayerComponent.class).addEnemy(enemy);	
	}

    @Override
    protected void onCollisionEnd(Entity attack, Entity walljump) {
    	List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
        player.get(0).getComponent(PlayerComponent.class).removeEnemyColliding();
    }
	

	

}
