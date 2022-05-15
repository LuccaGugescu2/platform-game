package game.collisionHandler;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getCutsceneService;

import java.util.List;

import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import game.EntityType;
import game.entities.FlyingEyeComponent;
import game.entities.GoblinComponent;
import game.entities.MeomartinoComponent;
import game.entities.PlayerComponent;
import game.entities.SkeletonComponent;

public class MeomartinoCollisionHandler extends CollisionHandler {

	private Entity button;

	public MeomartinoCollisionHandler() {
		super(EntityType.NPC, EntityType.PLAYER);
	}

	protected void onCollisionBegin(Entity npc, Entity player) {
		button = FXGL.spawn("button", npc.getPosition().getX() - 30, npc.getPosition().getY() - 80);
	}

	protected void onCollisionEnd(Entity npc, Entity player) {
		button.removeFromWorld();
	}
	protected void onCollision(Entity npc, Entity player) {
		if(npc.getComponent(MeomartinoComponent.class).getActive()) {
			var lines = getAssetLoader().loadText("cutscene.txt");
			var cutscene = new Cutscene(lines);
			getCutsceneService().startCutscene(cutscene);
		}
	}

}
