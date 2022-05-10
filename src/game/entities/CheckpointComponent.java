package game.entities;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class CheckpointComponent extends Component {
	private AnimatedTexture texture;
	private AnimationChannel animBonfire, bonfire;
	private boolean active = false;

	public CheckpointComponent() {
		Image bonfireImage = image("bonfire.png");
		Image bonfireAnimation = image("bonfire_animation.png");
		animBonfire = new AnimationChannel(bonfireAnimation, 8, 36, 70, Duration.seconds(0.6), 0, 7);
		bonfire = new AnimationChannel(bonfireImage, 1, 35, 70, Duration.seconds(0.8), 0, 0);
		texture = new AnimatedTexture(bonfire);

	}

	public void onAdded() {
		entity.getViewComponent().addChild(texture);
		texture.loopAnimationChannel(bonfire);
	}

	@Override
	public void onUpdate(double tpf) {
		if (active && texture.getAnimationChannel() != animBonfire)
			texture.loopAnimationChannel(animBonfire);

	}

	public void setActive() {
		this.active = true;
	}

}
