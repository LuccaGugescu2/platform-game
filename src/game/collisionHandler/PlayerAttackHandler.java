package game.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.PlayerComponent;

public class PlayerAttackHandler extends CollisionHandler {

	public PlayerAttackHandler() {
        super(EntityType.PLAYER_ATTACK, EntityType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity enemy) {
        player.getComponent(PlayerComponent.class).checkIfAttack();
    }
    
    @Override
    protected void onCollisionEnd(Entity player, Entity enemy) {
        player.getComponent(PlayerComponent.class).setFalling();
    }

}
