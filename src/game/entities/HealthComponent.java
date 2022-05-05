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
		
		texture = new Texture(half);
	}
	public void onAdded() {
		entity.getViewComponent().addChild(texture);
	}
	public void onUpdate() {
		List<Entity> player = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER);
		 if(player.get(0).getComponent(PlayerComponent.class).getHealth() > 0){
			texture.setImage(full);
		 }
		
	}
}
