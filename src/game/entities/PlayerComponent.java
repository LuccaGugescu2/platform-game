package game.entities;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.dsl.FXGL;

public class PlayerComponent extends Component {
	private PhysicsComponent physics;
	private AnimatedTexture texture;
	private boolean isAttacking = false;
	private AnimationChannel animIdle, animWalk, animJump, animFall, animWallSlide, animAttack, animTurnAround, animHit,
			animDeath;
	private boolean wallContact = false;
	private Entity enemy;
	private int jumps = 2;
	private boolean enemyLoading = false;
	private int health = 6;
	private Duration duration;
	private Entity playerAttack;
	private int attacks = 0;
	private String moving = "right";
	private String prevMoving = "right";
	private boolean isTurnedAround = false;
	private boolean isTakingDamage = false;
	public boolean isDead = false;

	/**
	 * funzione contenente le animazioni del personaggio
	 * 
	 * @author luccagugescu
	 */
	public PlayerComponent() {
		Image image = image("player/_Idle.png");
		Image imgDash = image("player/_Run.png");
		Image jumpImage = image("player/_Jump.png");
		Image fallImage = image("player/_Fall.png");
		Image wallSlide = image("player/_WallSlide.png");
		Image attack = image("player/_Attack.png");
		Image turnAround = image("player/_TurnAround.png");
		Image hit = image("player/_Hit.png");
		Image death = image("player/_Death.png");
		// immagine,nuero frame, width frame, height frame, secondi, inizio frame, fine
		// frame
		animIdle = new AnimationChannel(image, 10, 21, 38, Duration.seconds(0.3), 0, 9);
		animWalk = new AnimationChannel(imgDash, 8, 32, 39, Duration.seconds(0.6), 0, 7);
		animJump = new AnimationChannel(jumpImage, 3, 27, 38, Duration.seconds(0.6), 0, 2);
		animFall = new AnimationChannel(fallImage, 3, 31, 38, Duration.seconds(0.2), 0, 2);
		animWallSlide = new AnimationChannel(wallSlide, 3, 22, 34, Duration.seconds(0.5), 0, 2);
		animAttack = new AnimationChannel(attack, 4, 85, 43, Duration.seconds(0.35), 0, 3);
		animTurnAround = new AnimationChannel(turnAround, 3, 30, 35, Duration.seconds(0.15), 0, 2);
		animHit = new AnimationChannel(hit, 3, 31, 39, Duration.seconds(0.6), 0, 2);
		animDeath = new AnimationChannel(death, 10, 55, 39, Duration.seconds(1), 0, 9);

		texture = new AnimatedTexture(animJump);
		texture.loop();
		texture.setOnCycleFinished(() -> {
			if (texture.getAnimationChannel() == animDeath) {
				this.health--;
				this.isDead = false;
				this.isTakingDamage = false;
				physics.setVelocityX(0);
				physics.setVelocityY(0);
				texture.playAnimationChannel(animIdle);
			}
			if (texture.getAnimationChannel() == animAttack) {
				if (this.enemyLoading) {
					if(enemy.getComponents().get(5).getClass() == FlyingEyeComponent.class) {
						enemy.getComponent(FlyingEyeComponent.class).hasTakenDamage = false;
					}
					else {
						enemy.getComponent(SkeletonComponent.class).hasTakenDamage = false;
					}
					
				}
				this.isAttacking = false;
			}
			if (texture.getAnimationChannel() == animTurnAround) {
				this.isTurnedAround = false;
			}
			if (texture.getAnimationChannel() == animHit) {
				this.isTakingDamage = false;
			}
		});
	}

