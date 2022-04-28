package game.collisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.PlayerComponent;

public class PlayerWallJumpHandler extends CollisionHandler {

    public PlayerWallJumpHandler() {
        super(EntityType.PLAYER, EntityType.WALLJUMP);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity walljump) {
        player.getComponent(PlayerComponent.class).setJump();
    }
    
    @Override
    protected void onCollisionEnd(Entity player, Entity walljump) {
        player.getComponent(PlayerComponent.class).setFalling();
    }
}
