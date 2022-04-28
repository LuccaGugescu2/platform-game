package game;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerWallJumpHandler extends CollisionHandler {

    public PlayerWallJumpHandler() {
        super(EntityType.PLAYER, EntityType.WALLJUMP);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity walljump) {
        player.getComponent(PlayerComponent.class).setJump();
    }
}
