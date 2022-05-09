package game.data;

import static javafx.scene.input.KeyCode.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Config {
    public static Point2D playerPosition = new Point2D(76, 76*32);
    
    public static KeyCode leftKey;
    public static KeyCode rightKey;
    public static KeyCode jumpKey;
    public static KeyCode fightKey;
    
    public static double music;
    
    public static void setDefaultSettings () {
    	playerPosition = new Point2D(76, 76*32);
    	leftKey = A;
    	rightKey = D;
    	jumpKey = SPACE;
    	fightKey = F;
    	music = 0.25;
    	
    }

}
