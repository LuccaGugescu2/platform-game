package game.entities;

import static com.almasb.fxgl.dsl.FXGL.image;

import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import game.EntityType;
import javafx.scene.image.Image;

public class HealthComponent extends Component {
	private Texture texture;
	private Image empty = image("health/empty_health.png");
	private Image half = image("health/half_health.png");
	private Image full = image("health/full_health.png");
	public HealthComponent() {
		
		texture = new Texture(full);
	}
	public void onAdded() {
		entity.getViewComponent().addChild(texture);
		entity.setScaleUniform(0.6);
	}
	
	public void setFull() {
		texture.setImage(full);
	}
	
	public void sethalf() {
		texture.setImage(half);
	}
	
	public void setEmpty() {
		texture.setImage(empty);
	}
}