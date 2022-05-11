package game.data;

import static javafx.scene.input.KeyCode.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Config {
    
    public static KeyCode leftKey = A;
    public static KeyCode rightKey = D;
    public static KeyCode jumpKey = SPACE;
    public static KeyCode fightKey = F;
    
    public static int playerPositionX;
    public static int playerPositionY;
    
    public static Point2D playerPosition;
    
    public static double music;
    
    
    public static void setDefaultConfig () {
    	playerPositionX = 76;
    	playerPositionY = 77*32 -20;
    			
    	playerPosition = new Point2D(playerPositionX, playerPositionY);
    	music = 0.25;
    	
    }

}
