package game.entities;

import java.util.List;
import java.util.Random;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;

import game.EntityType;
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
	protected double spwanXposition;
	protected boolean isAttacking = false;

	@Override
	public void onAdded() {
		timer = FXGL.newLocalTimer();
		timer.capture();
		speed = -75;
		entity.getViewComponent().addChild(texture);
		entity.setScaleY(1.2);
		spwanXposition = entity.getPosition().getX();
	}

	public void onUpdate(double tpf) {
		commonEnemyFunc(tpf, false);
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

	// funzionalitą comuni dei nemici
	protected void commonEnemyFunc(double tpf, boolean isProtecting) {
		if (this.isDead && texture.getAnimationChannel() != animDeath && !isProtecting && !isAttacking) {
			texture.playAnimationChannel(animDeath);
		}
		if (isGettingHit && texture.getAnimationChannel() != animHit && !isDead && !isProtecting && !isAttacking) {
			texture.playAnimationChannel(animHit);
		}

		if (this.hasTakenDamage && texture.getAnimationChannel() != animHit && !isGettingHit && !isDead && !isProtecting
				&& !isAttacking) {
			texture.playAnimationChannel(animHit);
		}
		if (timer.elapsed(duration)) {
			goingRight = !goingRight;
			timer.capture();
		}

		if (speed != 0&& texture.getAnimationChannel() != animWalk && !isGettingHit && !isDead && !isProtecting && !isAttacking)
			texture.loopAnimationChannel(animWalk);
		if (goingRight) {
			entity.setScaleX(-1.2);
		}
		if (!goingRight) {
			entity.setScaleX(1.2);
		}
		entity.translateX(goingRight || entity.getPosition().getX() > spwanXposition - 8 ? speed * tpf : -speed * tpf);
	}
}
