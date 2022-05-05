package game.data;

import static javafx.scene.input.KeyCode.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Config {
    public static Point2D playerPosition = new Point2D(76, 76*70);
    
    public static KeyCode leftKey;
    public static KeyCode rightKey;
    public static KeyCode jumpKey;
    
    public static double music;
    
    public static void setDefaultSettings () {
    	playerPosition = new Point2D(76, 76*70);
    	leftKey = A;
    	rightKey = D;
    	jumpKey = SPACE;
    	music = 0.25;
    }

}
