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
	protected int health = 3;
	protected LocalTimer timer;
	protected Duration duration;
	protected boolean goingRight = true;
	protected double speed;
	public boolean hasTakenDamage = false;
	protected AnimationChannel animIdle;
	protected AnimationChannel animWalk;
	protected AnimationChannel animAttack;
	protected AnimationChannel animHit;
	protected AnimationChannel animDeath;
	protected AnimatedTexture texture;
	protected boolean isGettingHit = false;
	protected boolean isDead = false;

	@Override
	public void onAdded() {
		timer = FXGL.newLocalTimer();
		timer.capture();
		speed = -75;
		entity.getViewComponent().addChild(texture);
		entity.setScaleY(1.2);
	}

	public void onUpdate(double tpf) {
		if (this.isDead && texture.getAnimationChannel() != animDeath) {
			texture.playAnimationChannel(animDeath);
		}
		if (isGettingHit && texture.getAnimationChannel() != animHit && !isDead) {
			texture.playAnimationChannel(animHit);
		}

		if (this.hasTakenDamage && texture.getAnimationChannel() != animHit && !isGettingHit && !isDead) {
			texture.playAnimationChannel(animHit);
		}
		if (timer.elapsed(duration)) {
			goingRight = !goingRight;
			timer.capture();
		}
		if (goingRight) {
			if (texture.getAnimationChannel() != animWalk && !isGettingHit && !isDead)
				texture.loopAnimationChannel(animWalk);
			entity.setScaleX(-1.2);
		}
		if (!goingRight) {
			if (texture.getAnimationChannel() != animWalk && !isGettingHit && !isDead)
				texture.loopAnimationChannel(animWalk);
			entity.setScaleX(1.2);
		}
		entity.translateX(goingRight ? speed * tpf : -speed * tpf);

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
