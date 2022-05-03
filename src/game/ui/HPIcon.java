package game.ui;


import javafx.scene.Parent;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.texture.Texture;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class HPIcon extends Parent {
	private Texture hpTexture;
    public HPIcon() {
        hpTexture = texture("health/full_health.png");
        getChildren().addAll(hpTexture);
    }
    public void addHPIcon() {
    	getChildren().addAll(hpTexture);
    }
}