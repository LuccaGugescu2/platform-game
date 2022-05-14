package game.data;

import static javafx.scene.input.KeyCode.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;


/**
 * Contiene alcune variabili di servizio
 * @author montis
 *
 */
public class Config {
    
    public static KeyCode leftKey = A;
    public static KeyCode rightKey = D;
    public static KeyCode jumpKey = SPACE;
    public static KeyCode fightKey = F;
    
    public static int health = 6;
    public static Point2D playerPosition = new Point2D(76 , 77*32 -20);
    public static double music = 0.25;
    
    public static String nomePartita;
    
    public static void setConfig () {
    	health = 6;
    	playerPosition = new Point2D(76 , 77*32 -20);
    	music = 0.25;
    	
    }
    
    public static void setConfig (int positionX , int positionY , int h) {
    	playerPosition = new Point2D(positionX , positionY);
    	health = h;
    	
    	music = 0.25;
    }

}
