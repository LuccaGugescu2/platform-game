package game.entities;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class EnemyComponent extends Component {
	public int health = 3;
	private LocalTimer timer;
	private Duration duration;
	private double distance;
	private boolean goingRight = true;
	private double speed;
	private boolean isRemoved = false;
	public boolean hasTakenDamage = false;
	private AnimationChannel animIdle, animWalk, animAttack;
	private AnimatedTexture texture;

	public EnemyComponent() {
		duration = Duration.seconds(14);
		Image image = image("enemies/Skeleton/Idle.png");
		Image imgWalk = image("enemies/Skeleton/Walk.png");
		Image imgAttack = image("enemies/Skeleton/Attack.png");
		animIdle = new AnimationChannel(image, 4, 45, 51, Duration.seconds(0.8), 0, 3);
		animAttack = new AnimationChannel(imgAttack, 8, 45, 57, Duration.seconds(1.5), 0, 7);
		animWalk = new AnimationChannel(imgWalk, 4, 45, 51, Duration.seconds(0.8), 0, 3);
		texture = new AnimatedTexture(animIdle);
		texture.loop();
	}

	@Override
	public void onAdded() {
		distance = 300 - entity.getX();
		timer = FXGL.newLocalTimer();
		timer.capture();
		speed = distance / duration.toSeconds();
		entity.getViewComponent().addChild(texture);
	}

	public void onUpdate(double tpf) {
		if (timer.elapsed(duration)) {
			goingRight = !goingRight;
			timer.capture();
		}
		if (goingRight) {
			if (texture.getAnimationChannel() != animWalk)
				texture.loopAnimationChannel(animWalk);
			entity.setScaleX(-1.2);
			entity.setScaleY(1.2);
		}
		if (!goingRight) {
			if (texture.getAnimationChannel() != animWalk)
				texture.loopAnimationChannel(animWalk);
			entity.setScaleX(1.2);
			entity.setScaleY(1.2);
		}
		entity.translateX(goingRight ? speed * tpf : -speed * tpf);

	}

	public void addDamage() {
		if(!this.hasTakenDamage) {
			this.health--;			
			this.hasTakenDamage = true;
		}
	}
}
