package game.entities;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import javafx.scene.image.Image;

public class HealthComponent extends Component {
	private Texture texture;
	public HealthComponent() {
		Image empty = image("health/empty_health.png");
		Image half = image("health/half_health.png");
		Image full = image("health/full_health.png");
		texture = new Texture(half);
	}
	public void onAdded() {
		entity.getViewComponent().addChild(texture);
	}
	public void onUpdate() {
		
	}
}
