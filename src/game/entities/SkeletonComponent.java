package game.entities;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class SkeletonComponent extends EnemyComponent {
	public SkeletonComponent() {
		duration = Duration.seconds(2);
		Image image = image("enemies/Skeleton/Idle.png");
		Image imgWalk = image("enemies/Skeleton/Walk.png");
		Image imgAttack = image("enemies/Skeleton/Attack.png");
		Image imgHit = image("enemies/Skeleton/Take Hit.png");
		Image imgDeath = image("enemies/Skeleton/Death.png");
		animIdle = new AnimationChannel(image, 4, 45, 51, Duration.seconds(0.8), 0, 3);
		animAttack = new AnimationChannel(imgAttack, 8, 45, 57, Duration.seconds(1.5), 0, 7);
		animWalk = new AnimationChannel(imgWalk, 4, 47, 51, Duration.seconds(0.8), 0, 3);
		animHit = new AnimationChannel(imgHit, 4, 52, 53, Duration.seconds(0.45), 0, 3);
		animDeath = new AnimationChannel(imgDeath, 4, 61, 52, Duration.seconds(0.6), 0, 3);
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
		});
	}
}