	@Override
	public void onAdded() {
		entity.getViewComponent().addChild(texture);
		playerAttack = FXGL.spawn("playerAttack");
		getEntity().setScaleUniform(1.5);
		physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
			if (isOnGround) {
				jumps = 2;
			}
		});
	}

	public void setEnemy(Entity enemy) {
		this.enemy = enemy;
		this.enemyLoading = true;
	}

	public void clearEnemy() {
		this.enemyLoading = false;
	}

	@Override
	public void onUpdate(double tpf) {
		if (this.isDead && texture.getAnimationChannel() != animDeath) {
			texture.playAnimationChannel(animDeath);
			System.out.println(health);
		}
		if (this.isTakingDamage && texture.getAnimationChannel() != animHit && !isDead) {
			texture.playAnimationChannel(animHit);
		}
		if (prevMoving != moving && !isAttacking && !this.isTakingDamage && !isDead) {
			this.isTurnedAround = true;
			prevMoving = moving;
		}
		if (isTurnedAround && texture.getAnimationChannel() != animTurnAround && !isTakingDamage && !isDead) {
			texture.playAnimationChannel(animTurnAround);
		}
		switch (moving) {
		case "left":
			playerAttack.setAnchoredPosition(entity.getPosition().getX() - 100, entity.getPosition().getY());
			break;
		case "right":
			playerAttack.setAnchoredPosition(entity.getPosition().getX(), entity.getPosition().getY());
			break;
		default:
			playerAttack.setAnchoredPosition(entity.getPosition());
		}

		if (texture.getAnimationChannel() != animAttack && isAttacking && !isTurnedAround && !isTakingDamage
				&& !isDead) {
			texture.playAnimationChannel(animAttack);
		}
		if (physics.isMovingY()) {
			// il personaggio sta saltando
			if (physics.getVelocityY() <= 0 && !isAttacking && !isTurnedAround && !isTakingDamage && !isDead) {
				texture.loopAnimationChannel(animJump);
			}
			// il personaggio sta cadendo
			if (physics.getVelocityY() > 0 && !isAttacking && !isTurnedAround && !isTakingDamage && !isDead) {
				texture.loopAnimationChannel(animFall);
			}

		}
		if (!physics.isMovingX() && !physics.isMovingY() && jumps == 2 && !isAttacking && !isTakingDamage
				&& texture.getAnimationChannel() != animIdle && !isDead) {
			texture.loopAnimationChannel(animIdle);
		}
		if (physics.isMovingX() && !physics.isMovingY() && !isTurnedAround && !isDead) {
			if (texture.getAnimationChannel() != animWalk && !isAttacking && !isTakingDamage) {
				texture.loopAnimationChannel(animWalk);
			}
		}

		if (wallContact && !isAttacking && !isTakingDamage && !isDead) {
			texture.loopAnimationChannel(animWallSlide);
		}

	}

	/**
	 * muove e specchia il player a sinistra
	 */
	public void left() {
		if (!isDead) {
			getEntity().setScaleX(-1.5);
			physics.setVelocityX(-300);
			this.moving = "left";
		}
	}

	/**
	 * muove e specchia il player a destra
	 */
	public void right() {
		if (!isDead) {
			playerAttack.setScaleY(1);
			getEntity().setScaleX(1.5);
			physics.setVelocityX(300);
			this.moving = "right";
		}
	}

	public void stop() {
		physics.setVelocityX(0);
	}

	public void jump() {
		if (!isDead) {
			if (jumps == 0)
				return;

			physics.setVelocityY(-330);
			jumps--;
		}
	}

	public void attack() {
		if (!isDead)
			this.isAttacking = true;
	}

	public void setJump() {
		wallContact = true;
		jumps = 2;
	}

	public void setFalling() {
		wallContact = false;
	}

	public void losehealth() {
		if (this.health > 1) {
			health--;
			physics.setVelocityY(-350);
		} else {
			this.isDead = true;
		}
		this.isTakingDamage = true;
	}

	public void refillHealth() {
		health = 6;
	}

	public int getHealth() {
		return health;
	}

	public boolean getAttack() {
		return this.isAttacking;
	}
	public void addFriction() {
		// fa scivolare il player sul muro
		physics.setFixtureDef(new FixtureDef().friction(0.005f));
	}
	public void removeFriction() {
		physics.setFixtureDef(new FixtureDef().friction(0f));
	}
}
