package game.entities;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class MeomartinoComponent extends Component {
	private AnimationChannel animIdle;
	private AnimatedTexture texture;
	private boolean active = false;
	public MeomartinoComponent() {
		Image image = image("npc/Meomartino/Idle.png");
		animIdle = new AnimationChannel(image, 5, 24, 35, Duration.seconds(1.5), 0, 4);
		texture = new AnimatedTexture(animIdle);
		texture.loop();
	}
	
	@Override
	public void onAdded() {
		
		entity.getViewComponent().addChild(texture);
		
		entity.setScaleUniform(1.7);
	}
	public boolean getActive() {
		return this.active;
	}
	public void setActive(boolean value) {
		this.active = value;
	}
}
