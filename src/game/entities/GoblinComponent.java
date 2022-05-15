package game.entities;

import static com.almasb.fxgl.dsl.FXGL.image;

import java.util.List;
import java.util.Random;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import game.EntityType;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class GoblinComponent extends EnemyComponent {

	public GoblinComponent() {
		duration = Duration.seconds(1.5);
		Image image = image("enemies/Goblin/Idle.png");
		Image imgWalk = image("enemies/Goblin/Run.png");
		Image imgAttack = image("enemies/Goblin/Attack.png");
		Image imgHit = image("enemies/Goblin/Take Hit.png");
		Image imgDeath = image("enemies/Goblin/Death.png");
		animIdle = new AnimationChannel(image, 4, 45, 51, Duration.seconds(0.8), 0, 3);
		animAttack = new AnimationChannel(imgAttack, 8, 82, 57, Duration.seconds(0.4), 0, 7);
		animWalk = new AnimationChannel(imgWalk, 4, 37, 36, Duration.seconds(0.8), 0, 3);
		animHit = new AnimationChannel(imgHit, 4, 44, 36, Duration.seconds(0.45), 0, 3);
		animDeath = new AnimationChannel(imgDeath, 4, 53, 40, Duration.seconds(0.6), 0, 3);
		texture = new AnimatedTexture(animIdle);
		texture.loop();
		texture.setOnCycleFinished(() -> {
			if (texture.getAnimationChannel() == animHit) {
				this.isGettingHit = false;
				this.speed = -75;
				texture.loopAnimationChannel(animWalk);
			}
			if (texture.getAnimationChannel() == animDeath) {
				this.health--;
				entity.removeFromWorld();
			}
			if (texture.getAnimationChannel() == animAttack) {
				this.isAttacking = false;
			}

		});
	}

	@Override
	public void onAdded() {
		timer = FXGL.newLocalTimer();
		timer.capture();
		waitTimer = FXGL.newLocalTimer();
		speed = -75;
		entity.getViewComponent().addChild(texture);
		entity.setScaleY(1.2);
		spwanXposition = entity.getPosition().getX();

	}

	public void onUpdate(double tpf) {
		commonEnemyFunc(tpf, false);

		List<Entity> playerEntities = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
		Entity player = playerEntities.get(0);
		if (entity.distance(player) > 0 && entity.distance(player) < 120 && entity.distance(player) > 33
				&& !enemyWaiting && !isGettingHit) {
			if (entity.getPosition().getX() < player.getPosition().getX()) {
				goingRight = false;
			} else {
				goingRight = true;
			}
			speed = -75;
		}
		if (entity.distance(player) < 20) {
			speed = 0;
		}

	}

	public void addDamage() {
		if (!this.hasTakenDamage) {
			if (this.health > 1) {
				this.health--;
			} else {
				this.isDead = true;
			}
			this.hasTakenDamage = true;
			this.isGettingHit = true;
			this.speed = 0;

		}
	}
}
