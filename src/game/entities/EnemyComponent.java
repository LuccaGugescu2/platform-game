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
	private boolean goingRight = true;
	private double speed;
	private boolean isRemoved = false;
	public boolean hasTakenDamage = false;
	private AnimationChannel animIdle, animWalk, animAttack, animHit;
	private AnimatedTexture texture;
	private boolean isGettingHit = false;
	public EnemyComponent() {
		duration = Duration.seconds(2);
		Image image = image("enemies/Skeleton/Idle.png");
		Image imgWalk = image("enemies/Skeleton/Walk.png");
		Image imgAttack = image("enemies/Skeleton/Attack.png");
		Image imgHit = image("enemies/Skeleton/Take Hit.png");
		animIdle = new AnimationChannel(image, 4, 45, 51, Duration.seconds(0.8), 0, 3);
		animAttack = new AnimationChannel(imgAttack, 8, 45, 57, Duration.seconds(1.5), 0, 7);
		animWalk = new AnimationChannel(imgWalk, 4, 47, 51, Duration.seconds(0.8), 0, 3);
		animHit = new AnimationChannel(imgHit, 4, 45, 51, Duration.seconds(0.8), 0, 3);
		texture = new AnimatedTexture(animIdle);
		texture.loop();
		texture.setOnCycleFinished(() -> {
			if(texture.getAnimationChannel() == animHit) {
				this.isGettingHit = false;
				this.speed = -75;
			}
		});
	}

	@Override
	public void onAdded() {
		timer = FXGL.newLocalTimer();
		timer.capture();
		speed = -75;
		entity.getViewComponent().addChild(texture);
	}

	public void onUpdate(double tpf) {
		if(isGettingHit && texture.getAnimationChannel() != animHit) {
			texture.playAnimationChannel(animHit);
		}
		
		if(this.hasTakenDamage && texture.getAnimationChannel() != animHit && !isGettingHit) {
			texture.playAnimationChannel(animHit);
		}
		if (timer.elapsed(duration)) {
			goingRight = !goingRight;
			timer.capture();
		}
		if (goingRight) {
			if (texture.getAnimationChannel() != animWalk && !isGettingHit)
				texture.loopAnimationChannel(animWalk);
			entity.setScaleX(-1.2);
			entity.setScaleY(1.2);
		}
		if (!goingRight) {
			if (texture.getAnimationChannel() != animWalk && !isGettingHit)
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
			this.isGettingHit = true;
			this.speed = 0;
		}
	}
}
