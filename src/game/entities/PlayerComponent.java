package game.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

public class PlayerComponent extends Component {

	private PhysicsComponent physics;

	private AnimatedTexture texture;

	private AnimationChannel animIdle, animWalk, animJump, animFall,animWallSlide;
	
	private boolean wallContact = false;

	private int jumps = 2;
	/**
	 * funzione contenente le animazioni del personaggio
	 * @author luccagugescu
	 */
	public PlayerComponent() {
		Image image = image("player/_Idle.png");
		Image imgDash = image("player/_Run.png");
		Image jumpImage = image("player/_Jump.png");
		Image fallImage = image("player/_Fall.png");
		Image wallSlide = image("player/_WallSlide.png");
		animIdle = new AnimationChannel(image, 10, 119, 80, Duration.seconds(0.8), 0, 9);
		animWalk = new AnimationChannel(imgDash, 10, 119, 80, Duration.seconds(0.8), 0, 8);
		animJump = new AnimationChannel(jumpImage, 3, 119, 80, Duration.seconds(1), 0, 2);
		animFall = new AnimationChannel(fallImage, 3, 119, 80, Duration.seconds(1), 0, 2);
		animWallSlide = new AnimationChannel(wallSlide, 3, 119, 80, Duration.seconds(1), 0, 2);
		texture = new AnimatedTexture(animJump);
		texture.loop();
	}

	
	@Override
	public void onAdded() {
		entity.getViewComponent().addChild(texture);

		physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
			if (isOnGround) {
				jumps = 2;
			}
		});
	}

	@Override
	public void onUpdate(double tpf) {
		//salto
		if (physics.isMovingY()) {			
			//il personaggio sta saltando
			if(physics.getVelocityY() <= 0) {			
				texture.loopAnimationChannel(animJump);
			}
			//il personaggio sta cadendo
			if(physics.getVelocityY() > 0) {				
				texture.loopAnimationChannel(animFall);
			}

		}
		if (!physics.isMovingX() && jumps == 2) {
			texture.loopAnimationChannel(animIdle);
		}
		if (physics.isMovingX() && !physics.isMovingY()) {
			if (texture.getAnimationChannel() != animWalk) {
				texture.loopAnimationChannel(animWalk);
			}
		}

		if (wallContact) {
			texture.loopAnimationChannel(animWallSlide);
		}
		
	}
	/**
	 * muove e specchia il player a sinistra
	 */
	public void left() {
		getEntity().setScaleX(-1.5);
		physics.setVelocityX(-300);
	}
	/**
	 * muove e specchia il player a destra
	 */
	public void right() {
		getEntity().setScaleX(1.5);
		physics.setVelocityX(300);
	}

	public void stop() {
		physics.setVelocityX(0);
	}

	public void jump() {
		if (jumps == 0)
			return;

		physics.setVelocityY(-400);
		jumps--;
	}


	public void setJump() {
		wallContact = true;
		jumps = 2;
	}
	
	public void setFalling() {
		wallContact = false;
	}
}
